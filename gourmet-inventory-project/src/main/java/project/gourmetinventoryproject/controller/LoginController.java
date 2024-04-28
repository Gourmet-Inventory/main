package project.gourmetinventoryproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/login")
public class LoginController {

//    @Autowired
//    private UsuarioRepository repository;
//
//    @GetMapping()
//    public String index() {
//        return "login/index";
//    }

//    @PostMapping("/{email}/{senha}")
//    public ResponseEntity<String> login(@PathVariable String email, @PathVariable String senha) {
//        Usuario usuario = this.repository.login(email, senha);
//        return usuario != null ? status(200).body("redirect:/?") : status(401).body("Email ou Senha Incorreta");
//    }
}
