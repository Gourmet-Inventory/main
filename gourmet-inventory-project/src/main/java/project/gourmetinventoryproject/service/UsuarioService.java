package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioMapper;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void postUsuario(UsuarioCriacaoDto usuarioCriacaoDto, String cargo){//???
        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);
        usuarioRepository.save(novoUsuario);
    }

    public List<Usuario> getUsuarios(String cargo){ //???
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public ResponseEntity<Void> deleteUsuario(String cargo, Long id){ //???
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return status(200).build();
        }
        return status(404).build();
    }

    public ResponseEntity<Void> patchUsuario(Long id, UsuarioCriacaoDto usuarioCriacaoDto, String cargo){
        if (usuarioRepository.existsById(id)) {
            Usuario newUsuario = UsuarioMapper.of(usuarioCriacaoDto);
            usuarioRepository.save(newUsuario);
            return status(200).build();
        }
        return status(204).build();
    }

    public ResponseEntity<Object> getEmpresasUsuario(){
        return null;
    }
}
