package project.gourmetinventoryproject.dto.empresa;

import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;

public class EmpresaMapper {

    public static Empresa of(EmpresaCriacaoDto empresaCriacaoDto) {
        Empresa empresa = new Empresa();

        empresa.setNomeFantasia(empresaCriacaoDto.getNomeFantasia());
        empresa.setCnpj(empresaCriacaoDto.getCnpj());
        empresa.setTelefone(empresaCriacaoDto.getTelefone());
        // Você pode adicionar a lógica para mapear o responsável com base no ID
        return empresa;
    }
}
