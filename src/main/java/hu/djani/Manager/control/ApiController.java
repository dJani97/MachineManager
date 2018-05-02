package hu.djani.Manager.control;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.component.PingComponent;
import hu.djani.Manager.service.MachineService;

@RestController
@RequestMapping("/api/")
public class ApiController {

	@Autowired
	MachineService machineService;

	@Autowired
	PingComponent pingComponent;

	@RequestMapping("/machine/all")
	public ResponseEntity<List<Machine>> listAllMachines(Model model) {

		return ResponseEntity.ok(this.machineService.getList());
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
		//		System.out.println("ping called with: " + address);
		return ResponseEntity.ok(this.pingComponent.isReachable(address, 1500));
	}

}
