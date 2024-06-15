package project.gourmetinventoryproject.dto.estoqueIngrediente;


import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredienteCriacaoDto {
    private String lote;
    private Boolean manipulado;
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
