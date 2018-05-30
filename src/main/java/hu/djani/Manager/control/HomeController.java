package hu.djani.Manager.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hu.djani.Manager.component.OsInfoComponent;

@Controller
public class HomeController {

	@Autowired
	OsInfoComponent info;

	@RequestMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("raisePermissionAlert", !this.info.isRunningAsAdmin());
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	// @RequestMapping("/logout")
	// public String logout(Model model) {
	// model.addAttribute("logout");
	// return "login";
	// }

}
