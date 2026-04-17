package Model.Repositories;

import Model.Entidades.Estados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadosRepository extends JpaRepository<Estados, Long> {
    List<Estados> findAllByOrderByNomeAsc();

    List<Estados> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
