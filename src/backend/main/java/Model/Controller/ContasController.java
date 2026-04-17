package Model.Controller;

import Model.Entidades.Contas;
import Model.Entidades.Erro;
import Model.Service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("contas")
public class ContasController {

    @Autowired
    private ContasService contasService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllContas() {
        return ResponseEntity.ok(contasService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Contas conta = contasService.getId(id);
        if (conta != null) {
            return ResponseEntity.ok(conta);
        }
        return ResponseEntity.badRequest().body(new Erro("Conta nao existe"));
    }

    @GetMapping("get-by-usuario/{usuarioId}")
    public ResponseEntity<Object> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(contasService.getByUsuarioId(usuarioId));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(contasService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Contas conta) {
        conta = contasService.save(conta);
        if (conta != null) {
            return ResponseEntity.ok().body(conta);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Contas conta) {
        if (contasService.save(conta) != null) {
            return ResponseEntity.ok().body(conta);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (contasService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
