package br.com.associadoapi.repositorios;

import br.com.associadoapi.model.entidades.Associado;
import br.com.associadoapi.model.entidades.Boleto;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BoletoRepository extends BaseRepository<Boleto>{

    List<Boleto> findByAssociado(Associado associado);

}
