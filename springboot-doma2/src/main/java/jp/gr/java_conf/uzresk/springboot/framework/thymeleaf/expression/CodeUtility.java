package jp.gr.java_conf.uzresk.springboot.framework.thymeleaf.expression;

import jp.gr.java_conf.uzresk.springboot.framework.code.Code;
import jp.gr.java_conf.uzresk.springboot.framework.code.CodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeUtility {

    @Autowired
    CodeManager codeManager;

    public List<Code> get(String id) {
        return codeManager.getCodes(id);
    }
}
