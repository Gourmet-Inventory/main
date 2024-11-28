package project.gourmetinventoryproject.dto.alerta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

@Getter
@Setter
@NoArgsConstructor
public class AlertaConsultaDto {
    private long idAlerta;
    private String tipoAlerta;
    private EstoqueIngrediente estoqueIngrediente;
}
