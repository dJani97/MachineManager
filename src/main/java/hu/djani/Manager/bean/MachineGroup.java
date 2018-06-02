package hu.djani.Manager.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_machine_group")
@Data
@EqualsAndHashCode(of = "id")
public class MachineGroup {

	public MachineGroup() {
		this.creationDate = new Date();
	}

	public MachineGroup(@NotNull @Size(min = 2, max = 200) String name, Project project) {
		this.name = name;
		this.project = project;
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

	@OneToMany(mappedBy = "group")
	@JsonManagedReference
	private List<Machine> machines;

	@ManyToOne
	@JsonBackReference
	private Project project;

}
