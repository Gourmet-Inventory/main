package project.gourmetinventoryproject.dto.prato;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PratoConsultaDto {
    private Long idPrato;
    private String nome;
    private String descricao;
    private Double preco;
    private List<String> alergicosRestricoes;
    private String categoria;
    private List<IngredienteConsultaDto> receitaPrato;
}
