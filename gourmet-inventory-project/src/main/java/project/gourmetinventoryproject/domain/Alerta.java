package project.gourmetinventoryproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Getter
@Setter
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAlerta;
    private String tipoAlerta;
    @ManyToOne
    @JoinColumn(name = "estoque_ingrediente")
    private EstoqueIngrediente estoqueIngrediente;
    @Override
    public String toString() {
        return "Alerta{" +
                "idAlerta=" + idAlerta +
                ", tipoAlerta='" + tipoAlerta + '\'' +
                '}';
    }
}