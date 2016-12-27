package jp.gr.java_conf.uzresk.springboot.demo.web.controller.error;

import jp.gr.java_conf.uzresk.springboot.framework.controller.GlobalErrorController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Controller
public class DemoErrorController {

    @RequestMapping(value = "/systemError")
    public String error(HttpServletRequest request, Model model) {

        Optional<Throwable> exception = Optional.ofNullable((Throwable) request.getAttribute(GlobalErrorController.ERROR_EXCEPTION));
        exception.ifPresent(e -> {
            log.error(e.getMessage(), e);
            model.addAttribute("stackTrace", ExceptionUtils.getStackTrace(e));
            model.addAttribute("message", e.getMessage());
        });

        model.addAttribute("statusCode", request.getAttribute(GlobalErrorController.ERROR_STATUS_CODE));
        model.addAttribute("requestURI", request.getAttribute(GlobalErrorController.ERROR_REQUEST_URI));

        return "error/systemError";
    }
}
