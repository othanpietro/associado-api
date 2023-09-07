package br.com.associadoapi.service.impl;

import br.com.associadoapi.model.AssociadoDTO;
import br.com.associadoapi.model.entidades.Associado;
import br.com.associadoapi.model.entidades.Boleto;
import br.com.associadoapi.repositorios.AssociadoRepository;
import br.com.associadoapi.repositorios.BoletoRepository;
import br.com.associadoapi.service.Impl.AssociadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssociadoServiceImplTest {

    private AssociadoServiceImpl target;
    private final BoletoRepository boletoRepository = mock(BoletoRepository.class);
    private final AssociadoRepository associadoRepository = mock(AssociadoRepository.class);

    @BeforeEach
    public void setup(){
        target = new AssociadoServiceImpl(boletoRepository, associadoRepository);
    }

    @Test
    public void salvarTest(){
        when(associadoRepository.findByDocumento(any())).thenReturn(null);
        var output = target.salvar(createAssociadoDTO());
        assertNotNull(output);
    }

    @Test
    public void listarTest(){
        var associado = createAssociado();
        associado.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        when(associadoRepository.findByUuid(any())).thenReturn(associado);
        var output = target.listar("123e4567-e89b-12d3-a456-123e4567");
        assertNotNull(output);
        assertEquals("58799307006", output.getDocumento());
        assertEquals("PF", output.getTipoPessoa());
        assertEquals("TESTE", output.getNome());
        assertEquals("123e4567-e89b-12d3-a456-0000123e4567", output.getUuid());
    }
    @Test
    public void listarTodosTest(){
        var associado = createAssociado();
        associado.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        when(associadoRepository.findAll()).thenReturn(Arrays.asList(associado));
        var output = target.listarTodos();
        assertNotNull(output);
        assertEquals("58799307006", output.get(0).getDocumento());
        assertEquals("PF", output.get(0).getTipoPessoa());
        assertEquals("TESTE", output.get(0).getNome());
    }

    @Test
    public void deletarTest(){
        var associado = createAssociado();
        var boletos = createBoleto();
        boletos.setSituacao("PAGO");
        associado.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-0000123e4567"));
        when(associadoRepository.findByUuid(any())).thenReturn(associado);
        when(boletoRepository.findByAssociado(any())).thenReturn(Arrays.asList(boletos));
        var output = target.deletar("123e4567-e89b-12d3-a456-0000123e4567");
        assertTrue(output);
    }

    @Test
    public void atualizarTest(){
        var associadoNovo = createAssociadoDTO();
        associadoNovo.setNome("NOME_ATUALIZADO");
        associadoNovo.setUuid("123e4567-e89b-12d3-a456-0000123e4567");
        var associadoAntigo = createAssociado();
        associadoAntigo.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-0000123e4567"));
        when(associadoRepository.findByUuid(any())).thenReturn(associadoAntigo);
        var output = target.atualizar(associadoNovo);
        assertNotNull(output);
    }
    @Test
    public void listarPorDocumentoTest(){
        var associado = createAssociado();
        associado.setUuid(UUID.fromString("123e4567-e89b-12d3-a456-123e4567"));
        when(associadoRepository.findByDocumento(any())).thenReturn(associado);
        var output = target.listarPorDocumento("58799307006");
        assertNotNull(output);
        assertEquals("58799307006", output.getDocumento());
        assertEquals("PF", output.getTipoPessoa());
        assertEquals("TESTE", output.getNome());

    }

    private AssociadoDTO createAssociadoDTO(){
        return AssociadoDTO
                .builder()
                .documento("58799307006")
                .tipoPessoa("PF")
                .nome("TESTE")
                .build();
    }

    private Associado createAssociado(){
        return Associado
                .builder()
                .documento("58799307006")
                .tipoPessoa("PF")
                .nome("TESTE")
                .build();
    }

    private Boleto createBoleto(){
        return Boleto.builder()
                .valor(BigDecimal.valueOf(250.25))
                .documentoPagador("58799307006")
                .indentificador(555555555)
                .situacao("EM_ABERTO")
                .nomePagador("TESTE")
                .nomeFantasiaPagador("TESTE")
                .vencimento(LocalDate.now().plusDays(1))
                .associado(createAssociado())
                .build();
    }
}
