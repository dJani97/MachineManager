package hu.djani.Manager.control;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.service.MachineService;

@Controller
@RequestMapping("/machine")
public class MachineController {

	@Autowired
	MachineService machineService;

	@RequestMapping({"/list", ""})
	public String listMachines(Model model) {
		model.addAttribute("machines", this.machineService.getList());
		return "machine/list";
	}

	@GetMapping("/view/{id}")
	public String viewMachine(Model model, @PathVariable Integer id) {
		model.addAttribute("machine", this.machineService.getById(id));
		return "machine/view";
	}

	@GetMapping("/new")
	public String newMachineGet(Model model) {
		model.addAttribute("machine", new Machine());
		return "machine/edit";
	}

	@GetMapping("/edit/{id}")
	public String editMachineGet(Model model, @PathVariable Integer id) {
		model.addAttribute("machine", this.machineService.getById(id));
		return "machine/edit";
	}

	//	@PostMapping("/edit")
	//	public String editMachinePost(Model model, @ModelAttribute Machine machine) {
	//		System.out.println("Machine posted:");
	//		System.out.println(machine);
	//		model.addAttribute("machine", machine);
	//		return "machine/edit";
	//	}

	//	@RequestMapping(value="/edit", method=RequestMethod.POST)
	//	public String editMachinePost(@Valid Machine machine, BindingResult bindingResult) {
	//		System.out.println("Machine posted:\n" + machine);
	//		if(bindingResult.hasErrors()) {
	//			return "machine/edit";
	//		}
	//		return "machine/view";
	//	}

	@PostMapping("/edit")
	public String editMachinePost(Model model, @Valid Machine machine, BindingResult bindingResult) {
		System.out.println("Machine posted:\n" + machine);
		if (bindingResult.hasErrors()) {
			model.addAttribute("success", false);
			return "machine/edit";
		}
		this.machineService.save(machine);
		model.addAttribute("success", true);


		System.out.println("Machine posted:\n" + machine);

		model.addAttribute("machine", machine);
		return "machine/edit";
	}
}
