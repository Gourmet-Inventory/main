package project.gourmetinventoryproject.dto.prato;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import project.gourmetinventoryproject.domain.AlergicosRestricoes;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Ingrediente;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteConsultaDto;
import project.gourmetinventoryproject.dto.ingrediente.IngredienteCriacaoDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
public class PratoCriacaoDto {
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
    private List<IngredienteCriacaoDto> receitaPrato;
    private List<String> alergicosRestricoes;
    private MultipartFile foto;
}
