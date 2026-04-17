package Model.Service;

import Model.Entidades.Localizacao;
import Model.Repositories.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizacaoService {

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    public List<Localizacao> getAll() {
        return localizacaoRepository.findAll();
    }

    public Localizacao getId(Long id) {
        return localizacaoRepository.findById(id).orElse(null);
    }

    public Localizacao getByUsuarioId(Long usuarioId) {
        return localizacaoRepository.findByUsuarioId(usuarioId);
    }

    public Localizacao save(Localizacao localizacao) {
        Localizacao novaLocalizacao;
        try {
            novaLocalizacao = localizacaoRepository.save(localizacao);
        } catch (Exception e) {
            novaLocalizacao = null;
        }
        return novaLocalizacao;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        localizacaoRepository.deleteById(id);
        return true;
    }
}
