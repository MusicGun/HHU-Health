package com.test.examine.service.common.impl;


import com.test.examine.entity.User;
import com.test.examine.service.common.UserService;
import com.test.examine.service.common.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private UserService userService;

    @Autowired
    public UserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserById(username);
        if (user == null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername("tourist")
                    .password("123")
                    .authorities("p0")
                    .roles("tourist")
                    .disabled(true)
                    .build();
        }
        boolean enable = user.getEnable() == 0;
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole())
                .authorities(user.getAuthorities())
                .disabled(enable).build();
    }
}
