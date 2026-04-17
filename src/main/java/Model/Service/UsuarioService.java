package Model.Service;

import Model.Entidades.Usuario;
import Model.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getAll() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        return usuarioList;
    }

    public Usuario getId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        return usuario;
    }

    public Usuario getByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario;
    }

    public Usuario getByCpf(String cpf) {
        Usuario usuario = usuarioRepository.findByCpf(cpf);
        return usuario;
    }

    public List<Usuario> getByKeyWord(String kw) {
        List<Usuario> usuarioList = usuarioRepository.findAllByKeyWord(kw.toUpperCase());
        return usuarioList;
    }

    public Usuario save(Usuario usuario) {
        Usuario novoUsuario;
        try {
            usuarioRepository.save(usuario);
            novoUsuario = usuario;
        } catch (Exception e) {
            novoUsuario = null;
        }
        return novoUsuario;
    }

    public boolean delete(Long id){
        if(getId(id) == null){
            return false;
        }
        else{
            usuarioRepository.deleteById(id);
            return true;
        }
    }

}
