package jp.gr.java_conf.uzresk.sbex.web.account;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.web.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("account")
public class AccountCreateController {

	@Autowired
	AccountService accountService;

	@Autowired
	AccountValidator accountValidator;

	@ModelAttribute
	AccountCreateForm setUpForm() {
		return new AccountCreateForm();
	}

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(accountValidator);
    }

	@RequestMapping(method = RequestMethod.GET)
	String index() {
		return "account/create";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	String create(@Validated AccountCreateForm accountCreateForm,
			BindingResult result, Model model) {

		// 入力チェック
		if (result.hasErrors()) {
			return "account/create";
		}

		// Accountの登録
		Account account = new Account();
		account.setAccountId(accountCreateForm.getAccountId());
		account.setPassword(new BCryptPasswordEncoder()
				.encode(accountCreateForm.getPassword()));
		account.setMail(accountCreateForm.getMail());

		accountService.create(account);

		return "redirect:complete";
	}

	@RequestMapping(value = "complete", method = RequestMethod.GET)
	String complete() {

		return "account/complete";
	}
}
