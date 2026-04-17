package Model.Repositories;

import Model.Entidades.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {
    @Query(value = "SELECT * FROM \"TIPO_TRANSACAO\" " +
            "WHERE upper(\"TIPO_TRANSACAO_NOME\") LIKE %:kw% " +
            "OR upper(\"TIPO_TRANSACAO_DESCRICAO\") LIKE %:kw%", nativeQuery = true)
    List<TipoTransacao> findAllByKeyWord(@Param("kw") String kw);
}
