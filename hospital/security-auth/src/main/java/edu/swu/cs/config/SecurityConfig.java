package edu.swu.cs.config;

import edu.swu.cs.SecurityHandler.AccessDeniedHandlerImpl;
import edu.swu.cs.SecurityHandler.AuthenticationEntryPointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/logout").authenticated()
                .antMatchers("/login").anonymous();
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/testlogin")
//                .successHandler((req, resp, authentication) -> {
//                    Object principal = authentication.getPrincipal();
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write(new ObjectMapper().writeValueAsString(principal));
//                    out.flush();
//                    out.close();
//                })
//                .failureHandler((req, resp, e) -> {
//                    resp.setContentType("application/json;charset=utf-8");
//                    PrintWriter out = resp.getWriter();
//                    out.write(e.getMessage());
//                    out.flush();
//                    out.close();
//                })
        //  除上面外的所有请求全部不需要认证即可访问
//                .permitAll();

        //关闭默认的注销接口
        http.logout().disable();
        //添加自定义认证失败异常处理
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        //添加jwt认证过滤器

        //http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
