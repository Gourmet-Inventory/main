package project.gourmetinventoryproject.dto.estoqueIngrediente;


import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueIngredienteCriacaoDto {
    @NotBlank
    private String lote;
    private Boolean manipulado;
    @NotBlank
    private String nome;
    @NotBlank
    private String categoria;
    private Medidas tipoMedida;
    private Integer unitario;
    @NotNull
    private Double valorMedida;
    private String localArmazenamento;
    @PastOrPresent
    private LocalDate dtaCadastro;
    @FutureOrPresent
    private LocalDate dtaAviso;
    @NotBlank
    private String marca;
}
