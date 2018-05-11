package hu.djani.Manager.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "t_project")
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_MP")
	@SequenceGenerator(name = "SEQ_GEN_MP", sequenceName = "s_project", allocationSize = 1, initialValue = 1)
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

	@OneToMany(mappedBy = "project")
	private List<MachineGroup> groups;

	/*
	 * GET-SET
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<MachineGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<MachineGroup> groups) {
		this.groups = groups;
	}

}
