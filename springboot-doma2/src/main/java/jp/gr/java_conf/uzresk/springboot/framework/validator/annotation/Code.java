package jp.gr.java_conf.uzresk.springboot.framework.validator.annotation;

import jp.gr.java_conf.uzresk.springboot.framework.validator.validators.CodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Code {

    String message() default "{jp.gr.java_conf.uzresk.springboot.framework.validator.constraints.code.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();
}
