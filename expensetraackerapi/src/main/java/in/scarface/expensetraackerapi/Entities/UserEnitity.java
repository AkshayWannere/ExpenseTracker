package in.scarface.expensetraackerapi.Entities;

import java.security.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name ="tbl_userprofile")
public class UserEnitity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	
	
	private String name;
	
	@Column(unique =true)
	//Will throw exception if duplicate email comes
	private String email;

	@JsonIgnore
	private String password;
	
	private Long age;

//	@Column(name ="created_at",nullable =false,updatable = false)
//	@CreationTimestamp
//	private Timestamp created_at;
//	
//	@Column(name ="created_at")
//	@UpdateTimestamp
//	private Timestamp updated_at;

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public void existingDeatils(Long age) {
		this.age = age;
	}


}
