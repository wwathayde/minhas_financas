package com.wsys.financas.service;

import com.wsys.financas.exceptions.ErroAutenticacao;
import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Usuario;
import com.wsys.financas.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    public Usuario autenticar(final String email, final String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuario não encontrado");
        }

        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErroAutenticacao("Usuario ou senha incorretos");
        }

        return usuario.get();
    }

    @Transactional
    public Usuario salvarUsuario(final Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Transactional(readOnly = true)
    public void validarEmail(final String email) {
        boolean existe = repository.existsByEmail(email);

        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> obterUsuarioPorId(final Long id) {
        return repository.findById(id);
    }

}
