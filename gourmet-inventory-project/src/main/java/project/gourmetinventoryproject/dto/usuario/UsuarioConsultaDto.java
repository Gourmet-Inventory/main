package project.gourmetinventoryproject.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.dto.empresa.EmpresaResumidaDto;

@Getter
@NoArgsConstructor
public class UsuarioConsultaDto {
    private String nome;
    private String cargo;
    private String cpf;
    private String email;
    private String celular;
    private EmpresaResumidaDto Empresa;
}
