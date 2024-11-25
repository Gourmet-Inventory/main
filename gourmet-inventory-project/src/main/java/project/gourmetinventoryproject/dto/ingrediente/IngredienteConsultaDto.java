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
    private String nome;
    private Medidas tipoMedida;
    private Double valorMedida;
    private String exibirConca;

    public String getExibirConca() {
       String valorConcatenado;
        valorConcatenado = this.valorMedida + "-" + this.tipoMedida;
        return valorConcatenado;
    }
}
