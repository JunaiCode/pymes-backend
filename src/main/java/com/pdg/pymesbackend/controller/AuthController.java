package com.pdg.pymesbackend.controller;

import com.pdg.pymesbackend.api.AuthAPI;
import com.pdg.pymesbackend.dto.LoginOutDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.LoginInDTO;
import com.pdg.pymesbackend.service.modules.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class AuthController implements AuthAPI {

    private final AuthService authService;


    @Override
    public LoginOutDTO login(LoginInDTO loginInDTO) {

        return authService.login(loginInDTO);
    }

    @Override
    public LoginOutDTO register(RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }
}
