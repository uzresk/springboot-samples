package jp.gr.java_conf.uzresk.springboot.framework.aop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
public class DumpLogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.debug("BEGIN - " + request.getRequestURL() + " - " + request.getSession().getId());

        // dump request parameter
        List<String> o = new ArrayList<>();
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        requestParameterMap.keySet().stream()
                .forEach(key -> o.add(key + Arrays.deepToString(requestParameterMap.get(key))));
        log.trace("  args - " + o.stream().sorted(Comparator.naturalOrder()).collect(Collectors.joining(",")));

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        log.debug("END   - " + request.getRequestURL() + " - " + request.getSession().getId());

        super.postHandle(request, response, handler, modelAndView);
    }
}