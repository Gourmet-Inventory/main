package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Comanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "comanda_prato",
            joinColumns = @JoinColumn(name = "comanda_id"),
            inverseJoinColumns = @JoinColumn(name = "prato_id")
    )
    private List<Prato> itens;
    private String status;
    private Double total = 0.0;

    public void calcularTotal() {
        this.total = itens.stream()
                .mapToDouble(Prato::getPreco)
                .sum();
    }
}
