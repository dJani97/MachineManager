package hu.djani.Manager.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.djani.Manager.bean.User;

public interface UserDao extends JpaRepository<User, Long> {

	public Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);
}
