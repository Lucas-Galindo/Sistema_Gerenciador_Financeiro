package Model.Controller;

import Model.Entidades.Bancos;
import Model.Entidades.Erro;
import Model.Service.BancosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bancos")
public class BancosController {

    @Autowired
    private BancosService bancosService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllBancos() {
        return ResponseEntity.ok(bancosService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Bancos banco = bancosService.getId(id);
        if (banco != null) {
            return ResponseEntity.ok(banco);
        }
        return ResponseEntity.badRequest().body(new Erro("Banco nao existe"));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(bancosService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Bancos banco) {
        banco = bancosService.save(banco);
        if (banco != null) {
            return ResponseEntity.ok().body(banco);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Bancos banco) {
        if (bancosService.save(banco) != null) {
            return ResponseEntity.ok().body(banco);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (bancosService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
