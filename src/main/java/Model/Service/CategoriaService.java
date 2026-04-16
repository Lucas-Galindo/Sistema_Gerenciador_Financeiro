package Model.Service;

import Model.Entidades.Categoria;
import Model.Repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAll() {
        return categoriaRepository.findAll();
    }

    public Categoria getId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public List<Categoria> getByKeyWord(String kw) {
        List<Categoria> categoriaList = categoriaRepository.findAllByKeyWord(kw.toUpperCase());
        return categoriaList;
    }

    public Categoria save(Categoria categoria) {
        Categoria novaCategoria;
        try {
            novaCategoria = categoriaRepository.save(categoria);
        } catch (Exception e) {
            novaCategoria = null;
        }
        return novaCategoria;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }
}
