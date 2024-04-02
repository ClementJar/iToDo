package com.jars.itodolist.configuration;

import com.jars.itodolist.interfaces.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final IUserService iUserService;

    public MyUserDetailsService(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserService.loadUserByUsername(username);
    }
}
