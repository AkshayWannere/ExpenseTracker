package in.scarface.expensetraackerapi.Entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UserModel {

	//As we need to use json ignore in the Password so
	//It will not come in ui retrun type
	//But it will not come in DB also
	//Thats why we dont use enity direclty here 
	//We use DataObject and Then copyied Data feild into enitty
	//and then save
	
	//
	@NotBlank(message =" Name should be not be empty")
	private String name;
	
	@NotNull(message="Email should not be empty")
	@Email(message ="Enter valid email")
	private String email;
	
	@NotNull(message="PassWord should not be empty")
	@Size(min = 5, message ="Password should be atleast 5 Charcs")
	private String password;
	
	@NotNull(message="Age should not be empty")
	private Long age=0L;
	
	@NotNull(message="PassWord should not be empty")
	@Size(min = 5, message ="Password should be atleast 5 Charcs")
	private String confirmpassword;
	

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
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

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	
}
