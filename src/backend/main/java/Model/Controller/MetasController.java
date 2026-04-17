package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.Metas;
import Model.Service.MetasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("metas")
public class MetasController {

    @Autowired
    private MetasService metasService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllMetas() {
        return ResponseEntity.ok(metasService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Metas meta = metasService.getId(id);
        if (meta != null) {
            return ResponseEntity.ok(meta);
        }
        return ResponseEntity.badRequest().body(new Erro("Meta nao existe"));
    }

    @GetMapping("get-by-usuario/{usuarioId}")
    public ResponseEntity<Object> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(metasService.getByUsuarioId(usuarioId));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(metasService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Metas meta) {
        meta = metasService.save(meta);
        if (meta != null) {
            return ResponseEntity.ok().body(meta);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Metas meta) {
        if (metasService.save(meta) != null) {
            return ResponseEntity.ok().body(meta);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @PutMapping("update-progress/{id}")
    public ResponseEntity<Object> updateProgress(@PathVariable Long id, @RequestParam BigDecimal valorAtual) {
        Metas metaAtualizada = metasService.atualizarProgresso(id, valorAtual);
        if (metaAtualizada != null) {
            return ResponseEntity.ok(metaAtualizada);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao atualizar progresso"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (metasService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
