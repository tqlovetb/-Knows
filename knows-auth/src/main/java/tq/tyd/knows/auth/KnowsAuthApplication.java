package tq.tyd.knows.auth;

import tq.tyd.knows.auth.filter.CorsFilter;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class KnowsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsAuthApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    // 将一个注册过滤器的类保存在Spring容器中
    // Spring启动时会启用这里面注册的过滤器
    @Bean
    public FilterRegistrationBean registrationBean(){
        // 实例化注册过滤器的对象
        FilterRegistrationBean<CorsFilter> bean=
                new FilterRegistrationBean<>();
        // 设置过滤器生效路径(全部生效即可)
        bean.addUrlPatterns("/*");
        // 设置过滤器的优先级(多个过滤器的运行顺序,优先级高先运行)
        // 这里设置它的优先级为最高
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        // 设置过滤器对象
        bean.setFilter(new CorsFilter());
        return  bean;
    }
}
