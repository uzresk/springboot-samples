package jp.gr.java_conf.uzresk.sbex.web.account;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.gr.java_conf.uzresk.sbex.entity.Account;
import jp.gr.java_conf.uzresk.sbex.web.service.AccountService;

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
	String create(@Validated AccountCreateForm accountCreateForm, BindingResult result, Model model,
			RedirectAttributes attributes) {

		// 入力チェック
		if (result.hasErrors()) {
			return "account/create";
		}

		// Accountの登録
		Account account = new Account();
		account.setAccountId(accountCreateForm.getAccountId());
		account.setPassword(new BCryptPasswordEncoder().encode(accountCreateForm.getPassword()));
		account.setMail(accountCreateForm.getMail());

		// MFA利用時にはsecret_keyを生成する。
		String secret = null;
		if ("1".equals(accountCreateForm.getUseMfa())) {
			secret = createSecret();
			account.setSecretKey(secret);
		}
		accountService.create(account);

		if (StringUtils.isNotBlank(secret)) {
			attributes.addFlashAttribute("qr", getQRBarcodeURL(account.getAccountId(), "localhost", secret));
		}

		return "redirect:complete";
	}

	@RequestMapping(value = "complete", method = RequestMethod.GET)
	String complete() {

		return "account/complete";
	}

	private String createSecret() {
		byte[] buffer = new byte[10];
		new SecureRandom().nextBytes(buffer);
		String secret = new String(new Base32().encode(buffer));
		return secret;
	}

	public static String getQRBarcodeURL(String user, String host, String secret) {
		return "http://chart.googleapis.com/chart?" + getQRBarcodeURLQuery(user, host, secret);
	}

	public static String getQRBarcodeURLQuery(String user, String host, String secret) {
		try {
			return "chs=100x100&chld=M%7C0&cht=qr&chl="
					+ URLEncoder.encode(getQRBarcodeOtpAuthURL(user, host, secret), "UTF8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static String getQRBarcodeOtpAuthURL(String user, String host, String secret) {
		return String.format("otpauth://totp/%s@%s?secret=%s", user, host, secret);
	}

}
