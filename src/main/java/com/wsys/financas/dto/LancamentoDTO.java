package com.wsys.financas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LancamentoDTO {
    private Long id;
    private Integer mes;
    private Integer ano;
    private String descricao;
    private Long usuarioId;
    private BigDecimal valor;
    private String tipo;
    private String status;
    private String categoria;
}
