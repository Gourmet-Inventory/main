package project.gourmetinventoryproject.dto.usuario.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioDetalhesDto;
import project.gourmetinventoryproject.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);

        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UsuarioDetalhesDto(usuarioOptional.get());
    }
}
