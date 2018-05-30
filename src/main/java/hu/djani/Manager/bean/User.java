package hu.djani.Manager.bean;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_user")
@Data
// @ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
public class User implements UserDetails {
	private static final long serialVersionUID = -1103629629429559376L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;

	@Column(name = "username", length = 100, nullable = false)
	private String username;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Column(name = "non_expired", nullable = false)
	private boolean accountNonExpired;

	@Column(name = "non_locked", nullable = false)
	private boolean accountNonLocked;

	@Column(name = "credentials_non_expired", nullable = false)
	private boolean credentialsNonExpired;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "firstname", length = 100, nullable = false)
	@Size(min = 2, max = 100)
	private String firstname;

	@Column(name = "lastname", length = 100, nullable = false)
	@Size(min = 2, max = 100)
	private String lastname;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "t_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<UserRole> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableCollection(this.roles);
	}

	public void addRole(UserRole role) {
		this.roles.add(role);
	}

	public boolean hasRole(UserRole role) {
		return this.roles.contains(role);
	}

	public String getFullname() {
		return this.lastname + " " + this.firstname;
	}
}
