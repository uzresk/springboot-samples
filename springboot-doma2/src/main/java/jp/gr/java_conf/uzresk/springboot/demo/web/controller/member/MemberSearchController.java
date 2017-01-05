package jp.gr.java_conf.uzresk.springboot.demo.web.controller.member;

import jp.gr.java_conf.uzresk.springboot.demo.entity.Member;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.MemberSearchCondition;
import jp.gr.java_conf.uzresk.springboot.demo.web.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("member/search")
@SessionAttributes(types = {MemberSearchForm.class})
@AllArgsConstructor
public class MemberSearchController {

    private final MemberService memberService;

    @ModelAttribute("memberSearchForm")
    MemberSearchForm setUpForm() {
        return new MemberSearchForm();
    }

    @GetMapping
    public String index(SessionStatus sessionStatus) {

        // sessionからformを削除
        sessionStatus.setComplete();

        // setCompleteが動くタイミングはControllerの終了後なのでinitを経由させる
        return "redirect:/member/search/init";
    }

    @GetMapping(path = "init")
    public String init(@PageableDefault(size = 1) Pageable pageable, Model model) {
        return list(new MemberSearchForm(), pageable, model);
    }

    @GetMapping(path = "paging")
    public String paging(MemberSearchForm memberSearchForm, @PageableDefault(size = 1) Pageable pageable, Model model) {

        return list(memberSearchForm, pageable, model);
    }

    @PostMapping(path = "list")
    public String list(MemberSearchForm memberSearchForm, @PageableDefault(size = 1) Pageable pageable, Model model) {

        MemberSearchCondition condition = new MemberSearchCondition(pageable);
        BeanUtils.copyProperties(memberSearchForm, condition);

        Page<Member> page = memberService.search(condition);
        model.addAttribute("page", page);

        return "member/list";
    }

}
