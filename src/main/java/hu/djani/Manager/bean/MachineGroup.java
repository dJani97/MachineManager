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
@Table(name = "t_machine_group")
public class MachineGroup {

	public MachineGroup() {
		this.creationDate = new Date();
	}

	public MachineGroup(@NotNull @Size(min = 2, max = 200) String name) {
		this.name = name;
		this.creationDate = new Date();
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_MG")
	@SequenceGenerator(name = "SEQ_GEN_MG", sequenceName = "s_machine_group", allocationSize = 1, initialValue = 1)
	private Integer id;

	@NotNull
	@Size(min=2, max=200)
	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "group")
	private List<Machine> machines;

	@CreatedDate
	@Column(name = "creation_date", columnDefinition = "TIMESTAMP")
	private Date creationDate;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Machine> getMachines() {
		return this.machines;
	}

	public void setMachines(List<Machine> machines) {
		this.machines = machines;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
