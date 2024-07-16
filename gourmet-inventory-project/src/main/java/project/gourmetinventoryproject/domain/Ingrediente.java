package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Getter
@Setter
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngrediente;
    @ManyToOne
    @JoinColumn(name = "id_estoque_ingrediente")
    private EstoqueIngrediente estoqueIngrediente;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Double valorMedida;
}

