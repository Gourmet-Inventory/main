package project.gourmetinventoryproject.dto.prato;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PratoConsultaDto {
    private Long idPrato;
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
}
