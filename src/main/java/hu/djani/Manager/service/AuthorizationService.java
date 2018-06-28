package hu.djani.Manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public boolean userHasRightToProject(User user, Project project) {
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
