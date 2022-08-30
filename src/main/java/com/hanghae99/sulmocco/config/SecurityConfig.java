package com.hanghae99.sulmocco.config;

import com.hanghae99.sulmocco.repository.RefreshTokenRepository;
import com.hanghae99.sulmocco.security.FilterSkipMatcher;
import com.hanghae99.sulmocco.security.FormLoginSuccessHandler;
import com.hanghae99.sulmocco.security.filter.FormLoginFilter;
import com.hanghae99.sulmocco.security.filter.JwtAuthFilter;
import com.hanghae99.sulmocco.security.jwt.HeaderTokenExtractor;
import com.hanghae99.sulmocco.security.jwt.JwtDecoder;
import com.hanghae99.sulmocco.security.provider.FormLoginAuthProvider;
import com.hanghae99.sulmocco.security.provider.JWTAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtDecoder jwtDecoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(passwordEncoder());
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler(refreshTokenRepository);
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/api/login");     //앞단의 요청으로 /api 추가.
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**",
                        "ec2-13-209-8-162.ap-northeast-2.compute.amazonaws.com",
//                        "https://scw5dza5e4.execute-api.ap-northeast-2.amazonaws.com",
                        // swagger 관련 리소스 시큐리티 필터 제거
                        "/api/v1/auth/**",
                        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html",
                        "/swagger-ui.html", "/webjars/**", "/swagger/**", "/favicon.ico");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(formLoginAuthProvider())
                .authenticationProvider(jwtAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin(); // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.

        http.cors();
        http

                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement()

                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .authorizeRequests()
//                .antMatchers("/chat/**").permitAll()
                .anyRequest().permitAll();

    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();
//        skipPathList.add("POST,/**");
//        skipPathList.add("GET,/**");
        skipPathList.add("POST,/api/login");
        skipPathList.add("PUT,/api/refreshToken");
        skipPathList.add("POST,/api/signup");
        skipPathList.add("GET,/api/checkUser/{username}");
        skipPathList.add("GET,/oauth2/redirect");
        skipPathList.add("GET,/oauth2/redirect_pw");
        skipPathList.add("PUT,/api/resetPw");
        skipPathList.add("GET,/oauth2/**");
        skipPathList.add("GET,/password/**");
//        skipPathList.add("GET,/chat**");
//        skipPathList.add("GET,/room**");
//        skipPathList.add("POST,/chat**");
//        skipPathList.add("POST,/room**");
        // 채팅
        skipPathList.add("GET,/webjars/**");
        skipPathList.add("GET,/ws-stomp/**");
        skipPathList.add("GET,/ws-alarm/**");
        skipPathList.add("GET,/chat/user");


        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(matcher, headerTokenExtractor, jwtDecoder);
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
