package in.scarface.expensetraackerapi.Entities;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name ="tbl_expenses")
public class Expense {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="expense_name")
	//Use not blank as Not null will allow --- like this alos
	@NotBlank(message = "Expenses name must not be null")
	@Size(min =3 ,message ="Expense name mustv be at least3 charcater")
	private String name;
	
	//private String Name;
	private String description;
	
	@Column(name="expense_amount")
	@NotNull(message = "Please provide Expense Amount should not be null")
	private BigDecimal amount;
	
	@NotBlank(message = "Please provide category")
	private String category;
	
	@NotNull(message = "Please provide Date Value")
	private Date date;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private UserEnitity user;
	
	
	
//	@Column(name="Created_at",updatable = false)
//	@CreationTimestamp
//	private Timestamp creadtedAt;
//	
//	@Column(name="updated_at")
//	@UpdateTimestamp
//	private Timestamp updatedAt;
//	
//	public Timestamp getCreadtedAt() {
//		return creadtedAt;
//	}
//
//	public void setCreadtedAt(Timestamp creadtedAt) {
//		this.creadtedAt = creadtedAt;
//	}
//
//	public Timestamp getUpdatedAt() {
//		return updatedAt;
//	}
//
//	public void setUpdatedAt(Timestamp updatedAt) {
//		this.updatedAt = updatedAt;
//	}

	public UserEnitity getUser() {
		return user;
	}

	public void setUser(UserEnitity user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", name=" + name + ", description=" + description + ", amount="
				+ amount + ", category=" + category + ", date=" + date + "]";
	}
	
	

//	public String getName() {
//		return Name;
//	}
//
//	public void setName(String name) {
//		Name = name;
//	}

	
	
	
	
}
