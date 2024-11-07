package project.gourmetinventoryproject.dto.estoqueIngrediente;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredienteConsultaDto {
    private Long idItem;
    private Boolean manipulado;
    private String nome;
    private String lote;
    private CategoriaEstoque categoria;
    private String marca;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unitario;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;

    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
    private String descricao;
    private List<Alerta> Alertas;
}
