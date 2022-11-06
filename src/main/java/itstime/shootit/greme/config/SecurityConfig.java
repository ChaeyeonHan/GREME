package itstime.shootit.greme.config;

import itstime.shootit.greme.oauth.handler.OAuth2SuccessHandler;
import itstime.shootit.greme.oauth.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOauth2UserService customOauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .cors()
                .and()
                    .csrf().disable()
                .authorizeHttpRequests()
                    .antMatchers("/oauth2/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().disable()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOauth2UserService)
                .and()
                    //.defaultSuccessUrl("/oauth2-authroization-kakao") //로그인 실패 시
                    .successHandler(oAuth2SuccessHandler); //로그인 성공 시

        return http.build();
    }
}
