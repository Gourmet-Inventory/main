package project.gourmetinventoryproject.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.gourmetinventoryproject.domain.Empresa;

@Getter
@AllArgsConstructor
public class UsuarioConsultaDto {
    private String nome;
    private String cargo;
    private String cpf;
    private String email;
    private String celular;
    private Empresa Empresa;
}
