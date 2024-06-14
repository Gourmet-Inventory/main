package project.gourmetinventoryproject.dto.usuario;

import jakarta.persistence.Column;
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
    @NotBlank(message = "Preencha o campo!")
    private String nome;
    @NotBlank
    private String cargo;
    @Email
    @NotBlank(message = "Preencha o campo!")
    private String email;
    @NotBlank(message = "Preencha o campo!")
    @Pattern(regexp = "^\\(\\d{2}\\) (?:9\\d{4}|\\d{4})-\\d{4}$",message = "Telefone inválido")
    private String celular;
    @Size(min = 6)
    //@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-])")
    @NotBlank
    private String senha;
    private Long idEmpresa;
}
