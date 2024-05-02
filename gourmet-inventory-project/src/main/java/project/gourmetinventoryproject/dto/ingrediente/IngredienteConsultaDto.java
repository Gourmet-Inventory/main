package project.gourmetinventoryproject.dto.ingrediente;


import lombok.AllArgsConstructor;
import lombok.Getter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;

@Getter
@AllArgsConstructor
public class IngredienteConsultaDto {
    private Long idIngrediente;
    private EstoqueIngrediente estoqueIngrediente;
    private Medidas tipoMedida;
    private String valorMedida;
}
