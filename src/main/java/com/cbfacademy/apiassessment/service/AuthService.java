package com.cbfacademy.apiassessment.service;

import com.cbfacademy.apiassessment.dto.LoginDTO;

public interface AuthService {
   String login(LoginDTO loginDTO);
}
