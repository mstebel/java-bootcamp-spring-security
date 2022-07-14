package pl.ms.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests().antMatchers("/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**");
        http.authorizeHttpRequests(requests -> requests
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/register").permitAll()
                .mvcMatchers("/login/**").permitAll()
                .mvcMatchers("/userPanel/**").hasAnyRole("USER", "ADMIN")
                .mvcMatchers("/adminPanel/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        http.formLogin(login -> login.loginPage("/login").permitAll());
        http.logout(logout -> logout.logoutRequestMatcher
                        (new AntPathRequestMatcher("/logout", HttpMethod.GET.name()))
                .logoutSuccessUrl("/logoutSuccess").permitAll());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
