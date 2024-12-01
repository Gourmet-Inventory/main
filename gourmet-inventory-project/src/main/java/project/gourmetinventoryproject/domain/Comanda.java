package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Comanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idGarcom;
    private String titulo;
    private String mesa;

    @ManyToMany
    @JoinTable(
            name = "comanda_prato",
            joinColumns = @JoinColumn(name = "comanda_id"),
            inverseJoinColumns = @JoinColumn(name = "prato_id")
    )
    private List<Prato> itens;
    private String status;
    private Double total;
    private LocalDate data;

    public void calcularTotal() {
        this.total = itens.stream()
                .mapToDouble(Prato::getPreco)
                .sum();
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", mesa='" + mesa + '\'' +
                ", status='" + status + '\'' +
                ", total=" + total +
                '}';
    }
}
