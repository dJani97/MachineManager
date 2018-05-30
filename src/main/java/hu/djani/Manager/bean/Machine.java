package hu.djani.Manager.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "t_machine")
@Data
public class Machine {

	public Machine() {
		this.creationDate = new Date();
	}

	public Machine(String name, String description, String address, Date creationDate, MachineGroup group) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.creationDate = creationDate;
		this.group = group;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;

	@NotNull
	@Size(min = 2, max = 200)
	@Column(name = "name")
	private String name;

	@NotNull
	@Size(min = 2, max = 5000)
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Size(min = 7, max = 200)
	@Column(name = "address")
	private String address;

	@CreatedDate
	@Column(name = "creation_date", columnDefinition = "TIMESTAMP")
	private Date creationDate;

	/*
	 * relationships
	 */

	@ManyToOne
	@JsonBackReference
	private MachineGroup group;

	@Override
	public String toString() {
		return "Machine [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", ip="
				+ this.address + ", creationDate=" + this.creationDate + "]";
	}

}
