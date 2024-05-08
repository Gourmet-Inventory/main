package project.gourmetinventoryproject.service;

import project.gourmetinventoryproject.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;
import project.gourmetinventoryproject.dto.empresa.EmpresaMapper;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.EmpresaRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public void postEmpresa(EmpresaCriacaoDto empresaCriacaoDto) {
        Empresa novaEmpresa = EmpresaMapper.of(empresaCriacaoDto);
        empresaRepository.save(novaEmpresa);
    }
    public List<Empresa> getEmpresas() {
        return empresaRepository.findAll();
    }

    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }

    public void patchEmpresa(Long id, EmpresaCriacaoDto empresaAtualizada) {
        if (empresaRepository.existsById(id)) {
            Empresa empresa = EmpresaMapper.of(empresaAtualizada);
            empresa.setIdEmpresa(id);
            empresaRepository.save(empresa);
        }
    }
    public Empresa putEmpresa(Long id, EmpresaCriacaoDto empresaAtualizada){
        if (empresaRepository.existsById(id)){
            var empresa = EmpresaMapper.of(empresaAtualizada);
            empresa.setIdEmpresa(id);
            return empresaRepository.save(empresa);
        }
        throw new IdNotFoundException();
    }

    public Optional<Empresa> getEmpresasById(Long id) {
        return empresaRepository.findById(id);
    }
}
