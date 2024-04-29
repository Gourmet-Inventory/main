package project.gourmetinventoryproject.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.service.UsuarioService;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{cargo}")
    public ResponseEntity<List<Usuario>> getUsuarios(String cargo) {
        if (cargo.equalsIgnoreCase("administrador")){
            List<Usuario> lista = usuarioService.getUsuarios(cargo);
            return lista.isEmpty() ? status(204).build() : status(200).body(lista);
        }
        return status(403).build();
    }

    @PostMapping("/{cargo}")
    public ResponseEntity<Void> postUsuario(@RequestBody @Valid UsuarioCriacaoDto novoUsuario, String cargo) {
        if (cargo.equalsIgnoreCase("administrador")){
            usuarioService.postUsuario(novoUsuario, cargo);
            return status(201).build();
        }
        return status(403).build();
    }

    @DeleteMapping("/{id}/{cargo}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id, @PathVariable String cargo){
        if (cargo.equalsIgnoreCase("administrador")){
            return usuarioService.deleteUsuario(cargo, id);
        }
        return status(403).build();
    }

    @PatchMapping("/{cargo}")
    public ResponseEntity<Void> patchUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioCriacaoDto novoUsuario, String cargo) {
        if (cargo.equalsIgnoreCase("administrador")){
            if (usuarioService.usuarioRepository.existsById(id)) {
                return usuarioService.patchUsuario(id, novoUsuario, cargo);
            }
            return status(204).build();
        }
        return status(403).build();
    }

    @GetMapping("empresas")
    public ResponseEntity<Object> getAllEmpresas() {
        return null;
    }
}
