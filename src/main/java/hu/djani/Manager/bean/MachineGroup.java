package hu.djani.Manager.bean;

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

@Entity
@Table(name = "t_machine_group")
public class MachineGroup {

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
}
