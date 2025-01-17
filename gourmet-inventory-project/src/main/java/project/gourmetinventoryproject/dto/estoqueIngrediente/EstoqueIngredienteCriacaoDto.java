package project.gourmetinventoryproject.dto.estoqueIngrediente;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.CategoriaEstoque;
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
    private String nome;
    private String lote;
    private Boolean manipulado;
    private String marca;
    @Enumerated(EnumType.STRING)
    private CategoriaEstoque categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unitario;
    @NotNull
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    @PastOrPresent
    private LocalDate dtaCadastro;
    @FutureOrPresent
    private LocalDate dtaAviso;
    private String descricao;
}
