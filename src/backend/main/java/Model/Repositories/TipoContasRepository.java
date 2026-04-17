package Model.Repositories;

import Model.Entidades.TipoContas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoContasRepository extends JpaRepository<TipoContas, Long> {
    @Query(value = "SELECT * FROM \"Tipo_Contas\" WHERE upper(\"TIPOCONTAS_NOME\") LIKE %:kw%", nativeQuery = true)
    List<TipoContas> findAllByKeyWord(@Param("kw") String kw);
}
