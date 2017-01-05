package jp.gr.java_conf.uzresk.springboot.demo.web.controller.signup;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SignupValidator implements Validator {

    private final MemberDao memberDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignupForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        SignupForm signupForm = (SignupForm) target;

        // パスワードと確認用パスワードのチェック
        String password = signupForm.getPassword();
        String confirmPassword = signupForm.getConfirmPassword();

        if (password == null || confirmPassword == null) {
            return;
        }
        if (!password.equals(confirmPassword)) {
            errors.rejectValue("password", "signupValidator.passwordMismatch");
        }

        // ユーザIDの存在チェック
        Optional<Member> optional = memberDao.selectByUserId(signupForm.getUserId());
        optional.ifPresent(m -> errors.rejectValue("userId", "signupValidator.duplicateUserId"));

    }

}
