package hu.djani.Manager.service.entity;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.Project;
import hu.djani.Manager.bean.User;
import hu.djani.Manager.bean.VerificationToken;
import hu.djani.Manager.dao.ProjectDao;
import hu.djani.Manager.dao.UserDao;
import hu.djani.Manager.dao.VerificationTokenDao;
import hu.djani.Manager.security.MachineSessionRegistryImpl;

@Service
public class UserService implements UserDetailsService {
	private UserDao userDao;
	private ProjectDao projectDao;
	private VerificationTokenDao tokenDao;
	private MachineSessionRegistryImpl sessionRegistry;

	@Autowired
	public UserService(UserDao userDao, VerificationTokenDao tokenDao, ProjectDao projectDao,
			MachineSessionRegistryImpl sessionRegistry) {
		this.userDao = userDao;
		this.tokenDao = tokenDao;
		this.projectDao = projectDao;
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userDao.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	@Transactional
	public void registerNewUser(User user) {
		if (this.isEmailAvailable(user.getEmail())) {
			this.userDao.save(user);
		}
	}

	public boolean isEmailAvailable(String email) {
		return !this.userDao.existsByUsername(email);
	}

	public void createVerificationTokenForUser(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		this.tokenDao.save(myToken);
	}

	public VerificationToken getVerificationToken(String VerificationToken) {
		return this.tokenDao.findByToken(VerificationToken);
	}

	public void save(User user) {
		this.userDao.save(user);
	}

	public List<User> getList() {
		return this.userDao.findAll();
	}

	public User getById(Long id) {
		return this.userDao.getOne(id);
	}

	public Long count() {
		return this.userDao.count();
	}

	public void deleteById(Long id) {
		User user = this.userDao.getOne(id);
		Set<Project> projects = user.getProjects();
		for (Project project : projects) {
			Set<User> owners = project.getOwners();
			owners.remove(user);
			project.setOwners(owners);
			this.projectDao.save(project);
		}

		this.tokenDao.delete(this.tokenDao.findByUser(user));

		this.sessionRegistry.forceLogout(user);

		this.userDao.deleteById(id);

	}

}
