package hu.djani.Manager.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "t_role")
@Data()
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
public class UserRole implements GrantedAuthority {
	private static final long serialVersionUID = 1633397470969249426L;

	@Id
	private Long id;

	@Column(name = "authority", length = 100, nullable = false)
	private String authority;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	// @Override
	// public String getAuthority() {
	// return this.authority;
	// }
	//
	// public Set<User> getUsers() {
	// return Collections.unmodifiableSet(this.users);
	// }
	//
	// public Long getId() {
	// return this.id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }
	//
	// public void setAuthority(String authority) {
	// this.authority = authority;
	// }
}
