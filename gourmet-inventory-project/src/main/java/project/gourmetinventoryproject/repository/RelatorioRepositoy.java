package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Relatorio;

import java.util.List;

@Repository
public interface RelatorioRepositoy extends JpaRepository<Relatorio, Long> {

    @Query("SELECT r FROM Relatorio r WHERE MONTH(r.data) = :month AND r.empresa = :empresa")
    List<Relatorio> findAllByDataMonth(@Param("month") int month, @Param("empresa") Empresa empresa);

}
