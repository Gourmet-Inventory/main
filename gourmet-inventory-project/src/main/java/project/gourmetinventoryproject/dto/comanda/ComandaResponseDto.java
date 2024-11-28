package project.gourmetinventoryproject.dto.comanda;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComandaResponseDto {
    private Long id;
    private String titulo;
    private String mesa;
    private String status;
    private Double total;
}
