package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.TipoTransacao;
import Model.Service.TipoTransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tipo-transacao")
public class TipoTransacaoController {

    @Autowired
    private TipoTransacaoService tipoTransacaoService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllTipoTransacao() {
        return ResponseEntity.ok(tipoTransacaoService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        TipoTransacao tipoTransacao = tipoTransacaoService.getId(id);
        if (tipoTransacao != null) {
            return ResponseEntity.ok(tipoTransacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Tipo de transacao nao existe"));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(tipoTransacaoService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TipoTransacao tipoTransacao) {
        tipoTransacao = tipoTransacaoService.save(tipoTransacao);
        if (tipoTransacao != null) {
            return ResponseEntity.ok().body(tipoTransacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody TipoTransacao tipoTransacao) {
        if (tipoTransacaoService.save(tipoTransacao) != null) {
            return ResponseEntity.ok().body(tipoTransacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (tipoTransacaoService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
