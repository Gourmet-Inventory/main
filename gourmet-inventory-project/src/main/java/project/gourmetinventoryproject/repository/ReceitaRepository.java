package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.gourmetinventoryproject.domain.Receita;

import java.util.Optional;

public interface ReceitaRepository extends JpaRepository<Receita,Long> {

    @Query("SELECT r FROM Receita r WHERE r.idEstoqueIngrediente = :idEstoqueIngrediente")
    Optional<Receita> findByIdEstoqueIngrediente(@Param("idEstoqueIngrediente") Long idEstoqueIngrediente);

}
