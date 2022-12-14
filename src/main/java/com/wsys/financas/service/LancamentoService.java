package com.wsys.financas.service;

import com.wsys.financas.enums.StatusLancamento;
import com.wsys.financas.enums.TipoLancamento;
import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Lancamento;
import com.wsys.financas.repository.LancamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LancamentoService {
    private LancamentoRepository repository;

    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validarLancamento(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);
    }

    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validarLancamento(lancamento);
        return repository.save(lancamento);
    }

    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Transactional
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        Example<Lancamento> example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example);
    }

    @Transactional(readOnly = true)
    public Optional<Lancamento> obterLancamentoPorId(final Long id) {
        return repository.findById(id);
    }

    public BigDecimal obterSaldoPorUsuario(Long usuarioId, Integer mes, Integer ano) {
        BigDecimal receitas = repository.obterSaldoPorTipoLancamentoEUsuarioEStatusEMesEAno(
                usuarioId,
                TipoLancamento.RECEITA,
                StatusLancamento.PAGO,
                mes,
                ano);
        BigDecimal despesas = repository.obterSaldoPorTipoLancamentoEUsuarioEStatusEMesEAno(usuarioId,
                TipoLancamento.DESPESA,
                StatusLancamento.PAGO,
                mes,
                ano);

        if (receitas == null) {
            receitas = BigDecimal.ZERO;
        }

        if (despesas == null) {
            despesas = BigDecimal.ZERO;
        }

        return receitas.subtract(despesas);
    }

    @Transactional(readOnly = true)
    public void validarLancamento(Lancamento lancamento) {
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Informe uma Descricao v??lida");
        }

        if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() >12) {
            throw new RegraNegocioException("Informe um Mes v??lido");
        }

        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocioException("Informe um Ano v??lido");
        }

        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
            throw new RegraNegocioException("Informe um Usuario");
        }

        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocioException("Informe um Valor v??lido");
        }

        if (lancamento.getTipo() == null) {
            throw new RegraNegocioException("Informe um Tipo de Lancamento v??lido");
        }
    }
}
