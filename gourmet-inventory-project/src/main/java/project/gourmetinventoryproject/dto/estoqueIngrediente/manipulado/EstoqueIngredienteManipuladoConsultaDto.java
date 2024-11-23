package project.gourmetinventoryproject.dto.estoqueIngrediente.manipulado;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Alerta;
import project.gourmetinventoryproject.domain.CategoriaEstoque;
import project.gourmetinventoryproject.domain.Medidas;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueIngredienteManipuladoConsultaDto {
    private Long idItem;
    private Boolean manipulado;
    private String nome;
    private String lote;
    private CategoriaEstoque categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unitario;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
    private String descricao;
    private ReceitaConsultaDto receita;
    private List<Alerta> Alertas;
}
