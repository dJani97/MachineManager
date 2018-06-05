package hu.djani.Manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.control.AuthController;

@Service
public class NotificationService {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${application.port}")
	Integer appPort;

	@Value("${server.port}")
	Integer redirectPort;

	@Value("${spring.mail.username}")
	private String senderEmail;

	public void sendAccountVerification(User user, String url, String token) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(this.senderEmail);
		mail.setSubject("Email cím megerősítése");
		mail.setText(
				"Kedves " + user.getFirstname() + "!\n\nKérlek kattints ide az email címed megerősítéséhez: http://"
						+ url.replace(this.redirectPort.toString(), this.appPort.toString()) + "/confirmAccount?token="
						+ token);

		this.javaMailSender.send(mail);
		this.logger.debug("Email sent to: " + user);
	}

}
