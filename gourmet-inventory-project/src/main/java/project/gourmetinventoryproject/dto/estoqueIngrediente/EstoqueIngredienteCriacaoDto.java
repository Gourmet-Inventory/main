package project.gourmetinventoryproject.dto.estoqueIngrediente;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredienteCriacaoDto {
    private String lote;
    private Long idEmpresa;
    private String nome;
    private String categoria;
    private Medidas tipoMedida;
    private Integer unidades;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
}
