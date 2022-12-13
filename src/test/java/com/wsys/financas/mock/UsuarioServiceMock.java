package com.wsys.financas.mock;

import com.wsys.financas.model.Usuario;

public class UsuarioServiceMock {

    public static Usuario usuarioOkMock() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Nome");
        usuario.setEmail("user@example.com");
        usuario.setSenha("123");
        return usuario;
    }

    public static Usuario usuarioIdNullMock() {
        Usuario usuario = new Usuario();
        usuario.setNome("Nome");
        usuario.setEmail("user@example.com");
        usuario.setSenha("123");
        return usuario;
    }

}
