package hu.djani.Manager.control;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.bean.VerificationToken;
import hu.djani.Manager.event.OnRegistrationCompleteEvent;
import hu.djani.Manager.service.entity.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	ApplicationEventPublisher eventPublisher;

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

		if (bindingResult.hasErrors()) {
			model.addAttribute("success", false);
			model.addAttribute("user", user);
			return "auth/register";
		}

		if (!this.userService.isEmailAvailable(user.getEmail())) {
			model.addAttribute("nameTaken", true);
			bindingResult.rejectValue("username", "", "Ez a cím már szerepel az adatbázisban."); // , "
			model.addAttribute("user", user);
			return "auth/register";
		}

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		this.userService.registerNewUser(user);

		try {
			String appUrl = request.getServerName() + ":" + request.getServerPort();
			this.eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
			model.addAttribute("success", true);

		} catch (Exception ex) {
			this.logger.error("Error Sending Email: " + ex.getMessage());
			return "auth/register";
		}

		return "redirect:/";
		// return "redirect:user/view/" + user.getId(); // user/user/id TODO
	}

	@RequestMapping(value = "/confirmAccount", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

		VerificationToken verificationToken = this.userService.getVerificationToken(token);
		if (verificationToken == null) {
			model.addAttribute("verificationSuccess", false);
			model.addAttribute("verificationMessage", "Nem létező token!");
			return "auth/login";
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			model.addAttribute("verificationSuccess", false);
			model.addAttribute("verificationMessage", "Lejárt token!");
			return "auth/login";
		}

		user.setEnabled(true);
		this.userService.save(user);
		model.addAttribute("email", user.getEmail());
		model.addAttribute("verificationSuccess", true);
		return "auth/login";
	}

}
