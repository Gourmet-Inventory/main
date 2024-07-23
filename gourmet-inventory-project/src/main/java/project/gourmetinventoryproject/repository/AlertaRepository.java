package project.gourmetinventoryproject.repository;

import project.gourmetinventoryproject.domain.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {


}
