package Model.Controller;

import Model.Entidades.Usuario;
import Model.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("test")
    public ResponseEntity<Object> teste() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("list-usuarios")
    public ResponseEntity<Object> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.getUsuarios();
        return ResponseEntity.ok().body(usuarios);
    }

    @PostMapping("cadastro-usuario")
    public ResponseEntity<Object> cadastroUsuario(@RequestBody Usuario usuario) {
        if(usuario.getNome() == null || usuario.getNome().trim().isEmpty()
                || usuario.getCpf() == null || usuario.getCpf().trim().isEmpty()
                || usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()
                || usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()
                || usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Preencha todos os campos");
        }

        if(usuarioRepository.existsByCpf(usuario.getCpf())) {
            return ResponseEntity.badRequest().body("CPF já cadastrado");
        }

        if(usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        if(usuarioRepository.existsByPhone(usuario.getTelefone())) {
            return ResponseEntity.badRequest().body("Telefone já cadastrado");
        }

        usuarioRepository.add(usuario);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }
    @GetMapping("login")
    public ResponseEntity<Object> loginUsuario(@RequestParam String email, @RequestParam String senha) {
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
        if (usuario != null) {
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body("E-mail ou senha incorretos");
    }
    @GetMapping("buscar-usuario-email")
    public ResponseEntity<Object> buscarUsuarioEmail(@RequestParam String email){
        Usuario usuario = null;
        for (Usuario u : usuarioRepository.getUsuarios()){
            if(u.getEmail().equalsIgnoreCase(email))
                usuario = u;
        }
        if(usuario != null){
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body("E-mail não encontrado");
    }

    @GetMapping("buscar-usuario-cpf")
    public ResponseEntity<Object> buscarUsuarioCpf(@RequestParam String cpf){
        Usuario usuario = null;
        for (Usuario u : usuarioRepository.getUsuarios()){
            if(u.getCpf().equalsIgnoreCase(cpf))
                usuario = u;
        }
        if(usuario != null){
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body("CPF não encontrado");
    }

    public void atualizarUsuario(@RequestParam Usuario usuario){
        Boolean flag = false;
        for(Usuario u : usuarioRepository.getUsuarios()){
            if(u.getId() == usuario.getId()){
                u.setNome(usuario.getNome());
                u.setCpf(usuario.getCpf());
                u.setEmail(usuario.getEmail());
                u.setSenha(usuario.getSenha());
                u.setTelefone(usuario.getTelefone());
                flag = true;
            }
        }
        if(flag)
            ResponseEntity.ok().body("Usuário atualizado com sucesso");
        else
            ResponseEntity.badRequest().body("Usuário não encontrado");
    }

    public void deletarUsuario(@RequestParam Long id){
        Boolean flag = false;
        for(Usuario u : usuarioRepository.getUsuarios()){
            if(u.getId() == id){
                usuarioRepository.getUsuarios().remove(u);
                flag = true;
            }
        }
        if(flag)
            ResponseEntity.ok().body("Usuário deletado com sucesso");
        else
            ResponseEntity.badRequest().body("Usuário não encontrado");
    }
}