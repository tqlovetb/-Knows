package tq.tyd.knows.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tq.tyd.knows.portal.mapper.UserMapper;
import tq.tyd.knows.portal.model.Permission;
import tq.tyd.knows.portal.model.Role;
import tq.tyd.knows.portal.model.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userMapper.findUserByUsername(username);
        if(user == null){
            return null;
        }
        List<Permission> permissions = userMapper.findUserPermissionById(user.getId());
        String[] auth = new String[permissions.size()];
        int i = 0;
        for(Permission p : permissions){
            auth[i++] = p.getName();
        }
        List<Role> roles = userMapper.findUserRoleById(user.getId());
        auth = Arrays.copyOf(auth, auth.length+roles.size());
        for(Role role:roles){
            auth[i]=role.getName();
            i++;
        }

        UserDetails details = org.springframework.security.core
                .userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(auth)
                .accountLocked(user.getLocked()==1)
                .disabled(user.getEnabled()==0)
                .build();
        return details;
    }
}
