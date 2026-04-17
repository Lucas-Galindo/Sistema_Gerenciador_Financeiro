package Model.Repositories;

import Model.Entidades.Cidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadesRepository extends JpaRepository<Cidades, Long> {
    List<Cidades> findAllByOrderByNomeAsc();

    List<Cidades> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

    @Query("SELECT DISTINCT l.cidade FROM Localizacao l WHERE l.estado.id = :estadoId ORDER BY l.cidade.nome")
    List<Cidades> findDistinctByEstadoId(@Param("estadoId") Long estadoId);

    @Query("SELECT DISTINCT l.cidade FROM Localizacao l " +
            "WHERE l.estado.id = :estadoId AND UPPER(l.cidade.nome) LIKE CONCAT('%', UPPER(:nome), '%') " +
            "ORDER BY l.cidade.nome")
    List<Cidades> findDistinctByEstadoIdAndNome(@Param("estadoId") Long estadoId, @Param("nome") String nome);
}
