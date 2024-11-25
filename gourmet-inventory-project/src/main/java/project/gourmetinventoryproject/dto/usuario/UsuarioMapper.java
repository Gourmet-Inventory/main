package project.gourmetinventoryproject.dto.usuario;

import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioDetalhesDto;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setCargo(usuarioCriacaoDto.getCargo());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setCelular(usuarioCriacaoDto.getCelular());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        return usuario;
    }

    public static List<UsuarioDetalhesDto> toDto(List<Usuario> usuarioList) {
        return usuarioList
                .stream()
                .map(UsuarioDetalhesDto::new)
                .collect(Collectors.toList());
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto() ;

        usuarioTokenDto.setIdUsuario( usuario.getIdUsuario());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome()); 
        usuarioTokenDto.setToken(token);
        usuarioTokenDto.setCargo(usuario.getCargo());
        usuarioTokenDto.setCelular(usuario.getCelular());

        return usuarioTokenDto;
    }
}
