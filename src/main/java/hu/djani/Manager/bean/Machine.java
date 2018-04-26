package hu.djani.Manager.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_machine")
public class Machine {

	public Machine() {
	}

	public Machine(String name, String description, String address, Date creationDate) {
		this.name = name;
		this.description = description;
		this.address = address;
		this.creationDate = creationDate;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
	@SequenceGenerator(name = "SEQ_GEN", sequenceName = "s_book", allocationSize = 1, initialValue = 1)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "address")
	private String address;

	@Column(name = "creation_date", columnDefinition = "TIMESTAMP")
	private Date creationDate;

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
	//		boolean reachable = false;
	//
	//		try {
	//			reachable = InetAddress.getByName(this.getAddress()).isReachable(10);
	//		} catch (UnknownHostException e) {
	//			e.printStackTrace();
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//		//			Process p1;
	//		//			try {
	//		//				p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 -w 50 " + this.getIp());
	//		//				int returnVal = p1.waitFor();
	//		//				System.out.println(returnVal);
	//		//				reachable = (returnVal==0);
	//		//			} catch (Exception e) {
	//		//				e.printStackTrace();
	//		//			}
	//
	//		return reachable;
	//
	//
	//	}
}
