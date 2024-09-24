package tq.tyd.knows.faq.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tq.tyd.knows.faq.interceptor.AuthInterceptor;

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
    private AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        "/v2/questions",
                        "/v2/questions/my",
                        "/v2/questions/teacher",
                        "/v2/answers",             // 讲师回复
                        "/v2/comments",            // 添加评论
                        "/v2/comments/*/delete",   // 删除评论
                        "/v2/comments/*/update",   // 修改评论
                        "/v2/answers/*/solved"     // 采纳回答
                );
    }
}
