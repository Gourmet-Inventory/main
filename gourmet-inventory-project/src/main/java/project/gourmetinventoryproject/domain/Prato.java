package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrato;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
    @OneToMany
    @JoinTable(
            name = "prato_ingrediente",
            joinColumns = @JoinColumn(name = "prato_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> receitaPrato;
    @ElementCollection
    @CollectionTable(name = "prato_alergicos_restricoes", joinColumns = @JoinColumn(name = "prato_id"))
    @Column(name = "restricao")
    private List<String> alergicosRestricoes;
    private byte[] foto;
    @ManyToMany(mappedBy = "itens")
    private List<Comanda> comandas;
}
