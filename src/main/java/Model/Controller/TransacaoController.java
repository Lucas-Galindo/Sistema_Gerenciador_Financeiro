package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.Transacao;
import Model.Service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllTransacao() {
        return ResponseEntity.ok(transacaoService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Transacao transacao = transacaoService.getId(id);
        if (transacao != null) {
            return ResponseEntity.ok(transacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Transacao nao existe"));
    }

    @GetMapping("get-by-usuario/{usuarioId}")
    public ResponseEntity<Object> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(transacaoService.getByUsuarioId(usuarioId));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(transacaoService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Transacao transacao) {
        transacao = transacaoService.save(transacao);
        if (transacao != null) {
            return ResponseEntity.ok().body(transacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Transacao transacao) {
        if (transacaoService.save(transacao) != null) {
            return ResponseEntity.ok().body(transacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (transacaoService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
