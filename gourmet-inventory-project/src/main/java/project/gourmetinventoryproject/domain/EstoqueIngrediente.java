package project.gourmetinventoryproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class EstoqueIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItem;
    @NotBlank
    private String lote;
    private Boolean manipulado;
    @NotBlank
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    @NotBlank
    private String categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unitario;
    @NotNull
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    @PastOrPresent
    private LocalDate dtaCadastro;
    @FutureOrPresent
    private LocalDate dtaAviso;
    //@OneToMany(mappedBy = "estoqueIngrediente")
   // private List<Ingrediente> receitaManipulado;
    @OneToMany(mappedBy = "idAlerta", fetch=FetchType.EAGER)
    @JsonIgnore
    private List<Alerta> Alertas;
    public void addAlerta(Alerta alerta) {
        Alertas.add(alerta);
    }
    public void baixarEstoque(Double quantidadeUsada) {
        this.valorTotal -= quantidadeUsada;
    }

    @Override
    public String toString() {
        return "EstoqueIngrediente{" +
                "idItem=" + idItem +
                ", lote='" + lote + '\'' +
                ", manipulado=" + manipulado +
                ", nome='" + nome + '\'' +
                ", empresa=" + empresa +
                ", categoria='" + categoria + '\'' +
                ", tipoMedida=" + tipoMedida +
                ", unitario=" + unitario +
                ", valorMedida=" + valorMedida +
                ", valorTotal=" + valorTotal +
                ", localArmazenamento='" + localArmazenamento + '\'' +
                ", dtaCadastro=" + dtaCadastro +
                ", dtaAviso=" + dtaAviso +
                '}';
    }
}

