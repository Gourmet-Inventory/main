package project.gourmetinventoryproject.dto.empresa;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class EmpresaCriacaoDto {

    @NotBlank
    private String nomeFantasia;

    @NotBlank
    private String cnpj;

    @NotBlank
    private String telefone;

    @NotBlank
    private Long responsavelId;
}
