package project.gourmetinventoryproject.dto.prato;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.AlergicosRestricoes;
import project.gourmetinventoryproject.domain.Empresa;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PratoCriacaoDto {
    private Long idEmpresa;
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
    private List<AlergicosRestricoes> alergicosRestricoes;
}
