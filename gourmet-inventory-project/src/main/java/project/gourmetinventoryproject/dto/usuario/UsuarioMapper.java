package project.gourmetinventoryproject.dto.usuario;

import project.gourmetinventoryproject.domain.Usuario;
import project.gourmetinventoryproject.dto.usuario.autenticacao.dto.UsuarioTokenDto;

public class UsuarioMapper {

    public static Usuario of(UsuarioCriacaoDto usuarioCriacaoDto) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setCargo(usuarioCriacaoDto.getCargo());
        usuario.setCpf(usuarioCriacaoDto.getCpf());
        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setCelular(usuarioCriacaoDto.getCelular());
        usuario.setSenha(usuarioCriacaoDto.getSenha());
        return usuario;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto() ;

        usuarioTokenDto.setUserId( usuario.getIdUsuario());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setSenha(usuario.getSenha());
        usuarioTokenDto. setToken(token);
        return usuarioTokenDto;
    }
}
