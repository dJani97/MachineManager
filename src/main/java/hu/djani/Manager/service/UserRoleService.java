package hu.djani.Manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.UserRole;
import hu.djani.Manager.dao.UserRoleDao;

@Service
public class UserRoleService {
	public static final String ADMIN_ROLE = "ROLE_ADMIN";
	public static final String USER_ROLE = "ROLE_USER";

	private UserRoleDao roleDao;

	@Autowired
	public UserRoleService(UserRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<UserRole> getAllUsers() {
		return this.roleDao.findAll();
	}

	public void save(UserRole userRole) {
		this.roleDao.save(userRole);
	}

	public UserRole getByName(String roleName) {
		return this.roleDao.findByAuthority(roleName);
	}

}
