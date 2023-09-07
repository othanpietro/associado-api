package br.com.associadoapi.service.Impl;

import br.com.associadoapi.exceptions.AssociadoException;
import br.com.associadoapi.model.AssociadoDTO;
import br.com.associadoapi.model.entidades.Associado;
import br.com.associadoapi.repositorios.AssociadoRepository;
import br.com.associadoapi.repositorios.BoletoRepository;
import br.com.associadoapi.service.AssociadoService;
import br.com.associadoapi.utils.ValidateCpfCnpjUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {

    private final BoletoRepository boletoRepository;
    private final AssociadoRepository associadoRepository;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AssociadoDTO salvar(AssociadoDTO associadoDTO) {
        try {
            if(validaDTO(associadoDTO)){
                throw new AssociadoException("DTO inválido");
            }

            if(!ValidateCpfCnpjUtils.cpfCnpjIsValid(associadoDTO.getDocumento())){
                throw new AssociadoException("CPF/CNPJ inválido");
            }
            var associado = associadoRepository.findByDocumento(associadoDTO.getDocumento());

            if(ObjectUtils.isNotEmpty(associado)){
                throw new AssociadoException("associado já cadastrado.");
            }

           associadoRepository.save(converteDtoEmEntidade(associadoDTO));

            return associadoDTO;

        }catch (Exception ex){
            throw new AssociadoException("Erro ao salvar associado. " + ex.getMessage());
        }
    }

    @Override
    public AssociadoDTO listar(String uuid) {
        try {
            var associado = associadoRepository.findByUuid(UUID.fromString(uuid));
            if(ObjectUtils.isEmpty(associado)){
                throw new AssociadoException("associado não encontrado.");
            }
            return converteEntidadeEmDto(associado);
        }catch (Exception ex){
            throw new AssociadoException("Erro ao listar associado:  "+ uuid +". " + ex.getMessage());
        }
    }

    @Override
    public List<AssociadoDTO> listarTodos() {
        try {
            var associados = associadoRepository.findAll();
            if(ObjectUtils.isEmpty(associados)){
                return Collections.emptyList();
            }
            return associados.stream().map(this::converteEntidadeEmDto).collect(Collectors.toList());

        }catch (Exception ex){
            throw new AssociadoException("Erro ao listar todos associados. " + ex.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deletar(String uuid) {
        try {

            var associado = associadoRepository.findByUuid(UUID.fromString(uuid));
            if(ObjectUtils.isEmpty(associado)){
                throw new AssociadoException("Associado não encontrado.");
            }

            var boletos = boletoRepository.findByAssociado(associado);

            var boletosAbertos = boletos.parallelStream().filter( boleto ->
                    boleto.getSituacao().equals("EM_ABERTO") ||
                            boleto.getSituacao().equals("VENCIDO")).collect(Collectors.toList());

            if(boletosAbertos.size() > 0){
                throw new AssociadoException("Associado possui boletos em aberto ou vencido.");
            }

            associadoRepository.delete(associado);
            return true;

        }catch (Exception ex){
            throw new AssociadoException("Erro ao deletar associado:  "+uuid+". " + ex.getMessage());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AssociadoDTO atualizar(AssociadoDTO associadoDTO) {
        try {

            if(validaDTO(associadoDTO)){
                throw new AssociadoException("DTO inválido");
            }

            if(!ValidateCpfCnpjUtils.cpfCnpjIsValid(associadoDTO.getDocumento())){
                throw new AssociadoException("CPF/CNPJ inválido");
            }

            if(ObjectUtils.isEmpty(associadoDTO.getUuid())){
                throw new AssociadoException("UUID em branco");
            }

            var associado = associadoRepository.findByUuid(UUID.fromString(associadoDTO.getUuid()));

            if(ObjectUtils.isEmpty(associado)){
                throw new AssociadoException("Associado não encontrado.");
            }

            if(!associado.getDocumento().equals(associadoDTO.getDocumento())){
                throw new AssociadoException("Documentos diferentes.");
            }

            associado.setDocumento(associadoDTO.getDocumento());
            associado.setNome(associadoDTO.getNome());
            associado.setTipoPessoa(associadoDTO.getTipoPessoa());
            associadoRepository.save(associado);

            return associadoDTO;

        }catch (Exception ex){
            throw new AssociadoException("Erro ao atualizar associado. " +  ex.getMessage());
        }
    }

    @Override
    public AssociadoDTO listarPorDocumento(String documento) {
        try {
            var associado = associadoRepository.findByDocumento(documento);
            if(ObjectUtils.isEmpty(associado)){
                throw new AssociadoException("associado não encontrado.");
            }
            return converteEntidadeEmDto(associado);
        }catch (Exception ex){
            throw new AssociadoException("Erro ao listar associado. " + ex.getMessage());
        }
    }

    private boolean validaDTO(AssociadoDTO dto){
        return ObjectUtils.isEmpty(dto.getNome()) || ObjectUtils.isEmpty(dto.getDocumento()) || ObjectUtils.isEmpty(dto.getTipoPessoa());
    }
    private Associado converteDtoEmEntidade(AssociadoDTO dto){
        return Associado.builder().documento(dto.getDocumento()).nome(dto.getNome()).tipoPessoa(dto.getTipoPessoa()).build();
    }
    private AssociadoDTO converteEntidadeEmDto(Associado associado){
        return AssociadoDTO.builder().documento(associado.getDocumento()).nome(associado.getNome())
                .tipoPessoa(associado.getTipoPessoa()).uuid(associado.getUuid().toString()).build();
    }

}
