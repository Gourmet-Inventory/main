package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Receita;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByIdIngredienteAndIdPrato(Long idIngrediente, Long idPrato);

    @Query("SELECT r.id FROM Receita r JOIN Ingrediente i WHERE r.idIngrediente = :idIngrediente AND i.tipoMedida = :tipoMedida AND i.valorMedida = :valorMedida")
    Long findIngredienteIdByTipoMedidaValorMedida(@Param("idIngrediente") Long idIngrediente, @Param("tipoMedida") String tipoMedida, @Param("valorMedida") String valorMedida);

    List<Receita> findByIdPrato(Long idPrato);
}

