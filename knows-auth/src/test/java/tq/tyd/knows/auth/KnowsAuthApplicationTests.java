package tq.tyd.knows.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import tq.tyd.knows.auth.service.UserDetailsServiceImpl;

import javax.annotation.Resource;

@SpringBootTest
class KnowsAuthApplicationTests {
    @Resource
    UserDetailsServiceImpl userDetailsService;
    @Test
    void contextLoads() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("st2");
        System.out.println(userDetails);
    }

}
