package com.epam.addressbook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${https.disabled:true}")
    private boolean disableHttps;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!disableHttps) {
            http.requiresChannel().anyRequest().requiresInsecure();
        }

        http.authorizeRequests().antMatchers("/**").hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER");
    }
}
