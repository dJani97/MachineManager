package hu.djani.Manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Machine;
import hu.djani.Manager.bean.MachineGroup;
import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.bean.UserRole;
import hu.djani.Manager.service.entity.ProjectService;
import hu.djani.Manager.service.entity.UserRoleService;
import hu.djani.Manager.service.entity.UserService;

@Service
public class AuthorizationService {

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService roleService;

	public boolean isCurrentUserOwnerOf(Machine machine) {
		return this.isCurrentUserOwnerOf(machine.getGroup());
	}

	public boolean isCurrentUserOwnerOf(MachineGroup group) {
		return this.isCurrentUserOwnerOf(group.getProject());
	}

	public boolean isCurrentUserOwnerOf(Project project) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!(principal instanceof User)) {
			return false;
		}

		User user = (User) principal;
		UserRole adminRole = this.roleService.getByName(UserRoleService.ADMIN_ROLE);

		boolean isOwner = user.getProjects().contains(project);
		boolean isAdmin = user.hasRole(adminRole);

		return isOwner || isAdmin;
	}

	public boolean isUserOwnerOf(User user, Project project) {
		if (user == null) {
			return false;
		}

		UserRole adminRole = this.roleService.getByName(UserRoleService.ADMIN_ROLE);

		boolean isOwner = user.getProjects().contains(project);
		boolean isAdmin = user.hasRole(adminRole);

		return isOwner || isAdmin;
	}

	// public boolean userHasRightToProjectSuperSafe(User user, Project project) {
	// if (user == null) {
	// return false;
	// }
	//
	// UserRole adminRole = this.roleService.getByName(UserRoleService.ADMIN_ROLE);
	//
	// boolean isOwner = this.userService.getById(user.getId()).getProjects()
	// .contains(this.projectService.getById(project.getId()));
	// boolean isAdmin = this.userService.getById(user.getId()).hasRole(adminRole);
	//
	// return isOwner || isAdmin;
	// }

}
