package tq.tyd.knows.auth.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;
import tq.tyd.knows.commons.model.Permission;
import tq.tyd.knows.commons.model.Role;
import tq.tyd.knows.commons.model.User;

import javax.annotation.Resource;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    RestTemplate restTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url="http://sys-service/v1/auth/user?username={1}";
        User user = restTemplate.getForObject(url, User.class,username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }

        url="http://sys-service/v1/auth/permissions?id={1}";
        Permission[] permissions = restTemplate.getForObject(url, Permission[].class,user.getId());
        url = "http://sys-service/v1/auth/roles?id={1}";
        Role[] roles = restTemplate.getForObject(url, Role[].class,user.getId());
        String[] auth = new String[permissions.length+roles.length];
        int i = 0;
        for(Permission p : permissions){
            auth[i++] = p.getName();
        }
        for(Role r : roles){
            auth[i++] = r.getName();
        }
        UserDetails details = org.springframework.security.core
                .userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(auth)
                .accountLocked(user.getLocked() == 1)
                .disabled(user.getEnabled() == 0)
                .build();
        return details;
    }
}
