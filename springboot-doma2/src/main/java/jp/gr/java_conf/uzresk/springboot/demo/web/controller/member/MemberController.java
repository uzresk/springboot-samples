package jp.gr.java_conf.uzresk.springboot.demo.web.controller.member;

import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.LoginUserDetails;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.MemberService;
import jp.gr.java_conf.uzresk.springboot.framework.validator.order.CheckOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("member")
@SessionAttributes(types = {MemberEditForm.class, Member.class})
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("memberEditForm")
    MemberEditForm setUpForm() {
        return new MemberEditForm();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
         // NOOP
    }

    @GetMapping
    String index(@AuthenticationPrincipal LoginUserDetails loginUserDetails, MemberEditForm memberEditForm, Model model) {
        String userId = loginUserDetails.getUsername();
        if (userId == null) {
            throw new AccessDeniedException("access denied.");
        }

        Optional<Member> member = memberService.findMemberByUserId(userId);
        member.ifPresent(m -> {
            BeanUtils.copyProperties(m, memberEditForm);
            model.addAttribute(m);
        });
        
        return "member/edit";
    }

    @PostMapping(path = "edit", params = "update")
    String edit(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                @Validated(CheckOrder.class) MemberEditForm memberEditForm, BindingResult result, Model model, Member member) {

        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(System.out::println);
            return index(loginUserDetails, memberEditForm, model);
        }

        BeanUtils.copyProperties(memberEditForm, member);

        memberService.updateMember(member);

        return "redirect:complete";
    }

    @PostMapping(path = "edit", params = "goToTop")
    String goToTop() {
        return "redirect:/top";
    }

    @GetMapping(path = "complete")
    String complete(SessionStatus sessionStatus) {

        sessionStatus.setComplete();

        return "member/edit-complete";
    }

}
