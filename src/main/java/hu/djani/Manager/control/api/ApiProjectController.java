package hu.djani.Manager.control.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.service.AuthorizationService;
import hu.djani.Manager.service.entity.ProjectService;
import hu.djani.Manager.service.entity.UserService;

@RestController
@RequestMapping("/api/project")
public class ApiProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	AuthorizationService authService;

	@RequestMapping("/list")
	public ResponseEntity<List<Project>> listAllProjects() {
		return ResponseEntity.ok(this.projectService.getList());
	}

	@RequestMapping("/{id}")
	public ResponseEntity<Project> getMachineById(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(this.projectService.getById(id));
	}

	@RequestMapping("/delete/{id}")
	public ResponseEntity<Void> deleteProjectById(@PathVariable(required = true) Integer id,
			@AuthenticationPrincipal User principal) {

		if (!(principal instanceof User)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		if (!projectService.exists(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		if (!authService.isCurrentUserOwnerOf(projectService.getById(id))) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		this.projectService.deleteById(id);
		return ResponseEntity.accepted().build();
	}

	@RequestMapping("/create/{name}")
	public ResponseEntity<Void> createProjectWithName(@PathVariable(required = true) String name,
			@AuthenticationPrincipal User principal) {

		if (!(principal instanceof User)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		User user = principal;

		Project project = new Project(name);
		project.addOwner(user);

		this.projectService.save(project);
		this.userService.save(user);

		return ResponseEntity.accepted().build();
	}

}
