package com.wsys.financas.controller;

import com.wsys.financas.dto.UsuarioDTO;
import com.wsys.financas.exceptions.ErroAutenticacao;
import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Usuario;
import com.wsys.financas.service.LancamentoService;
import com.wsys.financas.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;


    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        try {
            Usuario usuarioAutenticado = service.autenticar(usuario.getEmail(), usuario.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/saldo/{ano}/{mes}")
    public ResponseEntity<Object> obterSaldo(
            @PathVariable("id") Long usuarioId,
            @PathVariable("ano") Integer ano,
            @PathVariable("mes") Integer mes) {
        Optional<Usuario> usuario = service.obterUsuarioPorId(usuarioId);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(lancamentoService.obterSaldoPorUsuario(usuarioId, mes, ano));
    }

}
