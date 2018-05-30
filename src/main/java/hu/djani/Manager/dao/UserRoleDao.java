package hu.djani.Manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.djani.Manager.bean.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Long> {

	public UserRole findByAuthority(String authority);

}
