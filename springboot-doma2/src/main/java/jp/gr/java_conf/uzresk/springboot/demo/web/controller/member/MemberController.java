package jp.gr.java_conf.uzresk.springboot.demo.web.controller.member;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.gr.java_conf.uzresk.springboot.demo.dao.MemberDao;
import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.LoginUserDetails;
import jp.gr.java_conf.uzresk.springboot.framework.validator.order.CheckOrder;

@Controller
@RequestMapping("member")
@SessionAttributes(types = { MemberForm.class, Member.class })
public class MemberController {

	@Autowired
	private MemberDao memberDao;

	// @Autowired
	// SignupValidator signupValidator;

	@ModelAttribute
	MemberForm setUpForm() {
		return new MemberForm();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// binder.addValidators(signupValidator);
	}

	@GetMapping
	String index(@AuthenticationPrincipal LoginUserDetails loginUserDetails, MemberForm memberForm, Model model) {

		String userId = loginUserDetails.getUsername();
		if (userId == null) {
			throw new AccessDeniedException("access denied.");
		}

		Optional<Member> member = memberDao.selectByUserId(userId);
		member.ifPresent(m -> BeanUtils.copyProperties(m, memberForm));
		member.ifPresent(m -> model.addAttribute(m));

		return "member/edit";
	}

	@PostMapping(path = "edit", params = "update")
	String edit(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			@Validated(CheckOrder.class) MemberForm memberForm, BindingResult result, Model model, Member member) {

		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(s -> System.out.println(s));
			return index(loginUserDetails, memberForm, model);
		}

		BeanUtils.copyProperties(memberForm, member);

		memberDao.update(member);

		return "redirect:complete";
	}

	@PostMapping(path = "edit", params = "goToTop")
	String goToTop() {
		System.out.println("clear");
		return "redirect:/top";
	}

	@GetMapping(path = "complete")
	String complete(SessionStatus sessionStatus) {

		sessionStatus.setComplete();

		return "member/edit-complete";
	}

}
