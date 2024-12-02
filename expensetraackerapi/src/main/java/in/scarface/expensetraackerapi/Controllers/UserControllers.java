package in.scarface.expensetraackerapi.Controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.springframework.web.bind.annotation.RestController;

import in.scarface.expensetraackerapi.Entities.UserEnitity;
import in.scarface.expensetraackerapi.Entities.UserModel;
import in.scarface.expensetraackerapi.ExceptionHandling.ResourceNotFoundException;
import in.scarface.expensetraackerapi.Repository.UserRepo;
import in.scarface.expensetraackerapi.Services.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
public class UserControllers {

	@Autowired
	private UserService userService;
	
	@Autowired
	public UserRepo userRepo;
	

	//Only admin have Such funiclitinliayt No users
	
	@GetMapping("/getusers")
	public  ResponseEntity<List<UserEnitity>> getallUsers(){
		List<UserEnitity> getallUsers = userService.getallUsers();
	
	return new ResponseEntity<>(getallUsers,HttpStatus.OK);
	}
	
//	
//	@GetMapping("/getsingleuser")
//	public  ResponseEntity<UserEnitity> getSingleUser(@RequestParam  Long id){
//		 UserEnitity singleUser = userService.getSingleUser(id);
//	
//	return new ResponseEntity<>(singleUser,HttpStatus.OK);
//	}
//
//	@PutMapping("/updateuserdeatils")
//	public ResponseEntity<UserEnitity> updateDeatils(@RequestParam  Long id ,@RequestBody UserModel user){	
//	
//		UserEnitity updateDetails = userService.updateDetails(id, user);
//		return new ResponseEntity<>(updateDetails,HttpStatus.OK) ;
//		//return null;
//		
//	}
//	
//	
//	@DeleteMapping("/getsingleuser/{id}")
//	public ResponseEntity<String> deleteUser(@PathVariable  Long id )
//	{		 userService.deleteUser(id);
//		return new ResponseEntity<String>("User Details Deleted",HttpStatus.OK);
//		
//	}
	
	@GetMapping("/getsingleuser")
	public  ResponseEntity<UserEnitity> getSingleUser(){
	return new ResponseEntity<>(userService.getSingleUser(),HttpStatus.OK);
	}
	
	
	@PutMapping("/updateuserdeatils")
	public ResponseEntity<UserEnitity> updateDeatils(@RequestBody UserModel user){	
		return new ResponseEntity<>(userService.updateDetails(user),HttpStatus.OK) ;		
	}
	
	@DeleteMapping("/deactivate")
	public ResponseEntity<String> deleteUser()
	{		 userService.deleteUser();
		return new ResponseEntity<String>("User Details Deleted",HttpStatus.OK);
		
	}
	
	@GetMapping("/exportExcel/{id}")
	public void genrateReport(HttpServletResponse response,@PathVariable Long id) throws Exception{
		response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=UserList.xls";
        response.setHeader(headerKey, headerValue);
		
		userService.reportRdf(response,id);
		
	}
	//lvce dnyc tcpz azzm
	//lvce dnyc tcpz azzm
	
	
}
