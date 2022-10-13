package com.wsys.financas.repository;

import com.wsys.financas.enums.StatusLancamento;
import com.wsys.financas.enums.TipoLancamento;
import com.wsys.financas.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    @Query("select sum(l.valor) from Lancamento l join l.usuario u "
            + "where u.id = :usuarioId and l.tipo = :tipo and l.status = :status and l.mes = :mes and l.ano = :ano"
            + " group by u")
    BigDecimal obterSaldoPorTipoLancamentoEUsuarioEStatusEMesEAno(
            Long usuarioId,
            TipoLancamento tipo,
            StatusLancamento status,
            Integer mes,
            Integer ano);
}
