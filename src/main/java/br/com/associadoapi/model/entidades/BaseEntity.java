package br.com.associadoapi.model.entidades;



import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;


@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type="uuid-char")
    private UUID uuid;

    public BaseEntity(UUID uuid) {this.uuid = uuid;}

}
