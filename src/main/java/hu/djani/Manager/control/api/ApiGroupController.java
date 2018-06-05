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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.MachineGroup;
import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.service.entity.MachineGroupService;
import hu.djani.Manager.service.entity.ProjectService;
import hu.djani.Manager.service.entity.UserService;

@RestController
@RequestMapping("/api/group")
public class ApiGroupController {
	private Logger logger = LoggerFactory.getLogger(ApiGroupController.class);

	@Autowired
	MachineGroupService groupService;

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@RequestMapping("/list")
	public ResponseEntity<List<MachineGroup>> listAllGroups() {
		return ResponseEntity.ok(this.groupService.getList());
	}

	@RequestMapping("/{id}")
	public ResponseEntity<MachineGroup> getGroupById(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(this.groupService.getById(id));
	}

	@RequestMapping("/delete/{id}")
	public ResponseEntity<Void> deleteGroupById(@PathVariable(required = true) Integer id) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof User)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		MachineGroup group = this.groupService.getById(id);
		if (group == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Long userId = ((User) principal).getId();
		if (this.userService.getById(userId).getProjects().contains(group.getProject())) {
			this.groupService.deleteById(id);
			return ResponseEntity.accepted().build();
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@RequestMapping("/create/{name}")
	public ResponseEntity<Void> createGroupWithName(@PathVariable(required = true) String name,
			@RequestParam(required = true) int project_id) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!(principal instanceof User)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Project project = this.projectService.getById(project_id);
		if (project == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		Long userId = ((User) principal).getId();
		if (this.userService.getById(userId).getProjects().contains(this.projectService.getById(project_id))) {
			MachineGroup group = new MachineGroup(name, project);
			this.groupService.save(group);
			return ResponseEntity.accepted().build();
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

}
