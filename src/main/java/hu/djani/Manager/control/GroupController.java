package hu.djani.Manager.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.djani.Manager.bean.MachineGroup;
import hu.djani.Manager.service.entity.MachineGroupService;

@Controller
@RequestMapping("/group")
public class GroupController {
	private Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	MachineGroupService groupService;

	@RequestMapping("/view/{id}")
	public String viewUser(Model model, @PathVariable("id") int id) {
		MachineGroup group = this.groupService.getById(id);
		model.addAttribute("group", group);
		return "group/view";
	}

}
