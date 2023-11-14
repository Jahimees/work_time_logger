package com.example.worktime.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * Конфигурация модуля Spring Security. Отвечает за настройку авторизации
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers("/**").permitAll()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") //Путь по которому происходит авторизация
                        .defaultSuccessUrl("/wlogger") //путь переадресации после успешной авторизации
                        .usernameParameter("username") //Параметр "логин"
                        .passwordParameter("password") //Параметр "пароль"
                        .permitAll()
                )
                .logout(logout -> {
                    logout.permitAll();
                    logout.logoutSuccessUrl("/login"); //Путь по которому приложение перенаправляет при выходе из аккаунта
                })
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    /**
     * Настройка объекта, отвечающего за хэширование паролей
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
