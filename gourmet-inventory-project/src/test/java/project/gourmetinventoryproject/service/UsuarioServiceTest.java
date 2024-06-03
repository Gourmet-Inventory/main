package project.gourmetinventoryproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.gourmetinventoryproject.api.configuration.security.jwt.GerenciadorTokenJwt;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.repository.UsuarioRepository;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UsuarioServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
    }

    @DisplayName("Deve criar um novo usuário")
    @Test
    void postUsuario() {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        Usuario usuario = new Usuario();
        usuario.setCargo("Cargo");
        usuario.setCelular("Celular");
        usuario.setCpf("Cpf");
        usuario.setEmail("jane.doe@example.org");
        usuario.setIdUsuario(1L);
        usuario.setNome("Nome");
        usuario.setSenha("Senha");
        when(usuarioRepository.save(Mockito.<Usuario>any())).thenReturn(usuario);

        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setCargo("Cargo");
        usuarioCriacaoDto.setCelular("Celular");
        usuarioCriacaoDto.setCpf("Cpf");
        usuarioCriacaoDto.setEmail("jane.doe@example.org");
        usuarioCriacaoDto.setNome("Nome");
        usuarioCriacaoDto.setSenha("Senha");

        // Act
        usuarioService.postUsuario(usuarioCriacaoDto);

        // Assert
        verify(usuarioRepository).save(isA(Usuario.class));
        verify(passwordEncoder).encode(isA(CharSequence.class));
    }

    @DisplayName("Deve retornar todos os usuários")
    @Test
    void getUsuarios() {
        Usuario user1 = new Usuario();
        user1.setNome("User 1");
        Usuario user2 = new Usuario();
        user2.setNome("User 2");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<Usuario> usuarios = usuarioService.getUsuarios();
        assertEquals(2, usuarios.size());
    }

    @DisplayName("Deve deletar um usuário caso exista")
    @Test
    void deleteUsuario() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(true);
        ResponseEntity<Void> response = usuarioService.deleteUsuario(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("Não deve deletar um usuário caso não exista")
    @Test
    void deleteUsuarioNotFound() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<Void> response = usuarioService.deleteUsuario(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @DisplayName("Deve atualizar um uzuario se existir")
    @Test
    void patchUsuario() {
        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setNome("Updated User");
        when(usuarioRepository.existsById(anyLong())).thenReturn(true);
        ResponseEntity<Void> response = usuarioService.patchUsuario(1L, usuarioCriacaoDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("Não deve atualizar um usuario se não existir")
    @Test
    void patchUsuarioNotFound() {
        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setNome("Updated User");
        when(usuarioRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<Void> response = usuarioService.patchUsuario(1L, usuarioCriacaoDto);
        assertEquals(204, response.getStatusCodeValue());
    }
}