package project.gourmetinventoryproject.api.configuration.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import project.gourmetinventoryproject.api.configuration.security.jwt.GerenciadorTokenJwt;
import project.gourmetinventoryproject.dto.usuario.autenticacao.AutenticacaoService;

import java.io.IOException;
import java.util.Objects;

/*
"AutenticacaoFilter" é uma classe do Spring Security responsável por processar as solicitações
de autenticação do usuário e realizar a validação das credenciais fornecidas pelo usuário.

O "AutenticacaoFilter" é uma implementação do "AbstractAuthenticationProcessingFilter",
que é a classe base fornecida pelo Spring Security para processar as solicitações de
autenticação. O "AutenticacaoFilter" é responsável por extrair as informações de autenticação
da solicitação HTTP recebida, criar uma instância de "Authentication" e enviar essa instância
para o "AuthenticationManager" realizar a autenticação.

Após a autenticação bem-sucedida, o "AutenticacaoFilter" é responsável por gerar o token de
acesso e retorná-lo ao cliente. O token é geralmente armazenado pelo cliente e enviado em
cada solicitação subsequente para validar a autenticação do usuário.
*/

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);
    private final AutenticacaoService autenticacaoService;
    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwtToken = null;

        String requestTokenHeader = request.getHeader("Authorization");

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException exception){
                LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}", exception.getClaims().getSubject(), exception.getMessage());
                LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            addUsernameInContext(request, username, jwtToken);
        }
        filterChain.doFilter(request, response);
    }

    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
