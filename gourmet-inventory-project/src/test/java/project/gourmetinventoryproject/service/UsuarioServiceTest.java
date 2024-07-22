package project.gourmetinventoryproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.api.configuration.security.jwt.GerenciadorTokenJwt;
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioConsultaDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioMapper;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioLoginDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.UsuarioRepository;


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

    //Esta anotação é usada para criar um mock da interface PasswordEncoder do Spring Security.
    // A interface PasswordEncoder é usada para codificar senhas antes de armazená-las em um banco de dados
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private EmpresaRepository empresaRepository;

    private Empresa empresa;

    @Mock
    private GerenciadorArquivoCSV gerenciadorArquivoCSV;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        empresa = new Empresa();
        empresa.setIdEmpresa(1L);
        empresa.setNomeFantasia("Empresa Teste");

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));
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
        usuario.setEmpresa(empresa);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setCargo("Cargo");
        usuarioCriacaoDto.setCelular("Celular");
        usuarioCriacaoDto.setEmail("jane.doe@example.org");
        usuarioCriacaoDto.setNome("Nome");
        usuarioCriacaoDto.setSenha("Senha");
        usuarioCriacaoDto.setIdEmpresa(1L);

        Usuario usuarioToSave = UsuarioMapper.of(usuarioCriacaoDto);
        usuarioToSave.setSenha(passwordEncoder.encode(usuarioToSave.getSenha()));
        usuarioService.postUsuario(usuarioCriacaoDto);

        Usuario usuarioSalvo = usuarioRepository.save(usuarioToSave);
        assertEquals(usuario, usuarioSalvo);
    }

    @DisplayName("Deve retornar todos os usuários de uma empresa")
    @Test
    void getUsuarios() {

        Usuario usuario1 = new Usuario();
        usuario1.setCargo("Cargo"); 
        usuario1.setCelular("Celular");
        usuario1.setEmail("jane.doe@example.org");
        usuario1.setIdUsuario(1L);
        usuario1.setNome("Nome");
        usuario1.setSenha("Senha");
        usuario1.setEmpresa(empresa);

        Usuario usuario2 = new Usuario();
        usuario2.setCargo("Cargo");
        usuario2.setCelular("Celular");
        usuario2.setEmail("joe.doe@example.org");
        usuario2.setIdUsuario(2L);
        usuario2.setNome("Nome");
        usuario2.setSenha("Senha");
        usuario2.setEmpresa(empresa);
        when(usuarioRepository.findAllByidEmpresa(1L)).thenReturn(List.of(usuario1, usuario2));

//        List<Usuario> result = usuarioRepository.findAllByidEmpresa(1L);
        List<UsuarioConsultaDto> result = usuarioService.getUsuarios(1L);
        verify(usuarioRepository, times(1)).findAllByidEmpresa(1L);
        assertEquals(2, result.size());
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

    @DisplayName("Deve atualizar um usuário se existir")
    @Test
    void patchUsuario() {
        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        usuarioCriacaoDto.setNome("Updated User");
        Usuario usuario = UsuarioMapper.of(usuarioCriacaoDto);
        usuario.setEmpresa(empresa);
        usuarioRepository.save(usuario);

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Void> response = usuarioService.patchUsuario(1L, usuarioCriacaoDto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @DisplayName("Não deve atualizar um usuário se não existir")
    @Test
    void patchUsuarioNotFound() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        UsuarioCriacaoDto usuarioCriacaoDto = new UsuarioCriacaoDto();
        ResponseEntity<Void> response = usuarioService.patchUsuario(1L, usuarioCriacaoDto);
    }

    @DisplayName("Deve autenticar um usuário válido")
    @Test
    void autenticarUsuarioValido() {
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("jane.doe@example.org");
        usuarioLoginDto.setSenha("password");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(new Usuario()));
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken("user", "password"));

        UsuarioTokenDto result = usuarioService.autenticar(usuarioLoginDto);

        assertNotNull(result);
    }

    @DisplayName("Deve falhar na autenticação de um usuário inexistente")
    @Test
    void authenticateNonExistentUser() {
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto();
        usuarioLoginDto.setEmail("non.existent@example.org");
        usuarioLoginDto.setSenha("password");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.autenticar(usuarioLoginDto));
    }

    @Test
    void downloadFileReturnsContentOnSuccess() {
        String fileName = "validFile";
        String expectedContent = "file content";
        String result;

        try (MockedStatic<GerenciadorArquivoCSV> mockedStatic = Mockito.mockStatic(GerenciadorArquivoCSV.class)) {
            mockedStatic.when(() -> GerenciadorArquivoCSV.downloadArquivoCsv(fileName)).thenReturn(expectedContent);

            result = usuarioService.downloadFile(fileName);
        }

        assertEquals(expectedContent, result);
    }

//    @Test
//    void downloadFileReturnsErrorMessageOnFailure() {
//        String fileName = "invalidFile";
//        Exception e = new Exception();
//        String result;
//
//        try (MockedStatic<GerenciadorArquivoCSV> mockedStatic = Mockito.mockStatic(GerenciadorArquivoCSV.class)) {
//            mockedStatic.when(() -> GerenciadorArquivoCSV.downloadArquivoCsv(fileName)).thenThrow(new Exception());
//            // Your test code here
//            result = usuarioService.downloadFile(fileName);
//        }
//
//        assertNotNull(result);
//    }
}