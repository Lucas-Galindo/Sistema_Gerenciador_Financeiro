package Model.Controller;

import Model.Entidades.Erro;
import Model.Entidades.Usuario;
import Model.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("get-all")
    public ResponseEntity<Object> getAllUsuario() {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getId(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.badRequest().body(new Erro("Usuario nao existe"));
    }

    @GetMapping("get-by-email/{email}")
    public ResponseEntity<Object> getByEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.getByEmail(email);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.badRequest().body(new Erro("E-mail nao encontrado"));
    }

    @GetMapping("get-by-cpf/{cpf}")
    public ResponseEntity<Object> getByCpf(@PathVariable String cpf) {
        Usuario usuario = usuarioService.getByCpf(cpf);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.badRequest().body(new Erro("CPF nao encontrado"));
    }

    @GetMapping("get-by-keyword/{kw}")
    public ResponseEntity<Object> getByKeyWord(@PathVariable String kw) {
        return ResponseEntity.ok(usuarioService.getByKeyWord(kw));
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Usuario usuario) {
        usuario = usuarioService.save(usuario);
        if (usuario != null) {
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao salvar"));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Usuario usuario) {
        if (usuarioService.save(usuario) != null) {
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao alterar"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        if (usuarioService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body(new Erro("Erro ao deletar"));
    }
}
