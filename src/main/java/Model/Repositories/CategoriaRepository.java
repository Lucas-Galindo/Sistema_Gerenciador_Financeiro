package Model.Repositories;

import Model.Entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value = "SELECT * FROM \"Categoria\" " +
            "WHERE upper(\"CAT_NOME\") LIKE %:kw% " +
            "OR upper(\"CAT_TIPO\") LIKE %:kw% " +
            "OR upper(\"CAT_DESCRICAO\") LIKE %:kw%", nativeQuery = true)
    List<Categoria> findAllByKeyWord(@Param("kw") String kw);
}
