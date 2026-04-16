package Model.Service;

import Model.Entidades.Bancos;
import Model.Repositories.BancosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancosService {

    @Autowired
    private BancosRepository bancosRepository;

    public List<Bancos> getAll() {
        return bancosRepository.findAll();
    }

    public Bancos getId(Long id) {
        return bancosRepository.findById(id).orElse(null);
    }

    public List<Bancos> getByKeyWord(String kw) {
        List<Bancos> bancosList = bancosRepository.findAllByKeyWord(kw.toUpperCase());
        return bancosList;
    }

    public Bancos save(Bancos banco) {
        Bancos novoBanco;
        try {
            if (banco.getId() == null) {
                Long nextId = bancosRepository.findAll().stream()
                        .map(Bancos::getId)
                        .filter(id -> id != null)
                        .max(Long::compareTo)
                        .orElse(0L) + 1L;
                banco.setId(nextId);
            }
            novoBanco = bancosRepository.save(banco);
        } catch (Exception e) {
            novoBanco = null;
        }
        return novoBanco;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        bancosRepository.deleteById(id);
        return true;
    }
}
