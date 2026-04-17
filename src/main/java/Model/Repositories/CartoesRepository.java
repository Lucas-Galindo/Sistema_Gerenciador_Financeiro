package Model.Repositories;

import Model.Entidades.Cartoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartoesRepository extends JpaRepository<Cartoes, Long> {
    List<Cartoes> findByUsuarioId(Long usuarioId);

    @Query(value = "SELECT * FROM \"Cartoes\" WHERE upper(\"CARTOES_NOME\") LIKE %:kw%", nativeQuery = true)
    List<Cartoes> findAllByKeyWord(@Param("kw") String kw);
}
