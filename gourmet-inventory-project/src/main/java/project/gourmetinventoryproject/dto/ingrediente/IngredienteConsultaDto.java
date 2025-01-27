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
    private String nome;
    private String tipoMedida;
    private Double valorMedida;
    private String exibirConca;

    public String getExibirConca() {
       String valorConcatenado;
        valorConcatenado = this.valorMedida + "-" + this.tipoMedida;
        return valorConcatenado;
    }
}
