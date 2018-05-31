package hu.djani.Manager.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
