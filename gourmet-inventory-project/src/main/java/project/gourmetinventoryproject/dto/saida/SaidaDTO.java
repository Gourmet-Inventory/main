package project.gourmetinventoryproject.dto.saida;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SaidaDTO {
    private List<Long> idPratoList;
    private Boolean descontarEstoque;
}
