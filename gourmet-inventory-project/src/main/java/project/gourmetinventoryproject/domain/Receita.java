package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idReceita;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idPrato", nullable = true)
    private Prato prato;
    private Long idEstoqueIngrediente;
    private Boolean isPrato;
    @OneToMany
    private List<Ingrediente> ingredientes;
}