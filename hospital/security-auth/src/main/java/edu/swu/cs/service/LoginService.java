package edu.swu.cs.service;

import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.LoginUserDTO;

public interface LoginService {
    ResponseResult login(LoginUserDTO user);

   // ResponseResult logout();
}