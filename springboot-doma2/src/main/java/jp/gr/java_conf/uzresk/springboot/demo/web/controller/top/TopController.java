package jp.gr.java_conf.uzresk.springboot.demo.web.controller.top;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("top")
public class TopController {

	@GetMapping
	String index(Model model) {
		return "top/top";
	}

}
