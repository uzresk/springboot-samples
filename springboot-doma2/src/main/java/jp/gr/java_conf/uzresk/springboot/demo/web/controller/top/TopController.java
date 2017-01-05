package jp.gr.java_conf.uzresk.springboot.demo.web.controller.top;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("top")
public class TopController {

    @GetMapping
    String index() {
        return "top/top";
    }

}
