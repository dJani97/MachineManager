package hu.djani.Manager.control;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.service.UserService;

@Controller
public class AuthController {

	@Autowired
	UserService userService;

	@RequestMapping("/login")
	public String login(Model model) {
		return "auth/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@GetMapping("/register")
	public String registerGet(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "auth/register";
	}

	@PostMapping("/register")
	public String registerGet(Model model, @Valid User user, BindingResult bindingResult, HttpServletRequest request)
			throws ServletException {
		System.out.println("User posted: " + user);

		if (bindingResult.hasErrors()) {
			model.addAttribute("success", false);
			model.addAttribute("user", user);
			return "auth/register";
		}

		if (!this.userService.isEmailAvailable(user.getEmail())) {
			model.addAttribute("nameTaken", true);
			model.addAttribute("user", user);
			return "auth/register";
		}

		String rawPassword = user.getPassword();
		user.setPassword(new BCryptPasswordEncoder().encode(rawPassword));

		this.userService.save(user);

		request.login(user.getUsername(), rawPassword);
		model.addAttribute("success", true);

		return "redirect:user/view/" + user.getId(); // user/user/id TODO
	}
}
