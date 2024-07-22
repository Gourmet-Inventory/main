package project.gourmetinventoryproject.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EmpresaCriacaoDto {

    @NotBlank
    private String nomeFantasia;

    @NotBlank
    @CNPJ
    private String cnpj;

    @NotBlank
    @Pattern(regexp = "^\\(\\d{2}\\) (?:9\\d{4}|\\d{4})-\\d{4}$")
    private String telefone;
}
