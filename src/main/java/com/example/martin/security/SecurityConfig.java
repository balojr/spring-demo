package com.example.martin.security;

import com.example.martin.security.JwtAuthenticationEntryPoint;
import com.example.martin.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfig {
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  public SecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public static PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception{
    http.cors().and().csrf().disable()
      .authorizeRequests()
      .antMatchers("/api/login","/api/registration/confirm", "/api/user" ).permitAll()
      .anyRequest().authenticated().and().exceptionHandling()
      .authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(authenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

    log.info("Invoked");
    return http.build();
  }
  @Bean
  public JwtAuthenticationFilter authenticationFilterBean(){
    return new JwtAuthenticationFilter();
  }


}
