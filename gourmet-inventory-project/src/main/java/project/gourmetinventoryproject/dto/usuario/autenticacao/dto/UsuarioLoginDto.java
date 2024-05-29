package project.gourmetinventoryproject.dto.usuario.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDto {
    private String email;
    private String senha;
}
