package jp.gr.java_conf.uzresk.springboot.demo.web.controller.signup;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.framework.validator.order.CheckOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("signup")
@AllArgsConstructor
public class SignupController {

    private final MemberDao memberDao;

    private final SignupValidator signupValidator;

    @ModelAttribute
    SignupForm setUpForm() {
        return new SignupForm();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(signupValidator);
    }

    @GetMapping
    public String index() {

        return "signup/signup";
    }

    @PostMapping(path = "create")
    public String create(@Validated(CheckOrder.class) SignupForm signupForm, BindingResult result) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return index();
        }

        Member member = new Member();
        BeanUtils.copyProperties(signupForm, member);
        member.setPassword(new BCryptPasswordEncoder().encode(signupForm.getPassword()));
        memberDao.insert(member);

        return "redirect:complete";
    }

    @GetMapping(path = "complete")
    public String complete() {

        return "signup/signup-complete";
    }
}
