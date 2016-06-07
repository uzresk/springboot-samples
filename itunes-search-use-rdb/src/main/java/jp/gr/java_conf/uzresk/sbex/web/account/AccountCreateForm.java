package jp.gr.java_conf.uzresk.sbex.web.account;

import lombok.Data;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class AccountCreateForm {

	@NotEmpty
	@Length(min = 5, max = 20)
	private String accountId;

	@NotEmpty
	@Length(min = 5, max = 20)
	private String password;

	@NotEmpty
	@Email
	@Length(max = 200)
	private String mail;
	
	private String useMfa;
}
