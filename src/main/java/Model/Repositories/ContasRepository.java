package Model.Repositories;

import Model.Entidades.Contas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContasRepository extends JpaRepository<Contas, Long> {
    boolean existsByNomeIgnoreCaseAndUsuarioId(String nome, Long usuarioId);

    List<Contas> findByUsuarioId(Long usuarioId);

    @Query(value = "SELECT * FROM \"Contas\" WHERE upper(\"CONTAS_NOME\") LIKE %:kw%", nativeQuery = true)
    List<Contas> findAllByKeyWord(@Param("kw") String kw);
}
