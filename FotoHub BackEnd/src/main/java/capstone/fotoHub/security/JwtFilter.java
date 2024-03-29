package capstone.fotoHub.security;

import capstone.fotoHub.exception.UnAuthorizedException;
import capstone.fotoHub.model.Persona;
import capstone.fotoHub.service.PersonaService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PersonaService personaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization==null||!authorization.startsWith("Bearer ")){
            System.out.println(request.getMethod());
            throw new UnAuthorizedException("Token non presente");
        }

        String token = authorization.substring(7);

        jwtTools.validateToken(token);

        String email = jwtTools.extractEmailFromToken(token);

        System.out.println(email);

        Persona persona = personaService.getPersonaByMail(email);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(persona, null, persona.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath())
                || new AntPathMatcher().match("/logged-in-user", request.getServletPath())
                || new AntPathMatcher().match("/foto", request.getServletPath())
                || new AntPathMatcher().match("/foto/user/*", request.getServletPath());
    }

    private void checkPathVariable(HttpServletRequest request, Persona persona) {
        String[] parts = request.getServletPath().split("/");
        System.out.println(parts.length);
        Arrays.stream(parts).forEach(System.out::println);
        if (parts.length == 3) {
            if (parts[1].equals("persone")) {
                int id = Integer.parseInt(parts[2]);

                if (persona.getId() != id) {
                    throw new UnAuthorizedException("Non sei abilitato ad utilizzare il servizio per id differenti dal tuo");
                }

            }
        }
    }
}