package project.gourmetinventoryproject.dto.prato;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PratoCriacaoDto {
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
}
