package com.jars.itodolist.controller;

import com.example.api.HomeApi;
import com.example.model.Login200Response;
import com.example.model.LoginRequest;
import com.example.model.RegisterUser201Response;
import com.example.model.RegisterUserRequest;
import com.jars.itodolist.interfaces.IUserService;
import com.jars.itodolist.mapper.RegistrationMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Map;

@RestController
@Slf4j
public class HomeController implements HomeApi {
    private final IUserService iUserService;
    private final RegistrationMapper registrationMapper;

    public HomeController(IUserService iUserService,
                          RegistrationMapper registrationMapper) {
        this.iUserService = iUserService;
        this.registrationMapper = registrationMapper;
    }

    @GetMapping({"/login","/"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }
    @GetMapping({"/logOut"})
    public void logOut() {
    }

 @Override
    public ResponseEntity<Login200Response> login(LoginRequest loginRequest) {
        Login200Response login200Response = new Login200Response();
        try {
            login200Response.message("success");
            return ResponseEntity.ok(login200Response);
        } catch (Exception e) {

            //log.error(e.getMessage());
            login200Response.message("Failed");
            return ResponseEntity.ok(login200Response);
        }

    }
@Override
    public ResponseEntity<RegisterUser201Response> registerUser(RegisterUserRequest registerUserRequest) {
        RegisterUser201Response registerUser201Response = new RegisterUser201Response();
        try {
            iUserService.addUser(registrationMapper.toUser(registerUserRequest));
            registerUser201Response.message("success");
            return ResponseEntity.ok(registerUser201Response);
        } catch (EntityExistsException e){
            registerUser201Response.message("EmailExists");
            return ResponseEntity.ok(registerUser201Response);
        }catch (Exception e) {
            //log.error(e.getMessage());
            registerUser201Response.message("Failed");
            return ResponseEntity.ok(registerUser201Response);
        }
    }
}
