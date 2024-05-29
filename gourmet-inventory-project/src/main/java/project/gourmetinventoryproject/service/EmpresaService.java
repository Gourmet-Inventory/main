package project.gourmetinventoryproject.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import project.gourmetinventoryproject.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void postEmpresa(EmpresaCriacaoDto empresaCriacaoDto) {

        Usuario responsavel = usuarioRepository.findById(empresaCriacaoDto.getResponsavelId()).orElseThrow(()-> new IdNotFoundException());

        Empresa novaEmpresa = modelMapper.map(empresaCriacaoDto,Empresa.class);
        novaEmpresa.setResponsavel(responsavel);
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
            Empresa empresa = modelMapper.map(empresaAtualizada, Empresa.class);
            empresa.setIdEmpresa(id);
            empresaRepository.save(empresa);
        }
    }
    public Empresa putEmpresa(Long id, EmpresaCriacaoDto empresaAtualizada){

        if (empresaRepository.existsById(id)){
            Empresa empresa = modelMapper.map(empresaAtualizada,Empresa.class);

            if (empresaAtualizada.getResponsavelId() != null) {
                Usuario responsavel = usuarioRepository.findById(empresaAtualizada.getResponsavelId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid responsavelId"));
                empresa.setResponsavel(responsavel);
            }
            return empresaRepository.save(empresa);
        }
        throw new IdNotFoundException();
    }

    public Optional<Empresa> getEmpresasById(Long id) {
        return empresaRepository.findById(id);
    }
}
