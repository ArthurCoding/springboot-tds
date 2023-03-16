package edu.spring.dogs.config

import edu.spring.dogs.services.DbUserService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable().authorizeHttpRequests()
            .requestMatchers("/master/**").hasRole("manager")
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login")
            .and()
            .authorizeHttpRequests()
            .requestMatchers(PathRequest.toH2Console()).permitAll() // (1)
            .and()
            .headers().frameOptions().sameOrigin() // (2)
            .and()
            .csrf().disable() // (3)
        return http.build()
    }
    @Bean
    fun userDetailsService(): UserDetailsService? {
        return DbUserService()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

}