package jp.gr.java_conf.uzresk.springboot.demo.web.controller.member;

import org.hibernate.validator.constraints.NotEmpty;

import jp.gr.java_conf.uzresk.springboot.framework.validator.order.RequiredCheck;
import lombok.Data;

@Data
public class MemberForm {

	@NotEmpty(groups = RequiredCheck.class)
	private String name;

	@NotEmpty(groups = RequiredCheck.class)
	private String authority;
}
