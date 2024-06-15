package project.gourmetinventoryproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private String lote;
    private Boolean manipulado;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    private String categoria;
    @Enumerated(EnumType.STRING)
    private Medidas tipoMedida;
    private Integer unitario;
    private Double valorMedida;
    private Double valorTotal;
    private String localArmazenamento;
    private LocalDate dtaCadastro;
    private LocalDate dtaAviso;
    @OneToMany(mappedBy = "estoqueIngrediente")
    private List<Ingrediente> receitaManipulado;
    @JsonIgnore
    @OneToMany(mappedBy = "idAlerta")
    private List<Alerta> Alertas;

    public void addAlerta(Alerta alerta) {
        Alertas.add(alerta);
    }
}

