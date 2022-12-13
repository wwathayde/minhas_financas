package com.wsys.financas.service;

import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Categoria;
import com.wsys.financas.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.wsys.financas.mock.CategoriaServiceMock.categoriaFilhoOkMock;
import static com.wsys.financas.mock.CategoriaServiceMock.categoriaIdNullMock;
import static com.wsys.financas.mock.CategoriaServiceMock.categoriaNomeVazioMock;
import static com.wsys.financas.mock.CategoriaServiceMock.categoriaOkMock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    CategoriaService categoriaService;

    @Mock
    CategoriaRepository categoriaRepository;

    @Test
    void deveriaAtualizarCategoriaQuandoCategoriaOk() {
        Categoria categoria = categoriaOkMock();

        categoriaService.atualizar(categoria);

        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void naoDeveriaAtualizarQuandoCategoriaComIdNull() throws Exception {
        Categoria categoria = categoriaIdNullMock();

        assertThrows(RegraNegocioException.class, () -> categoriaService.atualizar(categoria));
    }

    @Test
    void naoDeveriaAtualizarQuandoCategoriaComNomeVazio() {
        Categoria categoria = categoriaNomeVazioMock();

        assertThrows(RegraNegocioException.class, () -> categoriaService.atualizar(categoria));
    }

    @Test
    void deveriaDeletarCategoriaComCategoriaOk() {
        Categoria categoria = categoriaOkMock();

        categoriaService.deletar(categoria);

        verify(categoriaRepository, times(1)).delete(categoria);
    }

    @Test
    void naoDeveriaDeletarQuandoCategoriaComIdNull() throws Exception {
        Categoria categoria = categoriaIdNullMock();

        assertThrows(RegraNegocioException.class, () -> categoriaService.deletar(categoria));
    }

    @Test
    void naoDeveriaDeletarCategoriaQuandoCategoriatemFilho() {
        Categoria categoria = categoriaOkMock();
        List<Categoria> listaCategoriasfilho = new ArrayList<>();
        listaCategoriasfilho.add(categoriaFilhoOkMock());

        when(categoriaRepository.findByPai(categoria.getNome())).thenReturn(listaCategoriasfilho);

        assertThrows(RegraNegocioException.class, () -> categoriaService.deletar(categoria));
    }
}
