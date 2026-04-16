package Model.Service;

import Model.Entidades.Cartoes;
import Model.Repositories.CartoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartoesService {

    @Autowired
    private CartoesRepository cartoesRepository;

    public List<Cartoes> getAll() {
        return cartoesRepository.findAll();
    }

    public Cartoes getId(Long id) {
        return cartoesRepository.findById(id).orElse(null);
    }

    public List<Cartoes> getByUsuarioId(Long usuarioId) {
        return cartoesRepository.findByUsuarioId(usuarioId);
    }

    public List<Cartoes> getByKeyWord(String kw) {
        List<Cartoes> cartoesList = cartoesRepository.findAllByKeyWord(kw.toUpperCase());
        return cartoesList;
    }

    public Cartoes save(Cartoes cartao) {
        Cartoes novoCartao;
        try {
            novoCartao = cartoesRepository.save(cartao);
        } catch (Exception e) {
            novoCartao = null;
        }
        return novoCartao;
    }

    public boolean delete(Long id) {
        if (getId(id) == null) {
            return false;
        }
        cartoesRepository.deleteById(id);
        return true;
    }
}
