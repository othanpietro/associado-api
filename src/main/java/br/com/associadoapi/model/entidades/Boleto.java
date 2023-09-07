package br.com.associadoapi.model.entidades;

import br.com.associadoapi.model.entidades.Associado;
import br.com.associadoapi.model.entidades.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static br.com.associadoapi.AppConstants.ENTITY_SCHEMA;

@Entity
@Table(
        name = "boleto",
        schema = ENTITY_SCHEMA)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Boleto extends BaseEntity {

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "documento_pagador", nullable = false)
    private String documentoPagador;

    @Column(name = "nome_pagador", nullable = false)
    private String nomePagador;

    @Column(name = "nome_fantasia_pagador", nullable = false)
    private String nomeFantasiaPagador;

    @Column(name = "situacao", nullable = false)
    private String situacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_associado")
    private Associado associado;

    @Column(name = "indentificador", nullable = false)
    private Integer indentificador;

    @Column(name = "vencimento", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate vencimento;
}
