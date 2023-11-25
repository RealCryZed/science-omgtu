package ru.omgtu.scienceomgtu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/publications/page/**").access("hasAuthority('ADMIN')")
                .antMatchers("/publications/page/1").hasRole("ADMIN")
                .antMatchers("/", "/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                    .loginPage("/login")
//                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/publications/page/1", true)
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/publication/777")
                    .permitAll();
//                    .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//                .and()
//                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(60);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}