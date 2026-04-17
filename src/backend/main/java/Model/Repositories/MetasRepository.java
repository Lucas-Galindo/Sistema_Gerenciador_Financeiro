package Model.Repositories;

import Model.Entidades.Metas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    boolean existsByNomeIgnoreCaseAndUsuarioId(String nome, Long usuarioId);

    List<Metas> findByUsuarioId(Long usuarioId);

    @Query(value = "SELECT * FROM \"Metas\" " +
            "WHERE upper(\"METAS_NOME\") LIKE %:kw% " +
            "OR upper(cast(\"METAS_DATA_LIMITE\" as text)) LIKE %:kw%", nativeQuery = true)
    List<Metas> findAllByKeyWord(@Param("kw") String kw);
}
