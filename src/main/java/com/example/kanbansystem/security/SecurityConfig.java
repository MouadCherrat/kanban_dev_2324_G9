package com.example.kanbansystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(Arrays.asList("*"));
            corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
            corsConfig.setAllowedHeaders(Arrays.asList("*"));
            return corsConfig;
        });

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/user").permitAll()
                //hasRole("ADMIN")


                .antMatchers(HttpMethod.GET,"/api/boards/**").permitAll()
                //hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/boardsId/**").permitAll()
              //  hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/api/boards").permitAll()
                //hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/boardsDeleted/**").permitAll()
                //hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"/api/tasks/**").permitAll()
               // hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/taskId/**").permitAll()
                //hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/api/tasks").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/Tasks/**").permitAll()
                //hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/tasksDeleted/**").permitAll()
                //hasRole("ADMIN")

                .antMatchers(HttpMethod.GET,"/api/sprints/**").permitAll()
                //hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.GET,"/api/sprintId/**").permitAll()
                //hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.POST,"/api/sprints").permitAll()
                //hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/sprintDeleted/**").permitAll()
                //hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .formLogin()
               // .failureHandler(authenticationFailureHandler())// Set custom authentication failure handler
                .and()
                .httpBasic();
    }
    @Bean
    public UserDetailsService myUserDetailsService() {
        return new MyuserDetailsService();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
