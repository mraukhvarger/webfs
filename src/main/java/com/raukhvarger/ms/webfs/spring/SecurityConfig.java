package com.raukhvarger.ms.webfs.spring;

import com.raukhvarger.ms.webfs.front.spring.CustomRequestCache;
import com.raukhvarger.ms.webfs.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGOUT_SUCCESS_URL = "/";

    private final AppConfig appConfig;

    public SecurityConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomRequestCache requestCache() {
        return new CustomRequestCache();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (appConfig.getDisableAuthorization())
            http.csrf().disable().authorizeRequests().antMatchers("/", "/**").permitAll();
        else
            http.csrf().disable()
                    .requestCache().requestCache(requestCache())
                    .and().authorizeRequests()
                    .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/" + LoginView.ROUTE).permitAll()
                    .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",
                "/icons/**",
                "/images/**",
                "/frontend/**",
                "/static/frontend/**",
                "/webjars/**",
                "/h2-console/**",
                "/frontend-es5/**", "/frontend-es6/**");
    }
}