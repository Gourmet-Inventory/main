package project.gourmetinventoryproject.api.configuration.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

//"AuthenticationEntryPoint" é uma interface do Spring Security que define como a aplicação
//deve lidar com as requisições não autenticadas. Quando o usuário tenta acessar um endpoint
//protegido sem estar autenticado, o Spring Security redireciona o usuário para um ponto de
//entrada de autenticação.

@Component
public class AutenticacaoEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        if (authException.getClass().equals(BadCredentialsException.class) || authException.getClass().equals(InsufficientAuthenticationException.class)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
