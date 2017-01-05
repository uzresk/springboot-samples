package jp.gr.java_conf.uzresk.springboot.framework.thymeleaf.expression;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;

import java.util.Map;

public class ExpressionUtilityObjectsDialect extends AbstractDialect implements IExpressionEnhancingDialect {

    private Map<String, Object> expressionObjects;

    public ExpressionUtilityObjectsDialect(Map<String, Object> expressionObjects) {
        super();
        this.expressionObjects = expressionObjects;
    }

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(IProcessingContext processingContext) {
        return expressionObjects;
    }

    @Override
    public String getPrefix() {
        return null;
    }
}
