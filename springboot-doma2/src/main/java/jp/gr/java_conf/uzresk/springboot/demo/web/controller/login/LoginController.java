package jp.gr.java_conf.uzresk.springboot.demo.web.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@GetMapping(path = "/loginForm")
	public String loginForm() {

		return "login/login";
	}
}
