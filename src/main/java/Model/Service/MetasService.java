package Model.Service;

import Model.Entidades.Metas;
import Model.Repositories.MetasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MetasService {

    @Autowired
    private MetasRepository metasRepository;

    public List<Metas> getAll() {
        return metasRepository.findAll();
    }

    public Metas getId(Long id) {
        return metasRepository.findById(id).orElse(null);
    }

    public List<Metas> getByUsuarioId(Long usuarioId) {
        return metasRepository.findByUsuarioId(usuarioId);
    }

    public List<Metas> getByKeyWord(String kw) {
        List<Metas> metasList = metasRepository.findAllByKeyWord(kw.toUpperCase());
        return metasList;
    }

    public Metas save(Metas meta) {
        Metas novaMeta;
        try {
            if (meta.getValorAtual() == null || meta.getValorAtual().trim().isEmpty()) {
                meta.setValorAtual("0.00");
            }
            novaMeta = metasRepository.save(meta);
        } catch (Exception e) {
            novaMeta = null;
        }
        return novaMeta;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        metasRepository.deleteById(id);
        return true;
    }

    public boolean existsByNomeAndUsuarioId(String nome, Long usuarioId) {
        if (nome == null || usuarioId == null) {
            return false;
        }
        return metasRepository.existsByNomeIgnoreCaseAndUsuarioId(nome, usuarioId);
    }

    public Metas atualizarProgresso(Long id, BigDecimal valorAtual) {
        if (valorAtual == null || valorAtual.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        Metas meta = getId(id);
        if (meta == null) {
            return null;
        }

        meta.setValorAtual(valorAtual.toPlainString());
        return save(meta);
    }
}
