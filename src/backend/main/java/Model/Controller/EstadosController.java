package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.Estados;
import Model.Service.EstadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("estados")
public class EstadosController {

    @Autowired
    private EstadosService estadosService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getEstados() {
        return ResponseEntity.ok(estadosService.getAll());
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(estadosService.getByKeyWord(kw));
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Estados estado = estadosService.getId(id);
        if (estado != null) {
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.badRequest().body(new Erro("Estado nao existe"));
    }
}
