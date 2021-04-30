package com.board.api.config;

import com.board.api.service.UserService;
import com.board.common.type.UserType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public WebSecurityConfiguration(UserService userService) {

        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login", "/signUp", "/user").permitAll()
                .antMatchers("/index").hasAnyAuthority(UserType.GENERAL.getAuthority(), UserType.ADMIN.getAuthority())
                .antMatchers("/api/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/login/result", Boolean.TRUE)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(Boolean.TRUE)
                .and()
                .exceptionHandling().accessDeniedPage("/user/denied");
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
