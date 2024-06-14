package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class EstoqueIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;
    private String lote;
    private Boolean manipulado;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    private String nome;
    private String categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unidades;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
}

