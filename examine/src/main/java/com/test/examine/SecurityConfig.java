package com.test.examine;

import com.test.examine.service.common.impl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailService userDetailService;

    @Autowired
    public SecurityConfig(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("p1","p2","p3")
                .antMatchers("/health/**").hasAuthority("p4")
                .antMatchers("/authority").hasAuthority("p1")
                .antMatchers("/authority/**").hasAuthority("p1")
                .antMatchers("/healthAnalyse").hasAnyAuthority("p1","p2","p3")
                .antMatchers("/healthAnalyse/**").hasAnyAuthority("p1","p2","p3")
                .antMatchers("/teacher").hasAuthority("p3")
                .antMatchers("/labManage").hasAuthority("p1")
                .antMatchers("/index").authenticated()
                .antMatchers("/tmp").authenticated()
                .antMatchers("/history").hasAuthority("p4")
                .anyRequest().permitAll()
                .and()
                .logout()
                .and()
                .formLogin()
                .loginPage("/login-view")
                .loginProcessingUrl("/login")
                .successForwardUrl("/tmp")
                .failureForwardUrl("/loginFailure")
                .and().rememberMe().rememberMeCookieName("cookie").tokenValiditySeconds(60 * 60);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
