package org.example.config;

import lombok.extern.log4j.Log4j2;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO Заменить WebSecurityConfigurerAdapter на новый
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/auth/process_login").permitAll()
                .antMatchers("/admin/wewrr").hasAnyRole("ADMIN").anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/process_login")
                .defaultSuccessUrl("/admin", true)
                .failureUrl("/auth/login?error");
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorize ->
//                        authorize
//                                .anyRequest()
//                                .authenticated())
//                .csrf().disable()
//                .formLogin(form -> form
//                        .loginPage("/auth/login")
//                        .loginProcessingUrl("/auth/process_login")
//                        .failureUrl("/auth/login?error").permitAll())
//                .httpBasic();
//        return http.build();
//    }


//    @Bean
//    SecurityFilterChain configureSecurity(HttpSecurity http)
//            throws Exception {
//        http.authorizeHttpRequests() //
//                .requestMatchers("/static/**", "/photos/**", "/auth/**")
//                .permitAll() //
//                .requestMatchers(HttpMethod.GET, "/admin/**")
//                .hasRole("ADMIN") //
//                .requestMatchers("/db/**").access((authentication,
//                                                   object) -> {
//                    boolean anyMissing = Stream.of("ADMIN",
//                                    "DBA")//
//                            .map(role -> hasRole(role)
//                                    .check(authentication, object).isGranted()) //
//                            .filter(granted -> !granted) //
//                            .findAny() //
//                            .orElse(false); //
//                    return new AuthorizationDecision(!anyMissing);
//                }) //
//                .anyRequest().denyAll() //
//                .and() //
//                .formLogin() //
//                .and() //
//                .httpBasic();
//        return http.build();
//    }
}
