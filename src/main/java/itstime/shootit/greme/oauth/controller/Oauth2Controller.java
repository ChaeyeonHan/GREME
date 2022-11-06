package itstime.shootit.greme.oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @GetMapping("/login")
    public String login(){
        System.out.println("redirect url: /oauth2/login");
        return "success";
    }
}
