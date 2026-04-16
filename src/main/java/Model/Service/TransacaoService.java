package Model.Service;

import Model.Entidades.Transacao;
import Model.Repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> getAll() {
        return transacaoRepository.findAll();
    }

    public Transacao getId(Long id) {
        return transacaoRepository.findById(id).orElse(null);
    }

    public List<Transacao> getByUsuarioId(Long usuarioId) {
        return transacaoRepository.findByUsuarioId(usuarioId);
    }

    public List<Transacao> getByKeyWord(String kw) {
        List<Transacao> transacaoList = transacaoRepository.findAllByKeyWord(kw.toUpperCase());
        return transacaoList;
    }

    public Transacao save(Transacao transacao) {
        Transacao novaTransacao;
        try {
            novaTransacao = transacaoRepository.save(transacao);
        } catch (Exception e) {
            novaTransacao = null;
        }
        return novaTransacao;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        transacaoRepository.deleteById(id);
        return true;
    }
}
