package hu.djani.Manager.control.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.security.MachineSessionRegistryImpl;
import hu.djani.Manager.service.entity.UserService;

@RestController
@RequestMapping("/api/user/")
public class ApiUserController {
	private Logger logger = LoggerFactory.getLogger(ApiUserController.class);

	@Autowired
	UserService userService;

	@Autowired
	private MachineSessionRegistryImpl sessionRegistryImpl;

	@RequestMapping("/list")
	public ResponseEntity<List<User>> listAllUsers() {
		return ResponseEntity.ok(this.userService.getList());
	}

	@RequestMapping("/count")
	public ResponseEntity<Long> countUsers() {
		return ResponseEntity.ok(this.userService.count());
	}

	@RequestMapping("/listOnline")
	public ResponseEntity<List<User>> listOnlineUsers() {
		return ResponseEntity.ok(this.sessionRegistryImpl.getUsersFromSessionRegistry());
	}

	@RequestMapping("/countOnline")
	public ResponseEntity<Integer> countOnlineUsers() {
		return ResponseEntity.ok(this.sessionRegistryImpl.getActiveSessionCount());
	}

	@RequestMapping("/isOnline/{id}")
	public ResponseEntity<Boolean> isUserOnline(@PathVariable("id") Long id) {
		User user = this.userService.getById(id);
		return ResponseEntity.ok(this.sessionRegistryImpl.isActive(user));
	}

	@RequestMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUserById(HttpServletRequest request, @PathVariable(required = true) Long id) {

		if (!request.isUserInRole("ADMIN")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		this.userService.deleteById(id);
		return ResponseEntity.accepted().build();
	}

}
