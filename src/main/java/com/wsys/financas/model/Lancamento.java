package com.wsys.financas.model;

import com.wsys.financas.enums.StatusLancamento;
import com.wsys.financas.enums.TipoLancamento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "lancamento")
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer mes;

    private Integer ano;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private BigDecimal valor;

    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private TipoLancamento tipo;

    @Enumerated(EnumType.STRING)
    private StatusLancamento status;
}
