package com.wsys.financas.service;

import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Categoria;
import com.wsys.financas.repository.CategoriaRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria salvar(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Optional<Categoria> obterCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Transactional
    public Optional<List<Categoria>> obterCategorias() {
        return Optional.of(categoriaRepository.findAll());
    }

    @Transactional
    public Categoria atualizar(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deletar(Categoria categoria) {
        validarDelecaoCategoria(categoria);
        categoriaRepository.delete(categoria);
    }

//    @Transactional(readOnly = true)
    private void validarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new RegraNegocioException("Id da categoria inválido");
        }
        if (StringUtils.isBlank(categoria.getNome())) {
            throw new RegraNegocioException("Informe um Nome válido");
        }
    }

//    @Transactional(readOnly = true)
    private void validarDelecaoCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new RegraNegocioException("Id da categoria inválido");
        }
        if (!categoriaRepository.findByPai(categoria.getNome()).isEmpty()) {
            throw new RegraNegocioException("Categoria não pode ser deletada, pois possui uma ou mais sub-categorias dependentes.");
        }
    }

}
