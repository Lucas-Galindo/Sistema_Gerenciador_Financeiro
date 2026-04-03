package Model.Repository;

import Model.Entidades.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {
    List<Usuario> usuarios = new ArrayList<>();

    public UsuarioRepository() {
        Usuario u1 = new Usuario("Joao Silva", "12345678901", "joao@email.com", "123456", "11999990001");
        u1.setId(1L);
        usuarios.add(u1);

        Usuario u2 = new Usuario("Maria Souza", "23456789012", "maria@email.com", "123456", "11999990002");
        u2.setId(2L);
        usuarios.add(u2);

        Usuario u3 = new Usuario("Carlos Lima", "34567890123", "carlos@email.com", "123456", "11999990003");
        u3.setId(3L);
        usuarios.add(u3);

        Usuario u4 = new Usuario("Ana Paula", "45678901234", "ana@email.com", "123456", "11999990004");
        u4.setId(4L);
        usuarios.add(u4);

        Usuario u5 = new Usuario("Lucas Pereira", "56789012345", "lucas@email.com", "123456", "11999990005");
        u5.setId(5L);
        usuarios.add(u5);

        Usuario u6 = new Usuario("Fernanda Costa", "67890123456", "fernanda@email.com", "123456", "11999990006");
        u6.setId(6L);
        usuarios.add(u6);

        Usuario u7 = new Usuario("Rafael Alves", "78901234567", "rafael@email.com", "123456", "11999990007");
        u7.setId(7L);
        usuarios.add(u7);

        Usuario u8 = new Usuario("Patricia Gomes", "89012345678", "patricia@email.com", "123456", "11999990008");
        u8.setId(8L);
        usuarios.add(u8);

        Usuario u9 = new Usuario("Bruno Rocha", "90123456789", "bruno@email.com", "123456", "11999990009");
        u9.setId(9L);
        usuarios.add(u9);

        Usuario u10 = new Usuario("Juliana Martins", "01234567890", "juliana@email.com", "123456", "11999990010");
        u10.setId(10L);
        usuarios.add(u10);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void add(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean existsByCpf(String cpf) {
        boolean exists = false;
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                exists = true;
            }
        }
        return exists;
    }

    public boolean existsByEmail(String email) {
        boolean exists = false;
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                exists = true;
            }
        }
        return exists;
    }

    public boolean existsByPhone(String telefone) {
        boolean exists = false;
        for (Usuario usuario : usuarios) {
            if (usuario.getTelefone().equals(telefone)) {
                exists = true;
            }
        }
        return exists;
    }

    public Usuario findByEmailAndSenha(String email, String senha) {
        Usuario user = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                user = usuario;
            }
        }
        return user;
    }
}
