package jp.gr.java_conf.uzresk.springboot.framework.validator.validators;

import jp.gr.java_conf.uzresk.springboot.framework.code.CodeManager;
import jp.gr.java_conf.uzresk.springboot.framework.validator.annotation.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class CodeValidator implements ConstraintValidator<Code, String> {

    private String value;

    @Autowired
    CodeManager codeManager;

    @Override
    public void initialize(Code code) {
        value = code.value();
    }

    @Override
    public boolean isValid(String in, ConstraintValidatorContext ctx) {
        if (in == null) {
            return true;
        }
        log.trace("CodeValidator in[" + in + "] codeId[" + value + "]");
        return codeManager.contains(value, in);
    }
}
