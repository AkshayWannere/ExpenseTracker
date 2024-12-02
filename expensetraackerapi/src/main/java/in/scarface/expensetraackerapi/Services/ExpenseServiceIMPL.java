package in.scarface.expensetraackerapi.Services;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.hibernate.query.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import in.scarface.expensetraackerapi.entity.Expense;
import in.scarface.expensetraackerapi.Entities.Expense;
import in.scarface.expensetraackerapi.ExceptionHandling.ResourceNotFoundException;
import in.scarface.expensetraackerapi.Repository.ExpenseRepo;
import in.scarface.expensetraackerapi.Utils.EmailUtils;
import in.scarface.expensetraackerapi.Utils.ExpenseListByUserReportExcel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseServiceIMPL  implements ExpenseService{

	@Autowired
	private ExpenseRepo expenseRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier
	private EmailUtils emailUtils;
	
	@Autowired
	private ExpenseListByUserReportExcel expenseListByUserReportExcel;
	
	@Override
	public Page<Expense> listofExpenses(Pageable page) {
	    return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	}


	@Override
	public Expense getBxpenseById(Long id) {

		//   Optional<Expense> byId = expenseRepo.findById(id);
		Optional<Expense> byUserIdAndId = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
		  if(byUserIdAndId.isPresent()) {
			  return byUserIdAndId.get();
		  }
		  throw new ResourceNotFoundException("Expesnse is not Found for ID"+ id);	  	  
	}

	@Override
	public void deleteExpenseById(Long id) {
		Expense bxpenseById = getBxpenseById(id);
		expenseRepo.delete(bxpenseById);
		//return bxpenseById;
	}

//	@Override
//	public Expense saveExpenseDetails(Expense expense) {
//		try{
//			//expense.setUser(userService.getLoggedInUser());
//			return expenseRepo.save(expense);
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		throw new ResourceNotFoundException("DOnt Know what happene here" );
//
//	}

	@Override
	public Expense updateExpenseDeatils(Long id,Expense expense) {
		Expense existingExpenseId = getBxpenseById(id);
		//Last Method call
		
		//What we doing is If User trying to edit that or nnot we checking that
		//If user ewants to chnage the Name then We will use Bining object
		//If user dont want to Chnage name i.e it will be null then we taking Exisitng name object getters
		
		existingExpenseId.setName(expense.getName()!= null ? expense.getName() : existingExpenseId.getName());		
		existingExpenseId.setAmount(expense.getAmount()!=null ? expense.getAmount() : existingExpenseId.getAmount());
		existingExpenseId.setDescription(expense.getDescription()!=null ? expense.getDescription(): existingExpenseId.getDescription());
		existingExpenseId.setDate(expense.getDate()!=null ? expense.getDate() : existingExpenseId.getDate());
		existingExpenseId.setCategory(expense.getCategory()!=null ? expense.getCategory() : existingExpenseId.getCategory());
			expenseRepo.save(existingExpenseId);
		return existingExpenseId ;
	}


	@Override
	public Expense saveExpenseDetailsone( Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepo.save(expense);
		
	}
	
	//Filtering records based on  category
	@Override
	public List<Expense> readByCategory(String category, Pageable page) {
		return  expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
	}

	//Filtering the Records Just By Typing one name ffrom nam
	@Override
	public List<Expense> readByName(String keyword, Pageable page) {
	
		// List<Expense> list = expenseRepo.findByNameContaining(name, page).toList();
		
//		 if(list.isEmpty()) {
//			 throw new ResourceNotFoundException("Details Not Found for Keyword " + name);
//		 }
//		return list;
		
		//=========Without User Login==========
		
		Page<Expense> byUserIdAndNameContaining = expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page);
		
		List<Expense> aslist = byUserIdAndNameContaining.toList();
		
		return aslist;	 
	}

	@Override
	public List<Expense> findByDateBetween(Date startDate, Date endDate,Pageable page) {

		if(startDate==null){
			startDate= new Date();	
		}
		
		if(endDate==null){
			endDate= new Date(System.currentTimeMillis());	
		}
	 List<Expense>  recordsBetweenDate= expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
		
		if(recordsBetweenDate.isEmpty())
		{
		 throw new ResourceNotFoundException("No Record between Date " +startDate + " and " + endDate);	
		}
		return recordsBetweenDate;
	}

	@Override
	public List<Expense> expenseListByUser(Long id) {
		return expenseRepo.findByUserId(userService.getLoggedInUser().getId());
		//return byUserIdExpenses;
	}
	
	
	
	@Override
	public void exportExpenses(Long id,HttpServletResponse response) throws Exception {
		
	//	Pageable page = null;
	//	 Page<Expense> bxpenseById = expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	//Expense bxpenseById = expenseListByUser(id);
	
		List<Expense> bxpenseById = expenseListByUser(id);

		
		expenseListByUserReportExcel.generate(response, id, bxpenseById);
//		
//				String subject ="User deatils List";
//				String body ="<hi>This is the User deatisl list ";
//		        String to ="ak15.wannare@gmail.com";
//		
//		emailUtils.sendEmail(subject, body, to);
//			
	}


	

	
}
