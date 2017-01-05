package jp.gr.java_conf.uzresk.springboot.framework.thymeleaf.expression;

import jp.gr.java_conf.uzresk.springboot.framework.code.Code;
import jp.gr.java_conf.uzresk.springboot.framework.code.CodeManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CodeUtility {

    private final CodeManager codeManager;

    public List<Code> get(String id) {
        return codeManager.getCodes(id);
    }
}
