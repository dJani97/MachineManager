package hu.djani.Manager.control.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.service.entity.ProjectService;
import hu.djani.Manager.service.entity.UserService;

@RestController
@RequestMapping("/api/project")
public class ApiProjectController {
	private Logger logger = LoggerFactory.getLogger(ApiProjectController.class);

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@RequestMapping("/list")
	public ResponseEntity<List<Project>> listAllProjects() {
		return ResponseEntity.ok(this.projectService.getList());
	}

	@RequestMapping("/{id}")
	public ResponseEntity<Project> getMachineById(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(this.projectService.getById(id));
	}

	@RequestMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProjectById(@PathVariable(required = true) Integer id) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof User)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User user = (User) principal;
		if (user.getProjects().contains(this.projectService.getById(id))) {
			this.projectService.deleteById(id);
			return ResponseEntity.accepted().build();
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@RequestMapping("/create/{name}")
	public ResponseEntity<Void> createProjectWithName(@PathVariable(required = true) String name) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof User)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		User user = (User) principal;
		Project project = new Project(name);

		user.addProject(project);
		project.addOwner(user);

		this.projectService.save(project);
		this.userService.save(user);
		return ResponseEntity.accepted().build();
	}

}
