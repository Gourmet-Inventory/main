package project.gourmetinventoryproject.dto.estoqueIngrediente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.EstoqueIngrediente;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredientePratosDto {
    private Long idEstoque;
    private String nome;
    private String lote;
    private String nomeConcatenado;

    public String getNomeConcatenado() {
        return String.format("%s - %s",this.nome, this.lote);
    }
}
