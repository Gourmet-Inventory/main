package project.gourmetinventoryproject.service;

import com.sun.jdi.event.ModificationWatchpointEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import project.gourmetinventoryproject.api.configuration.security.jwt.GerenciadorTokenJwt;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioConsultaDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioLoginDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.repository.UsuarioRepository;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private ModelMapper modelMapper;

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
        usuario.setEmail("jane.doe@example.org");
        usuario.setIdUsuario(1L);
        usuario.setNome("Nome");
        usuario.setSenha("Senha");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setCargo("Cargo");
        usuarioCriacaoDto.setCelular("Celular");
        usuarioCriacaoDto.setEmail("jane.doe@example.org");
        usuarioCriacaoDto.setNome("Nome");
        usuarioCriacaoDto.setSenha("Senha");


        Usuario usuarioToSave = modelMapper.map(usuarioCriacaoDto, Usuario.class);
        usuarioToSave.setSenha(passwordEncoder.encode(usuarioToSave.getSenha()));
        usuarioService.postUsuario(usuarioCriacaoDto);


        verify(usuarioRepository).save(isA(Usuario.class));
        verify(passwordEncoder).encode(isA(CharSequence.class));
    }

//    @DisplayName("Deve retornar todos os usuários")
//    @Test
//    void getUsuarios() {
//        Usuario user1 = new Usuario();
//        user1.setNome("User 1");
//        Usuario user2 = new Usuario();
//        user2.setNome("User 2");
//        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
//        List<UsuarioConsultaDto> usuarios = usuarioService.getUsuarios();
//        assertEquals(2, usuarios.size());
//    }

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

    @DisplayName("Deve atualizar um usuário se existir")
    @Test
    void patchUsuario() {
        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setNome("Updated User");
        when(usuarioRepository.existsById(anyLong())).thenReturn(true);
        ResponseEntity<Void> response = usuarioService.patchUsuario(1L, usuarioCriacaoDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("Não deve atualizar um usuário se não existir")
    @Test
    void patchUsuarioNotFound() {
        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setNome("Updated User");
        when(usuarioRepository.existsById(anyLong())).thenReturn(false);
        ResponseEntity<Void> response = usuarioService.patchUsuario(1L, usuarioCriacaoDto);
        assertEquals(204, response.getStatusCodeValue());
    }

    @DisplayName("Deve autenticar um usuário válido")
    @Test
    void authenticateValidUser() {
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("jane.doe@example.org");
        usuarioLoginDto.setSenha("password");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(new Usuario()));
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken("user", "password"));

        UsuarioTokenDto result = usuarioService.autenticar(usuarioLoginDto);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any());
        verify(usuarioRepository).findByEmail(anyString());
    }

    @DisplayName("Deve falhar na autenticação de um usuário inexistente")
    @Test
    void authenticateNonExistentUser() {
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("non.existent@example.org");
        usuarioLoginDto.setSenha("password");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.autenticar(usuarioLoginDto));
        verify(authenticationManager, never()).authenticate(any());
    }

//    @DisplayName("Deve realizar o download com sucesso")
//    @Test
//    void downloadFileSuccess() {
//        String fileName = "test";
//        String fileContent = "File content";
//
//        when(GerenciadorArquivoCSV.downloadArquivoCsv(anyString())).thenReturn(fileContent);
//
//        String result = UsuarioService.downloadFile(fileName);
//
//        assertEquals(fileContent, result);
//        verify(GerenciadorArquivoCSV).downloadArquivoCsv(fileName);
//    }
//
//    @DisplayName("Deve gerar uma exeption ao tentar realizar o download")
//    @Test
//    void downloadFileFailure() {
//        String fileName = "test.csv";
//        when(GerenciadorArquivoCSV.downloadArquivoCsv(anyString())).thenThrow(new RuntimeException("File not found"));
//
//        String result = UsuarioService.downloadFile(fileName);
//
//        assertEquals("File not found", result);
//        verify(GerenciadorArquivoCSV).downloadArquivoCsv(fileName);
//        }
}