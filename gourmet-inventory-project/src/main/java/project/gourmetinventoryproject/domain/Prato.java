package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.*;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;

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
    private Boolean isBebida;
    private String descricao;
    private Double preco;
    private String categoria;
    @ElementCollection
    @CollectionTable(name = "prato_alergicos_restricoes", joinColumns = @JoinColumn(name = "prato_id"))
    @Column(name = "restricao")
    private List<String> alergicosRestricoes;
    @ManyToMany(mappedBy = "itens")
    private List<Comanda> comandas;
    private String foto;
    @Column(name = "URLASSINADA", length = 2000)
    private String URLAssinada;

    @Override
    public String toString() {
        return "Prato{" +
                "idPrato=" + idPrato +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
