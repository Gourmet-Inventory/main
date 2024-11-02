package project.gourmetinventoryproject.dto.estoqueIngrediente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredienteConsultaDto {
    private Long idItem;
    private Empresa empresa;
    private Boolean manipulado;
    private String lote;
    private String nome;
    private String categoria;
    private Medidas tipoMedida;
    private Integer unitario;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;

    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
   // private List<Ingrediente> receitaManipulado;
    private List<Alerta> Alertas;
    private String marca;
}
