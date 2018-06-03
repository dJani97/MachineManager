package hu.djani.Manager.control.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.Project;
import hu.djani.Manager.service.entity.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ApiProjectController {
	private Logger logger = LoggerFactory.getLogger(ApiProjectController.class);

	@Autowired
	ProjectService projectService;

	@RequestMapping("/list")
	public ResponseEntity<List<Project>> listAllProjects() {
		return ResponseEntity.ok(this.projectService.getList());
	}

	@RequestMapping("/{id}")
	public ResponseEntity<Project> getMachineById(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(this.projectService.getById(id));
	}

}
