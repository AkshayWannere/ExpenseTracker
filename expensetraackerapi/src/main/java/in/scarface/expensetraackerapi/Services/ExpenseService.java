package in.scarface.expensetraackerapi.Services;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.hibernate.query.Page;

import in.scarface.expensetraackerapi.Entities.Expense;
import jakarta.servlet.http.HttpServletResponse;

//import in.scarface.expensetraackerapi.entity.Expense;

public interface ExpenseService {

	//TO get  List of records
	 Page<Expense> listofExpenses (Pageable page);

	 //?TO Get records based on one Id
	 public Expense getBxpenseById(Long id);
	 
	 //TO delete the Deatils
	 public void deleteExpenseById(Long id);
	 
	 //TO save the Details 
	// public Expense saveExpenseDetails(Expense expense);

	//TO save the Details 
	 public Expense saveExpenseDetailsone(Expense expense);
		 
	 
	 //TO get the filter Category
	 
	 List<Expense> readByCategory(String Category,Pageable page);

	 //Update Expesne deatils Ins service
	 
	 public Expense updateExpenseDeatils(Long id,Expense expense);

	 //Get the data based on name we put 
	 public List<Expense> readByName(String name,Pageable page);

	 //to FIlter the Records between Start and End Date
	 public List<Expense> findByDateBetween(Date startDate, Date endDate,Pageable page);
	 
	 
	 //to Get the User specific expesnes
	 public void exportExpenses(Long id,HttpServletResponse response) throws Exception;
	 
	 //
	 public List<Expense> expenseListByUser(Long id);
	 
}
