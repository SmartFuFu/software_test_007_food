package org.tju.food_007.controller.pub.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.dto.pub.login.UserQuitRequestDTO;
import org.tju.food_007.dto.pub.login.UserQuitResponseDTO;
import org.tju.food_007.service.pub.login.UserLoginService;

/**
 * @author WGY
 * @create 2024-03-07-16:01
 */
@RestController
@RequestMapping("/api/pub/login")
public class UserLoginController {
    @Autowired
    UserLoginService userLoginService;
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDTO> UserLogin(@RequestBody UserLoginRequestDTO requestDTO){
        UserLoginResponseDTO responseDTO=userLoginService.UserLogin(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/drive",method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponseDTO> UserLoginDrive(@RequestBody UserLoginRequestDTO requestDTO){
        UserLoginResponseDTO responseDTO=userLoginService.UserLogin(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/quit",method = RequestMethod.POST)
    public ResponseEntity<UserQuitResponseDTO> UserQuit(@RequestBody UserQuitRequestDTO requestDTO){
        UserQuitResponseDTO responseDTO=new UserQuitResponseDTO();
        responseDTO.setMsg("用户退出");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
