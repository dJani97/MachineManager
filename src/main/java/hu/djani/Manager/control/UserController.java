package hu.djani.Manager.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.service.entity.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping("/view/{id}")
	public String viewUser(Model model, @PathVariable("id") long id) {
		User user = this.userService.getById(id);
		model.addAttribute("user", user);
		return "user/view";
	}

	@RequestMapping("/list")
	public String listUsers(Model model) {
		List<User> users = this.userService.getList();
		model.addAttribute("users", users);
		return "user/list";
	}
}
