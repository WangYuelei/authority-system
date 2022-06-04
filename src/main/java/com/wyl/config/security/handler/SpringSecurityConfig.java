package com.wyl.config.security.handler;

import com.wyl.config.security.service.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomerUserDetailsService customerUserDetailsService;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    /**
     * 注入加密处理类
     *
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录前进行过滤
        http.formLogin().loginProcessingUrl("/api/user/login")
                //设置登录验证成功或失败后的跳转地址
                //登录成功处理器
                .successHandler(loginSuccessHandler)
                //登录失败处理器
                .failureHandler(loginFailureHandler)
                //禁用csrf防御机制
                .and()
                .csrf().disable()
                //不创建session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //设置需要拦截的请求
                .authorizeRequests()
                //登录请求放行
                .antMatchers("/api/user/login")
                .permitAll()
                //其他所有请求都需要身份认证
                .anyRequest()
                .authenticated()
                .and().exceptionHandling()
                //匿名无权限访问
                .authenticationEntryPoint(anonymousAuthenticationHandler)
                //用户无权限访问
                .accessDeniedHandler(customerAccessDeniedHandler)
                .and()
                //支持跨域请求
                .cors();
    }

    /**
     * 配置认证处理器
     * @param auth the {@link AuthenticationManagerBuilder} to use
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
