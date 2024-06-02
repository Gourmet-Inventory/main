package project.gourmetinventoryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.gourmetinventoryproject.domain.Receita;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByIdIngredienteAndIdPrato(Long idIngrediente, Long idPrato);

    Long findIngredienteIdByTipoMedidaValorMedida(Long estoqueIngrediente, String tipoMedida, String valorMedida);

}

