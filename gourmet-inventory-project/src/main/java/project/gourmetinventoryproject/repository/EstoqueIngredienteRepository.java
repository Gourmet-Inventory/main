package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

@Repository
public interface EstoqueIngredienteRepository extends JpaRepository<EstoqueIngrediente, Long> {
}

