package br.com.associadoapi.service;

import br.com.associadoapi.model.AssociadoDTO;

import java.util.List;

public interface AssociadoService {

    public AssociadoDTO salvar(AssociadoDTO associadoDTO);

    public AssociadoDTO listar(String uuid);

    public List<AssociadoDTO> listarTodos();

    public boolean deletar(String uuid);

    public AssociadoDTO atualizar(AssociadoDTO associadoDTO);

    public AssociadoDTO listarPorDocumento(String documento);
}
