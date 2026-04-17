package Model.Repositories;

import Model.Entidades.Bancos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BancosRepository extends JpaRepository<Bancos, Long> {
    @Query(value = "SELECT * FROM \"BANCOS\" WHERE upper(\"BANCOS_NOME\") LIKE %:kw%", nativeQuery = true)
    List<Bancos> findAllByKeyWord(@Param("kw") String kw);
}
