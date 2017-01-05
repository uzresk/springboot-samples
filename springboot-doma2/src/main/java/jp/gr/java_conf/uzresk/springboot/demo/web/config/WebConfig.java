package jp.gr.java_conf.uzresk.springboot.demo.web.config;

import jp.gr.java_conf.uzresk.springboot.framework.thymeleaf.expression.CodeUtility;
import jp.gr.java_conf.uzresk.springboot.framework.thymeleaf.expression.ExpressionUtilityObjectsDialect;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jp.gr.java_conf.uzresk.springboot.framework.aop.DumpLogInterceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class WebConfig extends WebMvcConfigurerAdapter {

    private final MessageSource messageSource;

    private final CodeUtility codeUtility;

    /**
     * Validatorで利用するメッセージをMessageResourceから取得するように変更
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Bean
    ExpressionUtilityObjectsDialect expressionUtilityObjectsDialect() {

        Map<String, Object> objects = new HashMap<>();
        objects.put("code", codeUtility);
        return new ExpressionUtilityObjectsDialect(Collections.unmodifiableMap(objects));
    }

    @Override
    public org.springframework.validation.Validator getValidator() {
        return validator();
    }

    /**
     * Interceptorの設定
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // controllerの始めと終わりにログを出力する
        registry.addInterceptor(new DumpLogInterceptor()).addPathPatterns("/**");
    }

    /**
     * indexページの設定
     */
    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // forward requests to /admin and /user to their index.html
                registry.addViewController("/").setViewName("forward:/loginForm");
            }
        };
    }

}
