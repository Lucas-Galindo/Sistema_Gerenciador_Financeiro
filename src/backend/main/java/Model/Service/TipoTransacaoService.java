package Model.Service;

import Model.Entidades.TipoTransacao;
import Model.Repositories.TipoTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTransacaoService {

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;

    public List<TipoTransacao> getAll() {
        return tipoTransacaoRepository.findAll();
    }

    public TipoTransacao getId(Long id) {
        return tipoTransacaoRepository.findById(id).orElse(null);
    }

    public List<TipoTransacao> getByKeyWord(String kw) {
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAllByKeyWord(kw.toUpperCase());
        return tipoTransacaoList;
    }

    public TipoTransacao save(TipoTransacao tipoTransacao) {
        TipoTransacao novoTipoTransacao;
        try {
            novoTipoTransacao = tipoTransacaoRepository.save(tipoTransacao);
        } catch (Exception e) {
            novoTipoTransacao = null;
        }
        return novoTipoTransacao;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        tipoTransacaoRepository.deleteById(id);
        return true;
    }
}
