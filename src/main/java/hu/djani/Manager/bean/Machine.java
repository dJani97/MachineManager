package hu.djani.Manager.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "t_machine")
public class Machine {

	public Machine() {
		this.creationDate = new Date();
	}

	public Machine(String name, String description, String address, Date creationDate) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_M")
	@SequenceGenerator(name = "SEQ_GEN_M", sequenceName = "s_machine", allocationSize = 1, initialValue = 1)
	private Integer id;

	@NotNull
	@Size(min=2, max=200)
	@Column(name = "name")
	private String name;

	@NotNull
	@Size(min=2, max=5000)
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Size(min=7, max=200)
	@Column(name = "address")
	private String address;

	@CreatedDate
	@Column(name = "creation_date", columnDefinition = "TIMESTAMP")
	private Date creationDate;


	/*
	 * relationships
	 */

	@ManyToOne
	private MachineGroup group;


	@Override
	public String toString() {
		return "Machine [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", ip=" + this.address
				+ ", creationDate=" + this.creationDate + "]";
	}

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	//	public boolean getReachable() {
	//		return this.getPinger(null).isReachable(this.getAddress());
	//	}
	//
	//	@Autowired
	//	private PingComponent getPinger(PingComponent pinger) {
	//		return pinger;
	//	}
}
