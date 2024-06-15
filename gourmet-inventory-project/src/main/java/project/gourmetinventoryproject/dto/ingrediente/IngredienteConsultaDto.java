package project.gourmetinventoryproject.dto.ingrediente;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;
import project.gourmetinventoryproject.domain.Medidas;

@Getter
@Setter
@NoArgsConstructor
public class IngredienteConsultaDto {
    private Long idIngrediente;
    private EstoqueIngrediente estoqueIngrediente;
    private Medidas tipoMedida;
    private String valorMedida;
    private String exibirConca;

    public String getExibirConca() {
       String valorConcatenado = valorMedida + tipoMedida;
        return valorConcatenado;
    }


}
