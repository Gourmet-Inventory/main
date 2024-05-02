package project.gourmetinventoryproject.dto.estoqueIngrediente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class EstoqueIngredienteCriacaoDto {
    private Long idItem;
    private String lote;
    private String nome;
    private String categoria;
    private Medidas tipoMedida;
    private Double valorMedida;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private Date dtaAviso;
}
