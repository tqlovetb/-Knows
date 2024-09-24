package tq.tyd.knows.sys.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tq.tyd.knows.sys.interceptor.AuthInterceptor;
import tq.tyd.knows.sys.interceptor.DemoInterceptor;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
    @Resource
    private DemoInterceptor demoInterceptor;

    @Resource
    private AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor)
                .addPathPatterns("/v1/auth/demo");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        "/v1/home",
                        "/v1/users/me"
                );
    }
}
