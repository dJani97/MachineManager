package hu.djani.Manager.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import hu.djani.Manager.bean.User;

public class CustomLogoutHandler implements LogoutHandler {

	MachineSessionRegistryImpl sessionRegistryImpl;

	public CustomLogoutHandler(MachineSessionRegistryImpl sessionRegistryImpl) {
		this.sessionRegistryImpl = sessionRegistryImpl;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		User principal = (User) authentication.getPrincipal();
		List<SessionInformation> allSessions = this.sessionRegistryImpl.getAllSessions(principal, true);

		for (SessionInformation sessionInformation : allSessions) {
			sessionInformation.expireNow();
			this.sessionRegistryImpl.removeSessionInformation(sessionInformation.getSessionId());
		}
	}

}
