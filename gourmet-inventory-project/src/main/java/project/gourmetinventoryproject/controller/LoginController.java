package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.service.UsuarioService;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/login")
public class LoginController {

//    @Autowired
//    private UsuarioService usuarioService;
//
//    @GetMapping()
//    public String index() {
//        return "login/index";
//    }
//
//    @PostMapping
//    public ResponseEntity<String> login(@RequestBody UsuarioTokenDto usuarioTokenDto) {
//        UsuarioTokenDto usuarioToken = this.usuarioService.authenticate(usuarioTokenDto);
//
//        return usuario != null ? status(200).body("redirect:/?") : status(401).body("Email ou Senha Incorreta");
//    }
}
