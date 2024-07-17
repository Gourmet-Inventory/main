package project.gourmetinventoryproject.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import project.gourmetinventoryproject.domain.Empresa;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.UsuarioConsultaDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioCriacaoDto;
import project.gourmetinventoryproject.dto.usuario.UsuarioMapper;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioLoginDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;
import project.gourmetinventoryproject.exception.IdNotFoundException;
import project.gourmetinventoryproject.repository.EmpresaRepository;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmpresaRepository empresaRepository;


    public void postUsuario(UsuarioCriacaoDto usuarioCriacaoDto){
        Empresa empresa = empresaRepository.findById(usuarioCriacaoDto.getIdEmpresa()).orElseThrow(()-> new IdNotFoundException());
//        Usuario novoUsuario = modelMapper.map(usuarioCriacaoDto, Usuario.class);
        Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);
        String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenha());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setEmpresa(empresa);
        usuarioRepository.save(novoUsuario);
    }

    public List<UsuarioConsultaDto> getUsuarios(Long idEmpresa){
        List<Usuario> lista = usuarioRepository.findAllByidEmpresa(idEmpresa);
            if (lista.isEmpty()){
                throw new ResponseStatusException(HttpStatusCode.valueOf(204));
            }
            List<UsuarioConsultaDto> listaDto = modelMapper.map(lista,new TypeToken<List<UsuarioConsultaDto>>(){}.getType());
        return listaDto;
    }

    public ResponseEntity<Void> deleteUsuario(Long id){
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return status(200).build();
        }
        return status(404).build();
    }

    public ResponseEntity<Void> patchUsuario(Long id, UsuarioCriacaoDto usuarioCriacaoDto) {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuarioOptional.isPresent()) {
                Usuario usuarioExistente = usuarioOptional.get();

                if (usuarioCriacaoDto.getNome() != null) {
                    usuarioExistente.setNome(usuarioCriacaoDto.getNome());
                }
                if (usuarioCriacaoDto.getCargo() != null) {
                    usuarioExistente.setCargo(usuarioCriacaoDto.getCargo());
                }
                if (usuarioCriacaoDto.getEmail() != null) {
                    usuarioExistente.setEmail(usuarioCriacaoDto.getEmail());
                }
                if (usuarioCriacaoDto.getSenha() != null) {
                    usuarioExistente.setSenha(usuarioCriacaoDto.getSenha());
                }
                if (usuarioCriacaoDto.getIdEmpresa() != null) {
                    Empresa empresa = empresaRepository.findById(usuarioCriacaoDto.getIdEmpresa()).orElseThrow(()-> new IdNotFoundException());
                    usuarioExistente.setEmpresa(empresa);
                }


                usuarioRepository.save(usuarioExistente);
                return ResponseEntity.status(200).build();
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioLoginDto.getEmail());

        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não cadastrado", null);
        } else {
            final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                    usuarioLoginDto.getEmail(), usuarioLoginDto.getSenha());

            final Authentication authentication = this.authenticationManager.authenticate(credentials);

            usuarioOptional.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email do usuário não cadastrado", null)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = gerenciadorTokenJwt.generateToken(authentication);

            UsuarioTokenDto usuarioTokenDto = modelMapper.map(usuarioOptional.get(), UsuarioTokenDto.class);
            usuarioTokenDto.setToken(token);

            return usuarioTokenDto;
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
