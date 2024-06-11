package project.gourmetinventoryproject.dto.receita;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReceitaConsultaDto {
    private Long id;
    private Long idIngrediente;
    private Long idPrato;
    private Boolean manipulavel;
    private Integer quantidade;
}
