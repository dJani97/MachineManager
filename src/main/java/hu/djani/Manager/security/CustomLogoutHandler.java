package hu.djani.Manager.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import hu.djani.Manager.bean.User;

public class CustomLogoutHandler implements LogoutHandler {

	MachineSessionRegistryImpl sessionRegistryImpl;

	public CustomLogoutHandler(MachineSessionRegistryImpl sessionRegistryImpl) {
		this.sessionRegistryImpl = sessionRegistryImpl;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		if (authentication != null) {
			User principal = (User) authentication.getPrincipal();
			this.sessionRegistryImpl.forceLogout(principal);
		}
	}

}
