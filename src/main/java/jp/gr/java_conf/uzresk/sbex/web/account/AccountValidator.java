package jp.gr.java_conf.uzresk.sbex.web.account;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.web.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {

	@Autowired
	AccountService accountService;

	@Override
	public boolean supports(Class<?> clazz) {
		return AccountCreateForm.class.isAssignableFrom(clazz); // (2)
	}

	@Override
	public void validate(Object target, Errors errors) {

		AccountCreateForm form = (AccountCreateForm) target;
		String accountId = form.getAccountId();
		Account account = accountService.findAccount(accountId);

		if (account == null) {
			return;
		}
		errors.rejectValue("accountId",
				"AccountValidator.duplicateAccountId", "違うIDをご利用ください。");
	}
}
