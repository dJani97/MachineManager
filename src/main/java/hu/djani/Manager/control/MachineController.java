package hu.djani.Manager.control;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.service.entity.MachineGroupService;
import hu.djani.Manager.service.entity.MachineService;
import hu.djani.Manager.service.entity.ProjectService;

@Controller
@RequestMapping("/machine")
public class MachineController {

	@Autowired
	MachineService machineService;
	@Autowired
	MachineGroupService groupService;
	@Autowired
	ProjectService projectService;

	@RequestMapping({ "/list", "" })
	public String listMachines(Model model) {
		model.addAttribute("projects", this.projectService.getList());
		model.addAttribute("groups", this.groupService.getList());

		return "machine/list";
	}

	@RequestMapping("/table")
	public String machineTable(Model model) {
		model.addAttribute("machines", this.machineService.getList());
		return "machine/table";
	}

	@GetMapping("/view/{id}")
	public String viewMachine(Model model, @PathVariable Integer id) {
		model.addAttribute("machine", this.machineService.getById(id));
		return "machine/view";
	}

	@GetMapping("/new")
	public String newMachineGet(Model model) {
		model.addAttribute("projects", this.projectService.getList());
		model.addAttribute("groups", this.groupService.getList());

		model.addAttribute("machine", new Machine());

		return "machine/edit";
	}

	@GetMapping("/edit/{id}")
	public String editMachineGet(Model model, @PathVariable Integer id) {
		model.addAttribute("projects", this.projectService.getList());
		model.addAttribute("groups", this.groupService.getList());
		model.addAttribute("machine", this.machineService.getById(id));

		return "machine/edit";
	}

	@PostMapping("/edit")
	public String editMachinePost(Model model, @Valid Machine machine, BindingResult bindingResult, Integer groupId) {
		model.addAttribute("projects", this.projectService.getList());
		model.addAttribute("groups", this.groupService.getList());

		System.out.println("Machine posted:\n" + machine + "\nGroup ID: " + groupId);
		if (groupId != null) {
			machine.setGroup(this.groupService.getById(groupId));
		} else {
			model.addAttribute("success", false);
			return "machine/edit";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("success", false);
			return "machine/edit";
		}

		this.machineService.save(machine);
		model.addAttribute("machine", machine);
		model.addAttribute("success", true);

		return "machine/edit";
	}

	@GetMapping("/delete/{id}")
	public String deleteMachine(HttpServletRequest request, @PathVariable Integer id, @RequestParam String source) {
		this.machineService.deleteById(id);
		if (source != null) {
			return "redirect:/machine/" + source;
		}
		return "redirect:/machine/list";
	}
}
