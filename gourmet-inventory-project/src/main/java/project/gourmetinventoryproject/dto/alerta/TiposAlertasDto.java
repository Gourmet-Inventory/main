package project.gourmetinventoryproject.dto.alerta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TiposAlertasDto {

    private int estoqueVazioQtd;
    private int estoqueAcabandoQtd;
    private int diaChecagemQtd;
    private int dataProximaQtd;
    private int somaTotalAlertar;

}
