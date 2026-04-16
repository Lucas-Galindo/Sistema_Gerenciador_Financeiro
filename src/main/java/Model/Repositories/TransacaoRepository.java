package Model.Repositories;

import Model.Entidades.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByUsuarioId(Long usuarioId);

    @Query(value = "SELECT * FROM \"Transacao\" " +
            "WHERE upper(\"TRANS_DESCRICAO\") LIKE %:kw% " +
            "OR upper(cast(\"TRANS_DATAMOVIMENTACAO\" as text)) LIKE %:kw%", nativeQuery = true)
    List<Transacao> findAllByKeyWord(@Param("kw") String kw);
}
