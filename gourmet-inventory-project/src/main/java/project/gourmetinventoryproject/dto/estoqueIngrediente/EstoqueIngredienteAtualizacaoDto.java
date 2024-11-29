package project.gourmetinventoryproject.dto.estoqueIngrediente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.CategoriaEstoque;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueIngredienteAtualizacaoDto {
    private String nome;
    private String lote;
    private String marca;
    private CategoriaEstoque categoria;
    private Medidas tipoMedida;
    private Integer unitario;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
    private String descricao;
    }
