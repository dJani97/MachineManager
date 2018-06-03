package hu.djani.Manager.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_role")
@Data()
@EqualsAndHashCode(of = "id")
public class UserRole implements GrantedAuthority {
	private static final long serialVersionUID = 1633397470969249426L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "authority", length = 100, nullable = false)
	private String authority;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	@Override
	public String toString() {
		if (this.authority.contains("_")) {
			int first = this.authority.indexOf("_");
			return this.authority.substring(first + 1);
		}
		return this.authority;
	}
}
