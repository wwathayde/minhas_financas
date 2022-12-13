package com.wsys.financas.service;

import com.wsys.financas.exceptions.ErroAutenticacao;
import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Usuario;
import com.wsys.financas.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.wsys.financas.mock.UsuarioServiceMock.usuarioIdNullMock;
import static com.wsys.financas.mock.UsuarioServiceMock.usuarioOkMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    UsuarioService usuarioService;

    @Mock
    UsuarioRepository repository;

    @Test
    void deveriaSalvarUsuarioQuandoUsuarioOk() {
        Usuario usuario = usuarioIdNullMock();
        usuarioService.salvarUsuario(usuario);
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void naoDeveriaSalvarUsuarioQuandoUsuarioNaoOk() {
        Usuario usuario = usuarioIdNullMock();
        when(repository.existsByEmail(usuario.getEmail())).thenReturn(Boolean.TRUE);
        assertThrows(RegraNegocioException.class, () -> usuarioService.salvarUsuario(usuario));
    }

    @Test
    void deveriaAutenticarUsuarioQuandoUsuarioOk() {
        Usuario usuario = usuarioOkMock();
        when(repository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        Usuario usuarioLogado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
        assertEquals(usuario, usuarioLogado);
    }

    @Test
    void naoDeveriaAutenticarUsuarioQuandoUsuarioNaoExiste() {
        Usuario usuario = usuarioOkMock();
        when(repository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());

        assertThrows(ErroAutenticacao.class, () -> usuarioService.autenticar(usuario.getEmail(), usuario.getSenha()));
    }

    @Test
    void naoDeveriaAutenticarUsuarioQuandoUsuarioSenhaErrada() {
        Usuario usuario = usuarioOkMock();
        when(repository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        assertThrows(ErroAutenticacao.class, () -> usuarioService.autenticar(usuario.getEmail(), "321"));
    }
}
