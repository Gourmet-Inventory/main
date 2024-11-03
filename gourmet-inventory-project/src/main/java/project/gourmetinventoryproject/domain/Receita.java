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
//    @OneToOne(optional = true)
//    @JoinColumn(name = "prato_id", nullable = true)
//    private Prato prato;
    private Long idEstoqueIngrediente;
    @OneToMany
    private List<Ingrediente> ingredientes;
}