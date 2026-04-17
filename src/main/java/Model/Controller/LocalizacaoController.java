package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.Localizacao;
import Model.Service.LocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("localizacao")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllLocalizacao() {
        return ResponseEntity.ok(localizacaoService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Localizacao localizacao = localizacaoService.getId(id);
        if (localizacao != null) {
            return ResponseEntity.ok(localizacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Localizacao nao existe"));
    }

    @GetMapping("get-by-usuario/{usuarioId}")
    public ResponseEntity<Object> getByUsuario(@PathVariable Long usuarioId) {
        Localizacao localizacao = localizacaoService.getByUsuarioId(usuarioId);
        if (localizacao != null) {
            return ResponseEntity.ok(localizacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Localizacao nao encontrada para o usuario"));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Localizacao localizacao) {
        localizacao = localizacaoService.save(localizacao);
        if (localizacao != null) {
            return ResponseEntity.ok().body(localizacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Localizacao localizacao) {
        if (localizacaoService.save(localizacao) != null) {
            return ResponseEntity.ok().body(localizacao);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (localizacaoService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
