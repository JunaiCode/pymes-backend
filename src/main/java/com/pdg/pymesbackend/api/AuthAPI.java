package com.pdg.pymesbackend.api;

import com.pdg.pymesbackend.dto.LoginOutDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.LoginInDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthAPI {

    @PostMapping("/login")
    LoginOutDTO login(@RequestBody LoginInDTO loginInDTO);

    @PostMapping("/register")
    LoginOutDTO register(@RequestBody RegisterDTO registerDTO);
}
