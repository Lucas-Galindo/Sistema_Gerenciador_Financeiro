package Model.Service;

import Model.Entidades.Estados;
import Model.Repositories.EstadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadosService {

    @Autowired
    private EstadosRepository estadosRepository;

    public List<Estados> getAll() {
        return estadosRepository.findAllByOrderByNomeAsc();
    }

    public Estados getId(Long id) {
        return estadosRepository.findById(id).orElse(null);
    }

    public List<Estados> getByKeyWord(String kw) {
        return estadosRepository.findByNomeContainingIgnoreCaseOrderByNomeAsc(kw);
    }
}
