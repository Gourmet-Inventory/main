package project.gourmetinventoryproject.dto.usuario;

import project.gourmetinventoryproject.domain.Usuario;

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
}
