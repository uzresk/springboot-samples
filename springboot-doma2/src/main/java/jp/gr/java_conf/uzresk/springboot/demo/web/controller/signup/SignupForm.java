package jp.gr.java_conf.uzresk.springboot.demo.web.controller.signup;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import jp.gr.java_conf.uzresk.springboot.framework.validator.order.AttributeCheck;
import jp.gr.java_conf.uzresk.springboot.framework.validator.order.RequiredCheck;
import lombok.Data;

@Data
public class SignupForm {

	@NotEmpty(groups = RequiredCheck.class)
	@Length(min = 4, max = 20, groups = AttributeCheck.class)
	private String userId;

	@NotEmpty(groups = RequiredCheck.class)
	@Length(min = 7, max = 20, groups = AttributeCheck.class)
	private String password;

	private String confirmPassword;

	@NotEmpty(groups = RequiredCheck.class)
	private String name;

	@NotEmpty(groups = RequiredCheck.class)
	private String authority;
}
