package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.TipoContas;
import Model.Service.TipoContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tipo-contas")
public class TipoContasController {

    @Autowired
    private TipoContasService tipoContasService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllTipoContas() {
        return ResponseEntity.ok(tipoContasService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        TipoContas tipoConta = tipoContasService.getId(id);
        if (tipoConta != null) {
            return ResponseEntity.ok(tipoConta);
        }
        return ResponseEntity.badRequest().body(new Erro("Tipo de conta nao existe"));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(tipoContasService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody TipoContas tipoConta) {
        tipoConta = tipoContasService.save(tipoConta);
        if (tipoConta != null) {
            return ResponseEntity.ok().body(tipoConta);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody TipoContas tipoConta) {
        if (tipoContasService.save(tipoConta) != null) {
            return ResponseEntity.ok().body(tipoConta);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (tipoContasService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
