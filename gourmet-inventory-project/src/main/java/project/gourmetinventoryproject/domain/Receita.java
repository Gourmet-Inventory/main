package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idReceita;
    private Long idPrato;
    private Long idEstoqueIngrediente;
    @OneToMany
    private List<Ingrediente> ingredientes;
}