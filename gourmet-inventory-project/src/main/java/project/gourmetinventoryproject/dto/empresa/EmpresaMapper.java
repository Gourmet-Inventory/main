package project.gourmetinventoryproject.dto.empresa;

import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;

public class EmpresaMapper {

    public static Empresa of(EmpresaCriacaoDto empresaCriacaoDto) {
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(empresaCriacaoDto.getNomeFantasia());
        empresa.setCnpj(empresaCriacaoDto.getCnpj());
        empresa.setTelefone(empresaCriacaoDto.getTelefone());
        return empresa;
    }
}
