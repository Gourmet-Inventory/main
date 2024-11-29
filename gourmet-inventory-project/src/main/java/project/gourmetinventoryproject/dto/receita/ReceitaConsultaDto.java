package project.gourmetinventoryproject.dto.receita;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReceitaConsultaDto {
    private Long idReceita;
    private List<IngredienteConsultaDto> receita;
}
