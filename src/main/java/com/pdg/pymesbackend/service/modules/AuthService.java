package com.pdg.pymesbackend.service.modules;

import com.pdg.pymesbackend.dto.LoginOutDTO;
import com.pdg.pymesbackend.dto.RegisterDTO;
import com.pdg.pymesbackend.dto.out.LoginInDTO;

public interface AuthService {
    public LoginOutDTO login(LoginInDTO loginInDTO);
    public LoginOutDTO register(RegisterDTO registerDTO);
}
