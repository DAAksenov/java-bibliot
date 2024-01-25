package ru.fa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.fa.auth.Role;
import ru.fa.auth.User;
import ru.fa.auth.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/signup", "/login").permitAll()
                .requestMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword(passwordEncoder.encode("admin"));
//        admin.setRole(Role.ADMIN);
//        manager.createUser(admin);
//        return manager;
//    }
}
