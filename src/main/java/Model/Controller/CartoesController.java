package Model.Controller;

import Model.Entidades.Cartoes;
import Model.Entidades.Erro;
import Model.Service.CartoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cartoes")
public class CartoesController {

    @Autowired
    private CartoesService cartoesService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllCartoes() {
        return ResponseEntity.ok(cartoesService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Cartoes cartao = cartoesService.getId(id);
        if (cartao != null) {
            return ResponseEntity.ok(cartao);
        }
        return ResponseEntity.badRequest().body(new Erro("Cartao nao existe"));
    }

    @GetMapping("get-by-usuario/{usuarioId}")
    public ResponseEntity<Object> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(cartoesService.getByUsuarioId(usuarioId));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(cartoesService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Cartoes cartao) {
        cartao = cartoesService.save(cartao);
        if (cartao != null) {
            return ResponseEntity.ok().body(cartao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Cartoes cartao) {
        if (cartoesService.save(cartao) != null) {
            return ResponseEntity.ok().body(cartao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (cartoesService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
