package Model.Repositories;

import Model.Entidades.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long> {
    Localizacao findByUsuarioId(Long usuarioId);
}
