package project.gourmetinventoryproject.api.configuration.security;

import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.gourmetinventoryproject.api.configuration.security.jwt.GerenciadorTokenJwt;
import project.gourmetinventoryproject.dto.usuario.autenticacao.AutenticacaoService;

import java.util.Arrays;
import java.util.List;


/*
A classe "SecurityConfiguracao" é responsável por configurar e filtrar os endpoints para
permitir apenas o acesso autenticado. O método "public SecurityFilterChain
filterChain(HttpSecurity http)" é responsável por configurar o CORS e os endpoints que não
exigem autenticação, incluindo o endpoint de login.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguracao {
    private static final String ORIGENS_PERMITIDAS = "*";

    @Autowired
    private AutenticacaoService autenticacaoService;
    @Autowired
    private AutenticacaoEntryPoint autenticacaoEntryPoint;

    /*
    O array URLS_PERMITIDAS provavelmente armazena vários objetos AntPathRequestMatcher, cada um representando um padrão de URL que deve permitir acesso sem autenticação do Spring Security.
    Quando o Spring Security processa uma requisição, ele itera pelo array URLS_PERMITIDAS.
    */
    private static final AntPathRequestMatcher[] URLS_PERMITIDAS = {
        new AntPathRequestMatcher("/api/swagger-ui/**"),
        new AntPathRequestMatcher("/api/swagger-ui.html"),
        new AntPathRequestMatcher("/api/swagger-resources"),
        new AntPathRequestMatcher("/api/swagger-resources/**"),
        new AntPathRequestMatcher("/api/configuration/ui"),
        new AntPathRequestMatcher("/api/configuration/security"),
        new AntPathRequestMatcher("/api/public/**"),
        new AntPathRequestMatcher("/api/public/authenticate"),
        new AntPathRequestMatcher("/api/webjars/**"),
        new AntPathRequestMatcher("/api/v3/api-docs/**"),
        new AntPathRequestMatcher("/api/actuator/*"),
        new AntPathRequestMatcher("/api/usuarios/**"),
        new AntPathRequestMatcher("/api/usuarios/login/**"),
        new AntPathRequestMatcher("/api/usuarios/logout/**"),
        new AntPathRequestMatcher("/h2-console/**"),
        //new AntPathRequestMatcher("http://localhost:3306/workbench/**"), // Adicione a URL do MySQL Workbench
        //new AntPathRequestMatcher("jdbc:mysql://localhost:3306/**"), //??
        new AntPathRequestMatcher("/error/**"),
        //new AntPathRequestMatcher("http://localhost:8080/**"),
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer<HttpSecurity>::disable)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(URLS_PERMITIDAS)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(autenticacaoEntryPoint))
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(new AutenticacaoProvider(autenticacaoService, passwordEncoder()));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public AutenticacaoEntryPoint jwtAuthenticationEntryPointBean() {
        return new AutenticacaoEntryPoint();
    }

    @Bean
    public AutenticacaoFilter jwtAuthenticationFilterBean() {
        return new AutenticacaoFilter(autenticacaoService, jwtAuthenticationUtilBean());
    }

    @Bean
    public GerenciadorTokenJwt jwtAuthenticationUtilBean() {
        return new GerenciadorTokenJwt();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuracao = new CorsConfiguration();
        configuracao.applyPermitDefaultValues();
        configuracao.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name()));

        configuracao.setExposedHeaders(List.of(HttpHeaders.CONTENT_DISPOSITION));

        UrlBasedCorsConfigurationSource origem = new UrlBasedCorsConfigurationSource();
        origem.registerCorsConfiguration("/**", configuracao);
        return origem;
    }
}
