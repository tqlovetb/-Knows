package tq.tyd.knows.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import tq.tyd.knows.portal.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    //如果没有下面的话，就是用admin,123456登录，配合@AuthenticationPrincipal UserDetails user（控制类中的）一起使用，可以快速获取当前登录用户的信息
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        //"/index_student.html",
                        "/css/*",
                        "js/*",
                        "/img/**",
                        "/bower_components/**",
                        "/login.html",
                        "/register.html",
                        "/register"
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error")
                .defaultSuccessUrl("/index.html")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout");
    }
}
