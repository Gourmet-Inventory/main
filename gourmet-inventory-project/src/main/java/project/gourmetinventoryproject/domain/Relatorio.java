package project.gourmetinventoryproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private LocalDate data;
    @OneToMany
    private List<Prato> pratosSaidos;
    private Double valorBruto;
}
