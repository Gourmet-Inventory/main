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
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioConsultaDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioMapper;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioLoginDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.repository.UsuarioRepository;
import project.gourmetinventoryproject.service.UsuarioService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Obter Lista de usuarios", method = "GET")
    @GetMapping()
    public ResponseEntity<List<UsuarioConsultaDto>> getUsuarios() {
            List<UsuarioConsultaDto> lista = usuarioService.getUsuarios();
            return lista.isEmpty() ? status(204).build() : status(200).body(lista);
    }

    @Operation(summary = "Criar novo usuario", method = "POST")
    @PostMapping()
    public ResponseEntity<Void> postUsuario(@RequestBody @Valid UsuarioCriacaoDto novoUsuario) {
            usuarioService.postUsuario(novoUsuario);
            return status(201).build();
    }

    @Operation(summary = "Deletar usuario por id", method = "DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
            return usuarioService.deleteUsuario(id);
    }

    @Operation(summary = "Atualizar usuario por id", method = "PATCH")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> patchUsuario(@PathVariable Long userId, @RequestBody @Valid UsuarioCriacaoDto novoUsuario) {
            if (usuarioService.usuarioRepository.existsById(userId)) {
                return usuarioService.patchUsuario(userId, novoUsuario);
            }
            return status(204).build();
    }

    @ApiIgnore
    @GetMapping("/empresas")
    public ResponseEntity<Object> getAllEmpresas() {
        return null;
    }

    @Operation(summary = "Logar para receber o token", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "Logado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "{\"email\": \"peterson@example.com\",\"senha\": \"123456\"}")})}),
            @ApiResponse(responseCode ="404", description = "Usuario não encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto) {
        UsuarioTokenDto usuarioToken = this.usuarioService.autenticar(usuarioLoginDto);

//        UsuarioService.LoginUserResponse response = new UsuarioService.LoginUserResponse();
//        response.setToken(usuarioToken.getToken());
//        response.setEmail(usuarioToken.getEmail());
//        response.setIdUsuario(usuarioToken.getUserId());
        return status(200).body(usuarioToken);
    }

    @Operation(summary = "Download do arquivo.csv dos usuários com cargo de Administrador", method = "GET")
    @GetMapping("/csv/{nomeArquivo}")
    public ResponseEntity<String> downloadCsvUsuariosAdm(@PathVariable String nomeArquivo) {
//        if (nomeArquivo.isBlank()){
//            throw new ResponseStatusException(411, "Preencha um nome para gerar o arquivo", null);
//        }else {
            List<Usuario> usuarioList = usuarioRepository.findByCargoEqualsIgnoreCase("administrador");
            //List<Usuario> usuarioList = usuarioRepository.findAll();
            //List<UsuarioDetalhesDto> usuarioDetalhesDtoList = UsuarioMapper.toDto(usuarioList);
            GerenciadorArquivoCSV.gravaArquivoCsvUsuario(UsuarioMapper.toDto(usuarioList), nomeArquivo);
       //}
        String arquivo = UsuarioService.downloadFile(nomeArquivo);
        return arquivo.equals("Download concluído com sucesso!") ? status(200).body(arquivo) : status(404).build();
    }
}
