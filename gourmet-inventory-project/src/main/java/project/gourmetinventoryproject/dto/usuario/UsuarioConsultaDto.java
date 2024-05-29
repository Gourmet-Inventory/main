package project.gourmetinventoryproject.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioConsultaDto {
    private String nome;
    private String cargo;
    private String cpf;
    private String email;
    private String celular;
}
