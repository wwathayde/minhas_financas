package com.wsys.financas.mock;

import com.wsys.financas.enums.StatusLancamento;
import com.wsys.financas.enums.TipoLancamento;
import com.wsys.financas.model.Lancamento;

import java.math.BigDecimal;

import static com.wsys.financas.mock.UsuarioServiceMock.usuarioOkMock;

public class LancamentoServiceMock {

    public static Lancamento lancamentoSemIdMock() {
        Lancamento lancamento = new Lancamento();
        lancamento.setMes(12);
        lancamento.setAno(2022);
        lancamento.setDescricao("Descricao lancamento mock");
        lancamento.setUsuario(usuarioOkMock());
        lancamento.setValor(BigDecimal.valueOf(22.00));
        lancamento.setCategoria("Categoria");
        lancamento.setTipo(TipoLancamento.DESPESA);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return lancamento;
    }

    public static Lancamento lancamentoOkMock() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(1L);
        lancamento.setMes(12);
        lancamento.setAno(2022);
        lancamento.setDescricao("Descricao lancamento mock");
        lancamento.setUsuario(usuarioOkMock());
        lancamento.setValor(BigDecimal.valueOf(22.00));
        lancamento.setCategoria("Categoria");
        lancamento.setTipo(TipoLancamento.DESPESA);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return lancamento;
    }

    public static Lancamento lancamentoMesNullMock() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(1L);
        lancamento.setMes(null);
        lancamento.setAno(2022);
        lancamento.setDescricao("Descricao lancamento mock");
        lancamento.setUsuario(usuarioOkMock());
        lancamento.setValor(BigDecimal.valueOf(22.00));
        lancamento.setCategoria("Categoria");
        lancamento.setTipo(TipoLancamento.DESPESA);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return lancamento;
    }

}
