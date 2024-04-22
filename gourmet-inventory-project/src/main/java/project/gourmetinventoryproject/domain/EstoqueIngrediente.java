package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class EstoqueIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;
    private String lote;
    private String nome;
    private String categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Double valorMedida;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private Date dtaAviso;
}

