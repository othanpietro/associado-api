package br.com.associadoapi.model;

import lombok.*;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssociadoDTO {
    private String uuid;
    private String nome;
    private String tipoPessoa;
    private String documento;
}
