package br.com.associadoapi.repositorios;


import br.com.associadoapi.model.entidades.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, String> {
}
