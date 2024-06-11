package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.gourmetinventoryproject.domain.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

//    @Query("select u from Usuario u where u.email = ?1 and u.senha = ?2")
//    Usuario login(String email, String senha);

    Optional<Usuario> findByEmail(String email);

    @Transactional
    @Modifying
    Void deleteById(long id);

    List<Usuario> findByCargoEqualsIgnoreCase(String cargo);


    @Query("select u from Usuario u where u.empresa.idEmpresa = ?1")
    List<Usuario> findAllByidEmpresa(Long id);
}
