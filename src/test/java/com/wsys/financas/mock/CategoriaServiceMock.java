package com.wsys.financas.mock;

import com.wsys.financas.model.Categoria;

public class CategoriaServiceMock {

    public static Categoria categoriaOkMock() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria nome");
        categoria.setPai(null);
        categoria.setDescricao("Categoria descricao");
        return categoria;
    }

    public static Categoria categoriaFilhoOkMock() {
        Categoria categoria = new Categoria();
        categoria.setId(2L);
        categoria.setNome("Categoria filho nome");
        categoria.setPai("Categoria pai");
        categoria.setDescricao("Categoria filho descricao");
        return categoria;
    }

    public static Categoria categoriaIdNullMock() {
        Categoria categoria = new Categoria();
        categoria.setId(null);
        categoria.setNome("Categoria nome");
        categoria.setPai("Categoria pai");
        categoria.setDescricao("Categoria descricao");
        return categoria;
    }

    public static Categoria categoriaNomeVazioMock() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("");
        categoria.setPai("Categoria pai");
        categoria.setDescricao("Categoria descricao");
        return categoria;
    }

}
