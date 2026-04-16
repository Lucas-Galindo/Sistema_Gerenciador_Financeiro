package Model.Service;

import Model.Entidades.Cidades;
import Model.Repositories.CidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadesService {

    @Autowired
    private CidadesRepository cidadesRepository;

    public List<Cidades> getAll() {
        return cidadesRepository.findAllByOrderByNomeAsc();
    }

    public Cidades getId(Long id) {
        return cidadesRepository.findById(id).orElse(null);
    }

    public List<Cidades> getByKeyWord(String kw) {
        return cidadesRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(kw);
    }

    public List<Cidades> getByEstadoId(Long estadoId) {
        return cidadesRepository.findDistinctByEstadoId(estadoId);
    }

    public List<Cidades> getByEstadoIdAndNome(Long estadoId, String nome) {
        return cidadesRepository.findDistinctByEstadoIdAndNome(estadoId, nome);
    }
}
