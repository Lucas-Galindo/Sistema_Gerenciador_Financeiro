package Model.Service;

import Model.Entidades.Contas;
import Model.Repositories.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContasService {

    @Autowired
    private ContasRepository contasRepository;

    public List<Contas> getAll() {
        return contasRepository.findAll();
    }

    public Contas getId(Long id) {
        return contasRepository.findById(id).orElse(null);
    }

    public List<Contas> getByUsuarioId(Long usuarioId) {
        return contasRepository.findByUsuarioId(usuarioId);
    }

    public List<Contas> getByKeyWord(String kw) {
        List<Contas> contasList = contasRepository.findAllByKeyWord(kw.toUpperCase());
        return contasList;
    }

    public Contas save(Contas conta) {
        Contas novaConta;
        try {
            novaConta = contasRepository.save(conta);
        } catch (Exception e) {
            novaConta = null;
        }
        return novaConta;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        contasRepository.deleteById(id);
        return true;
    }

    public boolean existsByNomeAndUsuarioId(String nome, Long usuarioId) {
        if (nome == null || usuarioId == null) {
            return false;
        }
        return contasRepository.existsByNomeIgnoreCaseAndUsuarioId(nome, usuarioId);
    }
}
