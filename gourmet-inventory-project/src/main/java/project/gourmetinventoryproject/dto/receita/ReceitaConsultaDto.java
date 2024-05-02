package project.gourmetinventoryproject.dto.receita;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceitaConsultaDto {
    private Long id;
    private Long idIngrediente;
    private Long idPrato;
    private Boolean manipulavel;
}
