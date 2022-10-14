package com.wsys.financas.controller;

import com.wsys.financas.dto.AtualizaStatusDTO;
import com.wsys.financas.dto.LancamentoDTO;
import com.wsys.financas.enums.StatusLancamento;
import com.wsys.financas.enums.TipoLancamento;
import com.wsys.financas.exceptions.RegraNegocioException;
import com.wsys.financas.model.Lancamento;
import com.wsys.financas.model.Usuario;
import com.wsys.financas.service.LancamentoService;
import com.wsys.financas.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://wesley-financas-frontend.herokuapp.com")
@RestController
@RequestMapping("/api/lancamentos")
@AllArgsConstructor
public class LancamentoController {
    private final LancamentoService service;
    private final  UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody LancamentoDTO dto) {
        try {
            Lancamento lancamento = converter(dto);
            lancamento = service.salvar(lancamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(lancamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto) {
        try {
            Optional<Lancamento> entity = service.obterLancamentoPorId(id);

            if (entity.isEmpty()) {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lançamento não encontrado.");
            }

            Lancamento lancamento = converter(dto);
            lancamento.setId(id);

            service.atualizar(lancamento);

            return ResponseEntity.ok(lancamento);
        } catch(RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        try {
            Optional<Lancamento> lancamento = service.obterLancamentoPorId(Long.parseLong(id));

            if (lancamento.isPresent()) {
                service.deletar(lancamento.get());

                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lançamento não encontrado.");

        } catch(RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) String mes,
            @RequestParam(value = "ano", required = true) String ano,
            @RequestParam(value = "usuario", required = true) String usuarioId) {

        Optional<Usuario> usuario =  usuarioService.obterUsuarioPorId(Long.parseLong(usuarioId));

        if (usuario.isPresent()) {
            Lancamento lancamentoFiltro = new Lancamento();

            Integer mesInt =  (mes == null) ? null : Integer.parseInt(mes);

            lancamentoFiltro.setDescricao(descricao);
            lancamentoFiltro.setMes(mesInt);
            lancamentoFiltro.setAno(Integer.parseInt(ano));
            lancamentoFiltro.setUsuario(usuario.get());

            List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);

            return ResponseEntity.ok(lancamentos);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");
    }

    @GetMapping("{id}")
    public ResponseEntity buscarlancamento(@PathVariable("id") Long id) {
        return service.obterLancamentoPorId(id)
                .map(lancamento -> new ResponseEntity(converter(lancamento), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
        return service.obterLancamentoPorId(id).map(entity -> {
            try {
                StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
                entity.setStatus(statusSelecionado);
                return ResponseEntity.ok(service.atualizar(entity));
            } catch(RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity<String>("Lançamento e/ou Status não encontrado.", HttpStatus.BAD_REQUEST));
    }

    private LancamentoDTO converter(Lancamento lancamento) {
        return LancamentoDTO.builder()
                .id(lancamento.getId())
                .descricao(lancamento.getDescricao())
                .valor(lancamento.getValor())
                .mes(lancamento.getMes())
                .ano(lancamento.getAno())
                .status(lancamento.getStatus().name())
                .tipo(lancamento.getTipo().name())
                .usuarioId(lancamento.getUsuario().getId())
                .build();
    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();

        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario =  usuarioService.obterUsuarioPorId(dto.getUsuarioId())
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o Id informado."));
        lancamento.setUsuario(usuario);

        lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));

        return lancamento;
    }
}
