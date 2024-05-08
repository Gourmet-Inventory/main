package project.gourmetinventoryproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioMapper;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioLoginDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.repository.UsuarioRepository;
import project.gourmetinventoryproject.service.UsuarioService;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{cargo}")
    public ResponseEntity<List<Usuario>> getUsuarios(@PathVariable String cargo) {
        if (cargo.equalsIgnoreCase("administrador")){
            List<Usuario> lista = usuarioService.getUsuarios(cargo);
            return lista.isEmpty() ? status(204).build() : status(200).body(lista);
        }
        throw new ResponseStatusException(403, "Cargo insuficiente", null);
    }

    @PostMapping("/{cargo}")
    public ResponseEntity<Void> postUsuario(@RequestBody @Valid UsuarioCriacaoDto novoUsuario, String cargo) {
        if (cargo.equalsIgnoreCase("administrador")){
            usuarioService.postUsuario(novoUsuario, cargo);
            return status(201).build();
        }
        throw new ResponseStatusException(403, "Cargo insuficiente", null);
    }

    @DeleteMapping("/{id}/{cargo}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id, @PathVariable String cargo){
        if (cargo.equalsIgnoreCase("administrador")){
            return usuarioService.deleteUsuario(cargo, id);
        }
        throw new ResponseStatusException(403, "Cargo insuficiente", null);
    }

    @PatchMapping("/{cargo}")
    public ResponseEntity<Void> patchUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioCriacaoDto novoUsuario, String cargo) {
        if (cargo.equalsIgnoreCase("administrador")){
            if (usuarioService.usuarioRepository.existsById(id)) {
                return usuarioService.patchUsuario(id, novoUsuario, cargo);
            }
            return status(204).build();
        }
        throw new ResponseStatusException(403, "Cargo insuficiente", null);
    }

    @GetMapping("/empresas")
    public ResponseEntity<Object> getAllEmpresas() {
        return null;
    }

    @Operation(summary = "Buscar estoque de ingredientes por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Logado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"email\": \"peterson@example.com\",\"senha\": \"123456\"}")})}),
            @ApiResponse(responseCode ="404", description = "Usuario não encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);
        return status(200).body(usuarioToken);
    }


    @Operation(summary = "Download do arquivo.csv dos usuários com cargo de Administrador", method = "GET")
    @GetMapping("/csv")
    public ResponseEntity<String> downloadCsvUsuariosAdm(@RequestParam (value = "Nome do Arquivo") String nomeArquivo) {
        List<Usuario> usuarioList = usuarioRepository.findByCargoEqualsIgnoreCase("administrador");
        //List<UsuarioDetalhesDto> usuarioDetalhesDtoList = UsuarioMapper.toDto(usuarioList);
        GerenciadorArquivoCSV.gravaArquivoCsvUsuario(UsuarioMapper.toDto(usuarioList), nomeArquivo);

        return usuarioService.downloadFile(nomeArquivo).equals("Download concluído com sucesso!") ? status(200).build() : status(404).build();
    }
}
