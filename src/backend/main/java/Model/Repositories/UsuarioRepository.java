package Model.Repositories;

import Model.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String email);

    Usuario findByCpf(String cpf);

    @Query(value = "SELECT * FROM \"Usuario\" " +
            "WHERE upper(\"USU_NOME\") LIKE %:kw% " +
            "OR upper(\"USU_EMAIL\") LIKE %:kw% " +
            "OR upper(\"USU_CPF\") LIKE %:kw% " +
            "OR upper(\"USU_TEL\") LIKE %:kw%", nativeQuery = true)
    List<Usuario> findAllByKeyWord(@Param("kw") String kw);
}
