package project.gourmetinventoryproject.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.gourmetinventoryproject.dto.empresa.EmpresaResumidaDto;

@Getter
@NoArgsConstructor
public class UsuarioConsultaDto {
    private Long idUsuario;
    private String nome;
    private String cargo;
    private String email;
    private String celular;
    private EmpresaResumidaDto Empresa;}