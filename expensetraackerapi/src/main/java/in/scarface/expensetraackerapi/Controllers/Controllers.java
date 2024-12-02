package in.scarface.expensetraackerapi.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.scarface.expensetraackerapi.Entities.Expense;
import in.scarface.expensetraackerapi.Services.ExpenseService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class Controllers {

	@Autowired
		private ExpenseService expenseService;

		@GetMapping("/expenses")
		public   Page<Expense>  getALlExpenses(Pageable page ){
		//	System.out.println("From the Service layer");
			//return ;
			return expenseService.listofExpenses(page);
		}
		
		@GetMapping("/expenses/{id}")
		public Expense getExpenseById(@PathVariable Long id ){
			return  expenseService.getBxpenseById(id);
		}
		
		
		//{{url}}/expenses/45    Write like this in Postman
		
		//If method parmater and par,mter in URL then we dont need to 
		//SPecfity this @RequestParam("id")
		//@PathVariable("id") Long id Just use path variable
		
		
		//@ResponseStatus(value=HttpStatus.NO_CONTENT)
		@DeleteMapping("/expenses/{id}")
		public ResponseEntity<String> deleteExpenseById(@PathVariable  Long id )
		{
			expenseService.deleteExpenseById(id);
			return new ResponseEntity<String>("Expesne Deleted",HttpStatus.NO_CONTENT);
		}
		//{{url}}/expenses?id=34 Wriyte  like this in Postman
		
		
	//	@ResponseStatus(value=HttpStatus.CREATED)
		
//		@PostMapping("/expenses")
//		public Expense saveExpenseDeatils(@RequestBody  Expense expense) {
//			return expenseService.saveExpenseDetailsone(expense);
//		}
		
		
		@PutMapping("/expenses/{id}")
		public Expense updateExpenseDetails(@PathVariable  Long id , @RequestBody Expense expense) {
			
			Expense updatedExpenseDeatils = expenseService.updateExpenseDeatils(id, expense);
			return updatedExpenseDeatils;	
		}
		
		@PostMapping("/expensesone")
		public Expense saveExpenseDeatilsone(@Valid  @RequestBody  Expense expense) {
			return expenseService.saveExpenseDetailsone(expense);
		}
		
		
		@GetMapping("/expenses/category")
		public List<Expense> readByExpense(@RequestParam String category,Pageable page){
			return expenseService.readByCategory(category, page);
		}
		
		@GetMapping("/expenses/name")
		public List<Expense> readByName(@RequestParam String name,Pageable page){
			return expenseService.readByName(name, page);
		}	
		
//		
//		@GetMapping("/expenses/date")
//		public List<Expense> findByDateBetween(@RequestParam Date startDate,@RequestParam(required = false)  Date endDate){
//			return expenseService.findByDateBetween(startDate, endDate);
//		}
		
		@GetMapping("/expenses/date")
		public List<Expense> findByDateBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
		                                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
		                                       , Pageable page) {
		return expenseService.findByDateBetween(startDate, endDate, page);
		}

		@GetMapping("/exportExpenses/{id}")
		public void exportExpenses(HttpServletResponse response,@PathVariable Long id) throws Exception{
	
			response.setContentType("application/octet-stream");

	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=ExpenseListOfuser.xls";
	        response.setHeader(headerKey, headerValue);
			
			expenseService.exportExpenses(id, response);
			
		}
}


