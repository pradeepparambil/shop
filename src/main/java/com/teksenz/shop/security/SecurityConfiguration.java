package com.teksenz.shop.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john").password("{noop}john123").roles("SALES")
                .and()
                .withUser("maria").password("{noop}maria123").roles("STORE_MANAGER")
                .and()
                .withUser("david").password("{noop}david123").roles("ADMIN");

    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails storeManager = User.withDefaultPasswordEncoder()
//                .username("maria")
//                .password("maria123")
//                .roles("STORE_MANAGER")
//                .build();
//
//        UserDetails sales = User.withDefaultPasswordEncoder()
//                .username("john")
//                .password("john123")
//                .roles("SALES")
//                .build();
//
//        return new InMemoryUserDetailsManager(storeManager,sales);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize->{
                    authorize
                            .antMatchers("/","/webjars/**","/login").permitAll()
                            .antMatchers(HttpMethod.GET,"/api/v1/products").permitAll()
                            .antMatchers(HttpMethod.GET,"/api/v1/products/").permitAll()
                            .antMatchers(HttpMethod.GET,"/api/v1/products/{id}").permitAll()
                            .antMatchers(HttpMethod.POST,"/api/v1/products").hasAnyRole("STORE_MANAGER","ADMIN")
                            .antMatchers(HttpMethod.POST,"/api/v1/products/").hasAnyRole("STORE_MANAGER","ADMIN")
                            .antMatchers(HttpMethod.PUT,"/api/v1/products/{id}").hasAnyRole("STORE_MANAGER","ADMIN")
                            .antMatchers(HttpMethod.DELETE,"/api/v1/products/{id}").hasAnyRole("STORE_MANAGER","ADMIN");


                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic().and().csrf().disable();

    }



}
