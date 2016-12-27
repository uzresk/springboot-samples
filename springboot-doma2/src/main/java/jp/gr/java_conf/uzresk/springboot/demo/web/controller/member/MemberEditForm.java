package jp.gr.java_conf.uzresk.springboot.demo.web.controller.member;

import jp.gr.java_conf.uzresk.springboot.framework.validator.annotation.Code;
import jp.gr.java_conf.uzresk.springboot.framework.validator.order.AttributeCheck;
import org.hibernate.validator.constraints.NotEmpty;

import jp.gr.java_conf.uzresk.springboot.framework.validator.order.RequiredCheck;
import lombok.Data;

@Data
public class MemberEditForm {

    @NotEmpty(groups = RequiredCheck.class)
    private String name;

    @Code(value = "GENDER", groups = AttributeCheck.class)
    @NotEmpty(groups = RequiredCheck.class)
    private String gender;

    @NotEmpty(groups = RequiredCheck.class)
    private String authority;
}
