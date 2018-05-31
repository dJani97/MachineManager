package hu.djani.Manager.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.event.OnRegistrationCompleteEvent;
import hu.djani.Manager.service.NotificationService;
import hu.djani.Manager.service.entity.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	@Autowired
	private UserService service;

	@Autowired
	NotificationService emailService;

	// API

	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final User user = event.getUser();
		final String token = UUID.randomUUID().toString();
		this.service.createVerificationTokenForUser(user, token);

		this.emailService.sendAccountVerification(user, event.getAppUrl(), token);
	}

}