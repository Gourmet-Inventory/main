package project.gourmetinventoryproject.dto.estoqueIngrediente;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Medidas;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EstoqueIngredienteConsultaDto {
    private Long idItem;
    private String lote;
    private String nome;
    private String categoria;
    private Medidas tipoMedida;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private Date dtaAviso;
}
