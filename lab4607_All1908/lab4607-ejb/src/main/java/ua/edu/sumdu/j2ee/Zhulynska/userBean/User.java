package ua.edu.sumdu.j2ee.Zhulynska.userBean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable {

	public User() {
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(name="id_generator", sequenceName="seq_1", allocationSize=1)
	@GeneratedValue(generator="id_generator", strategy=GenerationType.SEQUENCE)
	
	@Column(name="id") 
	private Integer id;
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="NAME", length=100)
	private String name;
	
	@Column(name="SURNAME", length=100)
	private String surname;
	
	@Column(name="LOGIN", length=100)
	private String login;
	
	@Column(name="PASSWORD", length=100)
	private String password;

	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
