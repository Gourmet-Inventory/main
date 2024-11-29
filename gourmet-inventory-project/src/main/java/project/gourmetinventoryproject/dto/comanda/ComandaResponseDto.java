package project.gourmetinventoryproject.dto.comanda;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Prato;
import project.gourmetinventoryproject.dto.prato.PratoConsultaDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComandaResponseDto {
    private Long id;
    private Long idGarcom;
    private String titulo;
    private String mesa;
    private List<PratoConsultaDto> itens;
    private String status;
    private Double total;
}
