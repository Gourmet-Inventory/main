package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRelatorio;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;
    @ManyToMany
    @JoinTable(
            name = "relatorio_prato",
            joinColumns = @JoinColumn(name = "relatorio_id"),
            inverseJoinColumns = @JoinColumn(name = "prato_id")
    )
    private List<Prato> pratoList;
    private Double valorBruto;
}