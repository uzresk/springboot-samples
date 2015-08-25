package jp.gr.java_conf.uzresk.sbex.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

	@RequestMapping("/logout")
	String logout() {
		return "common/logout";
	}
}
