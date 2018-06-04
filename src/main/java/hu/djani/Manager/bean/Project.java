package hu.djani.Manager.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "t_project")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = { "groups" })
public class Project {

	public Project() {
		this.creationDate = new Date();
	}

	public Project(@NotNull @Size(min = 2, max = 200) String name) {
		this.name = name;
		this.creationDate = new Date();
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@NotNull
	@Size(min = 2, max = 200)
	@Column(name = "name")
	private String name;

	@CreatedDate
	@Column(name = "creation_date", columnDefinition = "TIMESTAMP")
	private Date creationDate;

	/*
	 * relationships
	 */

	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private List<MachineGroup> groups;

}
