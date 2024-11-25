package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.gourmetinventoryproject.domain.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @Query("SELECT a FROM Alerta a WHERE a.estoqueIngrediente.empresa = :empresa AND a.tipoAlerta = 'Estoque vazio' Or a.tipoAlerta = 'Estoque acabando'")
    List<Alerta> findAllByEmpresaAndTipoAlerta(@Param("empresa") Empresa empresa);


}
