package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import project.gourmetinventoryproject.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.empresa.EmpresaCriacaoDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    ModelMapper mapper = new ModelMapper();

    public void postEmpresa(EmpresaCriacaoDto empresaCriacaoDto) {
        Empresa novaEmpresa = mapper.map(empresaCriacaoDto, Empresa.class);
        empresaRepository.save(novaEmpresa);
    }

//    public void save(Empresa empresa, Integer idUsuario) {
//        Optional<Usuario> usuario = usuarioRepository.findById(Long.valueOf(idUsuario));
//        empresa.setResponsavel(usuario.get());
//        empresaRepository.save(empresa);
//    }

    public List<Empresa> getEmpresas() {
        return empresaRepository.findAll();
    }

    public void deleteEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }

    public void patchEmpresa(Long id, EmpresaCriacaoDto empresaAtualizada) {
        if (empresaRepository.existsById(id)) {
            Empresa empresa = mapper.map(empresaAtualizada, Empresa.class);
            empresa.setIdEmpresa(id);
            empresaRepository.save(empresa);
        }
    }
    public Empresa putEmpresa(Long id, EmpresaCriacaoDto empresaAtualizada){
        if (empresaRepository.existsById(id)){
            var empresa = mapper.map(empresaAtualizada, Empresa.class);
            empresa.setIdEmpresa(id);
            return empresaRepository.save(empresa);
        }
        throw new IdNotFoundException();
    }

    public Optional<Empresa> getEmpresasById(Long id) {
        return empresaRepository.findById(id);
    }
}
