package com.jars.itodolist.service;

import com.example.model.SecUser;
import com.jars.itodolist.interfaces.IUserService;
import com.jars.itodolist.mapper.SecUserEntityMapper;
import com.jars.itodolist.model.SecUserEntity;
import com.jars.itodolist.repository.UserEntityRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final UserEntityRepository userEntityRepository;
    private final SecUserEntityMapper userMapper;

    public UserService(UserEntityRepository userEntityRepository, SecUserEntityMapper userMapper) {
        this.userEntityRepository = userEntityRepository;
        this.userMapper = userMapper;
    }

    public SecUserEntity addUser(SecUser user) {
        if (!userEntityRepository.existsByEmail(user.getEmail())){
            user.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userEntityRepository.save(userMapper.toEntity(user));
        }
    throw new EntityExistsException();

    }
    public SecUserEntity getUserById(int id) {
        return userEntityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public SecUserEntity getUserByUserNameOrEmail(String userNameOrEmail) {
        SecUserEntity user = userEntityRepository.findByUsernameIgnoreCase(userNameOrEmail);
        if (user == null){
            user = userEntityRepository.findByEmailIgnoreCase(userNameOrEmail);
        }
        if (user == null){
            throw new EntityNotFoundException();
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecUserEntity user = getUserByUserNameOrEmail(username);
        if (user != null){
            return User.builder().username(user.getUsername()).password(user.getPassword()).roles("USER","ADMIN").build();

        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
