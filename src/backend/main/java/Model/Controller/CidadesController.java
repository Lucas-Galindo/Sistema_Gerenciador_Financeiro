package Model.Controller;

import Model.Entidades.Cidades;
import Model.Entidades.Erro;
import Model.Service.CidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cidades")
public class CidadesController {

    @Autowired
    private CidadesService cidadesService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getCidades() {
        return ResponseEntity.ok(cidadesService.getAll());
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(cidadesService.getByKeyWord(kw));
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Cidades cidade = cidadesService.getId(id);
        if (cidade != null) {
            return ResponseEntity.ok(cidade);
        }
        return ResponseEntity.badRequest().body(new Erro("Cidade nao existe"));
    }
}
