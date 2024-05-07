package project.gourmetinventoryproject.repository;

import inventory_gourmet.api.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

//    Optional<Empresa> findById(Long id);

    @Query("SELECT e FROM Empresa e WHERE e.responsavel = ?1")
    List<Empresa> findByResponsavel(Usuario responsavel);
}
