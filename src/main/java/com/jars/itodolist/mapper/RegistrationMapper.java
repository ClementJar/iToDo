package com.jars.itodolist.mapper;

import com.example.model.RegisterUserRequest;
import com.example.model.SecUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    SecUser toUser(RegisterUserRequest registerUserRequest);
}
