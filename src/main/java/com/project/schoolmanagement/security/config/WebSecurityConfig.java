package com.project.schoolmanagement.security.config;

import com.project.schoolmanagement.security.jwt.AuthEntryPointJwt;
import com.project.schoolmanagement.security.jwt.AuthTokenFilter;
import com.project.schoolmanagement.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.security.PermitAll;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
/**
 * this class is the main configuration class of security
 * all implementation will be injected in this class and be used as configuration
 */
public class WebSecurityConfig
{
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                //configured unauthorized exception handler
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                //configured session management to stateless
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //added white list
                .authorizeRequests().antMatchers(AUTH_WHITE_LIST).permitAll()
                //except whitelist authenticate all request
                .anyRequest().authenticated();

        http.headers().frameOptions().sameOrigin();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     *
     * @return auth token filter implemented in jwt package
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter()
    {
        return new AuthTokenFilter();
    }


    /**
     *this will be authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


    /**
     * this will be password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     *this will be cors configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // allow all URL.s
                registry.addMapping("/**")
                        // allow all origins
                        .allowedOrigins("*")
                        // allow all headers
                        .allowedHeaders("*")
                        // allow all HTTP methosa
                        .allowedMethods("*");
            }
        };
    }

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "swagger-ui.html",
            "/swagger-ui/**",
            "/",
            "index.html",
            "/images/**",
            "/css/**",
            "/js/**",
            "/contactMessages/save",
            "/auth/login"
    };
}
