package project.gourmetinventoryproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    private Boolean manipulado;
    private String nome;
    private String lote;
    private String marca;
    @Enumerated(EnumType.STRING)
    private CategoriaEstoque categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unitario;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
    private String descricao;
    @OneToMany(mappedBy = "idAlerta", fetch=FetchType.EAGER)
    @JsonIgnore
    private List<Alerta> Alertas;
    public void baixarEstoque(Double quantidadeUsada) {
        this.valorMedida -= quantidadeUsada;
    }
}

