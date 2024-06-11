package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.util.Date;
import java.util.List;

@Repository
public interface EstoqueIngredienteRepository extends JpaRepository<EstoqueIngrediente, Long> {
    List<EstoqueIngrediente> findByNomeAndValorMedida(String nomeIngrediente, double quantidadeIngrediente);

    List<EstoqueIngrediente> findAllByEmpresa(Empresa empresa);
}

