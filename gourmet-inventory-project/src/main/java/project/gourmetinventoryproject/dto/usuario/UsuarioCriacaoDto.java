package project.gourmetinventoryproject.dto.usuario;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@Data
public class UsuarioCriacaoDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String cargo;
    @CPF
    @NotBlank
    private String cpf;
    @Email
    @NotBlank
    private String email;
    private String celular;
    @Size(min = 6)
    //@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-])")//senha forte | Pelo menos uma letra minúscula | Pelo menos uma letra maiúscula | Pelo menos um número | Pelo menos um caractere especial
    @NotBlank
    private String senha;
    private Long idEmpresa;
}
