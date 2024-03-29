package capstone.fotoHub.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityChain {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll());
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());
////        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/utenti/**").hasAnyAuthority(Tipologia.ADMIN.name()));
////        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/comuni/**").hasAnyAuthority(Tipologia.ADMIN.name()));
////        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/provincie/**").hasAnyAuthority(Tipologia.ADMIN.name()));
////        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/clienti/**").hasAnyAuthority(Tipologia.ADMIN.name()));
////        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/fatture/**").hasAnyAuthority(Tipologia.ADMIN.name()));
////        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/indirizzi/**").hasAnyAuthority(Tipologia.ADMIN.name()));
//        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").denyAll());


        return httpSecurity.build();
    }




}