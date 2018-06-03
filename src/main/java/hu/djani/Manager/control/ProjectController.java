package hu.djani.Manager.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hu.djani.Manager.bean.Project;
import hu.djani.Manager.service.entity.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController {
	private Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	@RequestMapping("/view/{id}")
	public String viewUser(Model model, @PathVariable("id") int id) {
		Project project = this.projectService.getById(id);
		model.addAttribute("project", project);
		return "project/view";
	}

	@RequestMapping("/list")
	public String listUsers(Model model) {
		List<Project> projects = this.projectService.getList();
		model.addAttribute("projects", projects);
		return "project/list";
	}
}
