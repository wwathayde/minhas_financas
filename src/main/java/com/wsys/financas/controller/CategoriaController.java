package com.wsys.financas.controller;

import com.wsys.financas.dto.CategoriaDTO;
import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Categoria;
import com.wsys.financas.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            Categoria categoria = converter(categoriaDTO);
            categoria = categoriaService.salvar(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        try {
            Optional<Categoria> entity = categoriaService.obterCategoriaPorId(id);

            if (entity.isEmpty()) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
            }

            Categoria categoria = converter(dto);
            categoria.setId(id);

            categoriaService.atualizar(categoria);

            return ResponseEntity.ok(categoria);
        } catch(RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        try {
            Optional<Categoria> categoria = categoriaService.obterCategoriaPorId((Long.parseLong(id)));

            if (categoria.isPresent()) {
                categoriaService.deletar(categoria.get());

                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");

        } catch(RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> buscar(
            @RequestParam(value = "id", required = true) String id) {

        Optional<Categoria> categoria =  categoriaService.obterCategoriaPorId(Long.parseLong(id));

        if (categoria.isPresent()) {
            return ResponseEntity.ok(categoria);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
    }

    @GetMapping
    public ResponseEntity<Object> buscarCategorias() {

        Optional<List<Categoria>> categorias =  categoriaService.obterCategorias();

        if (categorias.isPresent()) {
            return ResponseEntity.ok(categorias);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma categoria não encontrada.");
    }

    private Categoria converter(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();

        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(StringUtils.isBlank(categoriaDTO.getDescricao()) ? null : categoriaDTO.getDescricao());
        categoria.setPai(categoriaDTO.getPai());

        return categoria;
    }

}
