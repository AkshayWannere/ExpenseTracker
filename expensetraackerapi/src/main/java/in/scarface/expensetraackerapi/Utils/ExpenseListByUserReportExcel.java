package in.scarface.expensetraackerapi.Utils;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.scarface.expensetraackerapi.Entities.Expense;
import in.scarface.expensetraackerapi.Repository.ExpenseRepo;
import in.scarface.expensetraackerapi.Services.ExpenseServiceIMPL;
import in.scarface.expensetraackerapi.Services.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class ExpenseListByUserReportExcel {

	
	@Autowired
	private ExpenseRepo expenseRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ExpenseServiceIMPL expenseServiceImpl;
	

	public void generate(HttpServletResponse response,Long id,List<Expense> bxpenseById) throws Exception {

		//List<Expense> bxpenseById = expenseServiceImpl.expenseListByUser(id);
		
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("ExpenseLitByUser");
		HSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("id");
		row.createCell(1).setCellValue("name");
		row.createCell(2).setCellValue("description");
		row.createCell(3).setCellValue("amount");
		row.createCell(4).setCellValue("category");
		row.createCell(4).setCellValue("date");
		
		int datarowIndex=1;
		
		for(Expense singleExpense  :bxpenseById) {
			HSSFRow row2 = sheet.createRow(datarowIndex);
			
			row2.createCell(0).setCellValue(singleExpense.getId());
			row2.createCell(1).setCellValue(singleExpense.getName());
			row2.createCell(2).setCellValue(singleExpense.getDescription());
		//	row2.createCell(3).setCellValue(singleExpense.getAmount());
			row2.createCell(3).setCellValue(singleExpense.getAmount().doubleValue());
			row2.createCell(4).setCellValue(singleExpense.getCategory());
			row2.createCell(5).setCellValue(singleExpense.getDate());
		
			datarowIndex++;
		}

		ServletOutputStream outputStream = response.getOutputStream();

		
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();

	}
	
	
}
