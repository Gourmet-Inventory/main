package project.gourmetinventoryproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.gourmetinventoryproject.GerenciadorArquivoCSV;
import project.gourmetinventoryproject.api.configuration.security.jwt.GerenciadorTokenJwt;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioMapper;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioLoginDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class UsuarioService {

    @Autowired
    public UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void postUsuario(UsuarioCriacaoDto usuarioCriacaoDto){//???
        final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        usuarioRepository.save(novoUsuario);
    }

    public List<Usuario> getUsuarios(){ //???
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public ResponseEntity<Void> deleteUsuario(Long id){ //???
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return status(200).build();
        }
        return status(404).build();
    }

    public ResponseEntity<Void> patchUsuario(Long id, UsuarioCriacaoDto usuarioCriacaoDto){
        if (usuarioRepository.existsById(id)) {
            Usuario newUsuario = UsuarioMapper.of(usuarioCriacaoDto);
            usuarioRepository.save(newUsuario);
            return status(200).build();
        }
        return status(204).build();
    }

//    public ResponseEntity<Object> getEmpresasUsuario(){
//        return null;
//    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioLoginDto.getEmail());

        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não cadastrado", null);
        }else {
            final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                    usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

            final Authentication authentication = this.authenticationManager.authenticate(credentials);
            usuarioOptional
                    .orElseThrow(
                            () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = gerenciadorTokenJwt.generateToken(authentication);
            return UsuarioMapper.of(usuarioOptional.orElse(null), token);
        }
    }

    public static String downloadFile(String fileName /* ,HttpServletResponse response*/) {
        try {
            return GerenciadorArquivoCSV.downloadArquivoCsv(fileName);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
