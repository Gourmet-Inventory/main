package project.gourmetinventoryproject.dto.usuario.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.dto.empresa.EmpresaResumidaDto;

@Getter
@Setter
public class UsuarioTokenDto {
    private Long idUsuario;
    private String nome;
    private String email;
    private String token;
    private String cargo;
    private EmpresaResumidaDto empresa;
    private String celular;
}
