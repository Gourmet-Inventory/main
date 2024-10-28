package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
@Getter
@Setter
public class ItemListaCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idItemLista;
    private String nome;
    private Integer qtdMedia;
    @OneToOne
    private EstoqueIngrediente estoqueIngrediente;
}
