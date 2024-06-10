package project.gourmetinventoryproject.dto.usuario;

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
public class UsuarioCriacaoDto {

    @Size(min = 1)
    private String nome;
    private String cargo;
    @CPF
    private String cpf;
    @Email
    private String email;
    private String celular;
    @Size(min = 8)
    //@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-])")//senha forte | Pelo menos uma letra minúscula | Pelo menos uma letra maiúscula | Pelo menos um número | Pelo menos um caractere especial
    @NotBlank
    private String senha;
    private Long idEmpresa;
}
