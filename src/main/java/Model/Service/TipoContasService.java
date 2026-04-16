package Model.Service;

import Model.Entidades.TipoContas;
import Model.Repositories.TipoContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoContasService {

    @Autowired
    private TipoContasRepository tipoContasRepository;

    public List<TipoContas> getAll() {
        return tipoContasRepository.findAll();
    }

    public TipoContas getId(Long id) {
        return tipoContasRepository.findById(id).orElse(null);
    }

    public List<TipoContas> getByKeyWord(String kw) {
        List<TipoContas> tipoContasList = tipoContasRepository.findAllByKeyWord(kw.toUpperCase());
        return tipoContasList;
    }

    public TipoContas save(TipoContas tipoConta) {
        TipoContas novoTipoConta;
        try {
            novoTipoConta = tipoContasRepository.save(tipoConta);
        } catch (Exception e) {
            novoTipoConta = null;
        }
        return novoTipoConta;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        tipoContasRepository.deleteById(id);
        return true;
    }
}
