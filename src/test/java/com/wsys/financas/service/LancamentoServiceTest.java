package com.wsys.financas.service;

import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Lancamento;
import com.wsys.financas.repository.LancamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wsys.financas.mock.LancamentoServiceMock.lancamentoMesNullMock;
import static com.wsys.financas.mock.LancamentoServiceMock.lancamentoOkMock;
import static com.wsys.financas.mock.LancamentoServiceMock.lancamentoSemIdMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LancamentoServiceTest {


    @InjectMocks
    LancamentoService lancamentoService;

    @Mock
    LancamentoRepository repository;

    @Test
    void deveriaSalvarLancamentoQuandoLancamentoOk() {
        Lancamento lancamento = lancamentoSemIdMock();
        lancamentoService.salvar(lancamento);
        verify(repository, times(1)).save(lancamento);
    }

    @Test
    void naoDeveriaSalvarLancamentoQuandoLancamentoNaoOk() {
        Lancamento lancamento = lancamentoMesNullMock();
        assertThrows(RegraNegocioException.class, () -> lancamentoService.salvar(lancamento));
    }

    @Test
    void deveriaAtualizarLancamentoQuandoLancamentoOk() {
        Lancamento lancamento = lancamentoOkMock();
        lancamentoService.salvar(lancamento);
        verify(repository, times(1)).save(lancamento);
    }

    @Test
    void naoDeveriaAtualizarLancamentoQuandoLancamentoNaoOk() {
        Lancamento lancamento = lancamentoSemIdMock();
        assertThrows(RegraNegocioException.class, () -> lancamentoService.atualizar(lancamento));
    }

    @Test
    void deveriaDeletarLancamentoQuandoLancamentoOk() {
        Lancamento lancamento = lancamentoOkMock();
        lancamentoService.deletar(lancamento);
        verify(repository, times(1)).delete(lancamento);
    }

    @Test
    void naoDeveriaDeletarLancamentoQuandoLancamentoIdNull() {
        Lancamento lancamento = lancamentoSemIdMock();
        assertThrows(RegraNegocioException.class, () -> lancamentoService.deletar(lancamento));
    }
}
