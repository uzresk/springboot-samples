package jp.gr.java_conf.uzresk.springboot.framework.validator.order;

import javax.validation.GroupSequence;

@GroupSequence({RequiredCheck.class, AttributeCheck.class})
public interface CheckOrder {

}
