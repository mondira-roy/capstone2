package com.company.captstone2.adminapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource ds;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder enc = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username,authority from authorities where username = ?")
                .passwordEncoder(enc);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                // all employees can have access to get routes
                .mvcMatchers(HttpMethod.GET,
                        "/customers/*","/customers",
                        "/levelup/*","/levelup",
                        "/invoices/*","/invoices",
                        "/inventory/*", "/inventory",
                        "/products/*", "/products"
                ).hasAuthority("ROLE_EMPLOYEE")
                // and update inventory
                .mvcMatchers(HttpMethod.PUT, "/inventory/*").hasAuthority("ROLE_EMPLOYEE")
                // all teamlead can have access to all update route (inventory is granted above)
                .mvcMatchers(HttpMethod.PUT,
                        "/customers/*",
                        "/levelup/*",
                        "/invoices/*",
                        "/products/*"
                ).hasAuthority("ROLE_TEAMLEAD")
                // and create Customers
                .mvcMatchers(HttpMethod.POST, "/customers").hasAuthority("ROLE_TEAMLEAD")
                // all manager can have access to all create route (customer is granted above)
                .mvcMatchers(HttpMethod.POST,
                        "/inventory",
                        "/levelup",
                        "/invoices",
                        "/products"
                ).hasAuthority("ROLE_MANAGER")
                // Delete requires admin level
                .mvcMatchers(HttpMethod.DELETE,
                        "/customers/*",
                        "/levelup/*",
                        "/invoices/*",
                        "/inventory/*",
                        "/products/*"
                        ).hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/invoices")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

}
