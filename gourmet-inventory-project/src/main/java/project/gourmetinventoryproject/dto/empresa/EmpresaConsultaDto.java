package project.gourmetinventoryproject.dto.empresa;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpresaConsultaDto {
    private String nomeFantasia;
    private String cnpj;
    private String telefone;
}
