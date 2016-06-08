package jp.gr.java_conf.uzresk.sbex.web.top;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.gr.java_conf.uzresk.sbex.entity.ItunesSearchResult;
import jp.gr.java_conf.uzresk.sbex.entity.Media;
import jp.gr.java_conf.uzresk.sbex.web.common.WebUtils;
import jp.gr.java_conf.uzresk.sbex.web.service.ItunesSearchService;
import jp.gr.java_conf.uzresk.sbex.web.service.LoginAccountDetails;

@Controller
public class TopController {

	Logger logger = LoggerFactory.getLogger(TopController.class);

	@Autowired
	private HttpSession session;

	@Autowired
	ItunesSearchService itunesSearchService;

	@ModelAttribute
	SearchForm setUpForm() {
		return new SearchForm();
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	String top(SearchForm searchForm,
			@AuthenticationPrincipal LoginAccountDetails loginAccountDetails,
			Model model) {
		
		System.out.println("SESSIONID->" + session.getId());

		WebUtils.clearSession(session);

		logger.debug("login succeeded!:" + loginAccountDetails);

		model.addAttribute("media", Media.nameValueMap());

		return "top/search";
	}

	@RequestMapping(value = "/top/search", method = RequestMethod.POST)
	String search(SearchForm searchForm,
			@AuthenticationPrincipal LoginAccountDetails loginAccountDetails,
			Model model) {

		model.addAttribute("media", Media.nameValueMap());
		ItunesSearchResult result = itunesSearchService.search(
				Media.fromValue(searchForm.getMedia()), searchForm.getTerm());

		model.addAttribute("resultCount", result.getResultCount());
		model.addAttribute("result", result.getResults());

		return "top/search";
	}
}
