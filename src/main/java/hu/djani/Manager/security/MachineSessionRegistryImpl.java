package hu.djani.Manager.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Component;

import hu.djani.Manager.bean.User;

@Component
public class MachineSessionRegistryImpl extends SessionRegistryImpl {

	public List<User> getUsersFromSessionRegistry() {
		// @formatter:off
	    return this.getAllPrincipals().stream()
	      .filter(u -> !this.getAllSessions(u, false).isEmpty())
	      .filter(User.class::isInstance)
	      .map(User.class::cast)
	      .collect(Collectors.toList());
	}

	public int getActiveSessionCount() {
		return this.getUsersFromSessionRegistry().size();
	}

	public boolean isActive(User user) {
		return this.getUsersFromSessionRegistry().contains(user);
	}
}
