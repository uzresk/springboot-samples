package jp.gr.java_conf.uzresk.springboot.framework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class GlobalErrorController implements ErrorController {

    public static final String ERROR_EXCEPTION = "jp.gr.java_conf.springboot.framework.error.exception";

    public static final String ERROR_REQUEST_URI = "jp.gr.java_conf.springboot.framework.error.request_uri";

    public static final String ERROR_STATUS_CODE = "jp.gr.java_conf.springboot.framework.error.status_code";

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = "${server.error.path:${error.path:/error}}")
    public String error(HttpServletRequest req, HttpServletResponse res, WebRequest webRequest) {

        Throwable error = errorAttributes.getError(new ServletRequestAttributes(req));
        req.setAttribute(ERROR_EXCEPTION, error);

        String requestURI = (String) req.getAttribute("javax.servlet.forward.request_uri");
        req.setAttribute(ERROR_REQUEST_URI, requestURI);

        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        req.setAttribute(ERROR_STATUS_CODE, statusCode);

        log.warn("Caught an error. requestURI = {}", requestURI);

        return "forward:/systemError";
    }
}
