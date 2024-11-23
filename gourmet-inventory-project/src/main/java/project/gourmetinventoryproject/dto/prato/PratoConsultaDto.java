package project.gourmetinventoryproject.dto.prato;


import lombok.*;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.receita.ReceitaConsultaDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PratoConsultaDto {
    private Long idPrato;
    private String nome;
    private Boolean isBebida;
    private String descricao;
    private Double preco;
    private List<String> alergicosRestricoes;
    private String categoria;
    private ReceitaConsultaDto receitaPrato;
    private String foto;
    private String URLAssinada;
}
