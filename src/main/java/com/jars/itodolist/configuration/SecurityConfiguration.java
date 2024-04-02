package com.jars.itodolist.configuration;

import com.jars.itodolist.interfaces.IUserService;
import com.jars.itodolist.model.SecUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {
    private final MyUserDetailsService userService;

    public SecurityConfiguration(MyUserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if needed
                // Configure login page and authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/assets/**", "/layout/**", "/icons/**", "/", "/login", "/home/register", "/home/login").permitAll()
                        .anyRequest().authenticated()// Permit access to "/" and "/login"
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Specify your custom login page
                        .loginProcessingUrl("/home/login")  // URL for processing login form
                        .defaultSuccessUrl("/toDoList")
                        .permitAll()
                )
                .logout(log -> log
                        .logoutUrl("logOut")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logOut", "GET"))
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")// Specify the logout URL
                        .logoutSuccessUrl("/login"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());

        return provider;
    }
}
