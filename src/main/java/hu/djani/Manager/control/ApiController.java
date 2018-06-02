package hu.djani.Manager.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.component.PingComponent;
import hu.djani.Manager.service.entity.MachineService;
import hu.djani.Manager.service.entity.ProjectService;
import hu.djani.Manager.service.entity.UserService;

@RestController
@RequestMapping("/api/")
public class ApiController {
	private Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	MachineService machineService;

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	PingComponent pingComponent;

	@RequestMapping("/machines")
	public ResponseEntity<List<Machine>> listAllMachines() {
		return ResponseEntity.ok(this.machineService.getList());
	}

	@RequestMapping("/projects")
	public ResponseEntity<List<Project>> listAllProjects() {
		return ResponseEntity.ok(this.projectService.getList());
	}

	@RequestMapping("/users")
	public ResponseEntity<List<User>> listAllUsers() {
		return ResponseEntity.ok(this.userService.getList());
	}

	/*
	 * machine services
	 */

	@RequestMapping("/machine/{id}")
	public ResponseEntity<Machine> getMachineById(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(this.machineService.getById(id));
	}

	@RequestMapping("/machine/addDummy")
	public ResponseEntity<Void> getMachineById() {
		this.machineService.save(new Machine("dummy", "dummy leiras", "10.11.16.69", new Date(), null));
		return ResponseEntity.accepted().build();
	}

	@RequestMapping("/ping/{address}")
	public ResponseEntity<Boolean> isReachable(@PathVariable(required = true) String address) {
		// System.out.println("ping called with: " + address);
		return ResponseEntity.ok(this.pingComponent.isReachable(address, 100));
	}

	@RequestMapping("/machine/running")
	public ResponseEntity<List<Machine>> pingAll() {
		return ResponseEntity.ok(this.getRunningMachines());
	}

	@RequestMapping("/machine/countRunning")
	public ResponseEntity<Integer> countRunning() {
		return ResponseEntity.ok(this.getRunningMachines().size());
	}

	@RequestMapping("/machine/count")
	public ResponseEntity<Long> countMachines() {
		return ResponseEntity.ok(this.machineService.count());
	}

	private List<Machine> getRunningMachines() {
		List<Machine> machines = this.machineService.getList();
		List<Machine> reachableMachines = new ArrayList<>();
		// @formatter:off

		machines.parallelStream()
			.filter(m -> this.pingComponent.isReachable(m.getAddress(), 100))
			.forEach(machine -> reachableMachines.add(machine));

		return reachableMachines;
	}

}
