package com.jars.itodolist.interfaces;
import com.example.model.SecUser;
import com.jars.itodolist.model.SecUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    public SecUserEntity addUser(SecUser user);
    public SecUserEntity getUserById(int id);
    public SecUserEntity getUserByUserNameOrEmail(String userNameOrEmail);
}
