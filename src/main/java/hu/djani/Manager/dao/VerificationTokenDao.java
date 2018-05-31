package hu.djani.Manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.djani.Manager.bean.User;
import hu.djani.Manager.bean.VerificationToken;

public interface VerificationTokenDao extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(User user);

}
