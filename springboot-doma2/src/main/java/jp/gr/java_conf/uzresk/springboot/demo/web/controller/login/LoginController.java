package jp.gr.java_conf.uzresk.springboot.demo.web.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping(path = "/loginForm")
    public String loginForm() {

        return "login/login";
    }
}
