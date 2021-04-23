package com.ng0cth1nh.management.configuration;

import com.ng0cth1nh.management.security.CustomAccessDeniedHandler;
import com.ng0cth1nh.management.security.JwtRequestFilter;
import com.ng0cth1nh.management.security.RestAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public JwtRequestFilter jwtRequestFilter() throws Exception {
        JwtRequestFilter jwtRequestFilter = new JwtRequestFilter();
       jwtRequestFilter.setAuthenticationManager(authenticationManager());
        return jwtRequestFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("api/v1/**").permitAll()
//
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable();

        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
//
//        http = http.cors().and().csrf().disable();
//
//        http = http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and();
//
//        http = http.exceptionHandling().authenticationEntryPoint(restServicesEntryPoint()).and();
//
//
//        http.authorizeRequests()
//             .antMatchers("/api/v1/user/login**").permitAll()
//             .antMatchers(HttpMethod.GET,"/api/v1/users**").access("hasAnyAuthority('USER_READ')")
//             .anyRequest().authenticated();
//
//
//        http.addFilterBefore(jwtRequestFilter(),UsernamePasswordAuthenticationFilter.class);


//        http.csrf().ignoringAntMatchers("/api/v1/**");
//        http.authorizeRequests().antMatchers("/api/v1/user/login**").permitAll();
//
//        http.antMatcher("/api/v1/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/v1/**").access("hasRole('USER')")
//                .antMatchers(HttpMethod.POST, "/api/v1/**").access("hasRole('USER')")
//                .antMatchers(HttpMethod.DELETE, "/api/v1/**").access("hasRole('USER')")
//                .and()
//                .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

    }
}
