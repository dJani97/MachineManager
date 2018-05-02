package hu.djani.Manager.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.djani.Manager.bean.Machine;
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

	@GetMapping("/edit")
	public String editMachineGet(Model model) {
		model.addAttribute("machine", this.machineService.getById(4));
		return "machine/edit";
	}

	@PostMapping("/edit")
	public String editMachinePost(Model model, @ModelAttribute Machine machine) {
		System.out.println("Machine posted:");
		System.out.println(machine);
		model.addAttribute("machine", machine);
		return "machine/edit";
	}
}
