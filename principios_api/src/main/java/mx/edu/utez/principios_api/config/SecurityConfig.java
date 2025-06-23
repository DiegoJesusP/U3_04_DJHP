package mx.edu.utez.principios_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(authz -> authz
                // Permitir acceso público a recursos estáticos
                .requestMatchers("/", "/index.html", "/css/**", "/js/**", "/images/**").permitAll()
                // Permitir lectura de datos básicos sin autenticación
                .requestMatchers(HttpMethod.GET, "/api/almacenes/disponibles").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/cedes").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/clientes").permitAll()
                // Permitir registro de clientes
                .requestMatchers(HttpMethod.POST, "/api/clientes").permitAll()
                // Permitir operaciones de compra/renta
                .requestMatchers(HttpMethod.POST, "/api/almacenes/operacion").permitAll()
                // Requerir autenticación para operaciones administrativas
                .requestMatchers(HttpMethod.POST, "/api/cedes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/almacenes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                // Permitir el resto con autenticación básica
                .anyRequest().authenticated()
            )
            .httpBasic(httpBasic -> {});
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();
        
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();
        
        return new InMemoryUserDetailsManager(admin, user);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
