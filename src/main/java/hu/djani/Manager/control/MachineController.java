package hu.djani.Manager.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.djani.Manager.service.MachineService;

@Controller
@RequestMapping("/machine/")
public class MachineController {

	@Autowired
	MachineService machineService;

	@RequestMapping("/list")
	public String listMachines(Model model) {
		model.addAttribute("machines", this.machineService.getList());
		return "machine/list";
	}
}
