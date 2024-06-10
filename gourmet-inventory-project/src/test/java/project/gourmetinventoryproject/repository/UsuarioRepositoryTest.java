package project.gourmetinventoryproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.gourmetinventoryproject.domain.Usuario;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void findByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@eteste.com");
        usuario.setSenha("123456");
        usuario.setNome("Teste");
        usuario.setCargo("Administrador");
        usuarioRepository.save(usuario);

        Optional<Usuario> found = usuarioRepository.findByEmail(usuario.getEmail());
        assertThat(found.get().getEmail()).isEqualTo(usuario.getEmail());

    }
}