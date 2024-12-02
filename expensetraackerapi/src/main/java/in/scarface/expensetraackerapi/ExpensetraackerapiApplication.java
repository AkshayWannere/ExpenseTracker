package in.scarface.expensetraackerapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.scarface.expensetraackerapi.Entities.Expense;
import in.scarface.expensetraackerapi.Services.ExpenseService;
import in.scarface.expensetraackerapi.Utils.EmailUtils;

@SpringBootApplication
@RestController
@EnableWebSecurity
@ComponentScan("in.scarface.expensetraackerapi")
public class ExpensetraackerapiApplication {
	
	
	 @Autowired
	    private EmailUtils emailUtils;

	    @Autowired
	    private JavaMailSender mailSender;

	public static void main(String[] args) {
		SpringApplication.run(ExpensetraackerapiApplication.class, args);
	}
	
	@Autowired
	private ExpenseService expenseService;
	
    public void setMailSenderToEmailUtils() {
        emailUtils.setMailSender(mailSender);
    }

//	@GetMapping("/expenses1")
//	public String getALlExpenses1(){
//		
//		return "expenseService.listofExpenses()";
//
//	}
}
