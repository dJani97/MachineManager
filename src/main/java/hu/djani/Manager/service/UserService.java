package hu.djani.Manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.dao.UserDao;

@Service
public class UserService implements UserDetailsService {
	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userDao.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}

	public List<User> getAllUsers() {
		return this.userDao.findAll();
	}

	public void save(User user) {
		this.userDao.save(user);
	}

}
