package in.scarface.expensetraackerapi.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.scarface.expensetraackerapi.Entities.UserEnitity;
import in.scarface.expensetraackerapi.Entities.UserModel;
import in.scarface.expensetraackerapi.ExceptionHandling.ResourceNotFoundException;
import in.scarface.expensetraackerapi.ExceptionHandling.UserAlreadyExists;
import in.scarface.expensetraackerapi.Repository.UserRepo;
import in.scarface.expensetraackerapi.Security.CustomUserDetailsClass;
import in.scarface.expensetraackerapi.Utils.EmailUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServicesImpl implements UserService {

	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEnocder;
	
	@Autowired
	private EmailUtils emailUtils;
	
	
	@Override
	public List<UserModel> createUser(UserModel user) {
		
		List<UserModel> userDeatials = new ArrayList<>();
		
		if(user.getPassword().equals(user.getConfirmpassword()))
		{
			UserEnitity entity = new UserEnitity();
			BeanUtils.copyProperties(user, entity);
			entity.setPassword(bcryptEnocder.encode(entity.getPassword()));

			try {
				userRepo.save(entity);
		
				//Convert UserEntity details to UserModel and add to userDetails list
		        UserModel userModel = new UserModel();
		        BeanUtils.copyProperties(entity, userModel);
		        userDeatials.add(userModel);
			}catch (DataIntegrityViolationException e) {
		        throw new UserAlreadyExists("User Existed ");
		    } catch (Exception e) {
		        // Handle other unexpected exceptions
		        throw new RuntimeException("Failed to create user due to an unexpected error.");
		    }

			return userDeatials ;
		}else
		{
	        throw new RuntimeException(" Password Not matching");
		}
		//return null;
	}

	@Override
	public List<UserEnitity> getallUsers() {
		return userRepo.findAll();
	}


//	@Override
//	public UserEnitity getSingleUser(Long id) {	
//	return	userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found " + id));
//	}
//
//
//	@Override
//	public UserEnitity updateDetails(Long id ,UserModel user) {
//
//	  UserEnitity existingDeatils = getSingleUser(id);
//	            
//		
//		existingDeatils.setName(user.getName()!=null ? user.getName() : existingDeatils.getName());
//		existingDeatils.setEmail(user.getEmail()!=null ? user.getEmail() : existingDeatils.getEmail());
//		existingDeatils.setPassword(user.getPassword()!=null ? bcryptEnocder.encode(user.getPassword()) : existingDeatils.getPassword());
//		//existingDeatils.setAge(user.getAge()!=null ? user.getAge() : existingDeatils.getAge());
//		existingDeatils.setAge(user.getAge()!=null ? user.getAge() : existingDeatils.getAge());
//		
//		return userRepo.save(existingDeatils);
//	}
//	@Override
//	public String deleteUser(Long id) {
//				
//	UserEnitity singleUser = getSingleUser(id);
//			userRepo.deleteById(id);
//	
//		return "User deleted";
//	}

	@Override
	public UserEnitity getLoggedInUser() {

		Authentication authenticationObj = SecurityContextHolder.getContext().getAuthentication();
		 if (authenticationObj == null || !authenticationObj.isAuthenticated()) {
		        throw new UsernameNotFoundException("User is not authenticated.");
		    }
		
		String email = authenticationObj.getName();
		
		return userRepo.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("User not found for email " + email));

	}

	@Override
	public UserEnitity getSingleUser() {

		Long id = getLoggedInUser().getId();
		return	userRepo.findById(id).orElseThrow( () -> new ResourceNotFoundException("User not found for id " + id));
	
	}

	@Override
	public UserEnitity updateDetails(UserModel user) {

		UserEnitity existingDeatils = getSingleUser();
		existingDeatils.setName(user.getName()!=null ? user.getName() : existingDeatils.getName());
		existingDeatils.setEmail(user.getEmail()!=null ? user.getEmail() : existingDeatils.getEmail());
		existingDeatils.setPassword(user.getPassword()!=null ? bcryptEnocder.encode(user.getPassword()) : existingDeatils.getPassword());
		//existingDeatils.setAge(user.getAge()!=null ? user.getAge() : existingDeatils.getAge());
		existingDeatils.setAge(user.getAge()!=null ? user.getAge() : existingDeatils.getAge());
		
		return userRepo.save(existingDeatils);
	}

	@Override
	public String deleteUser() {
		UserEnitity singleUser = getSingleUser();
		userRepo.delete(singleUser);		
		
		return "User Deleted";
	}

	@Override
	public void reportRdf(HttpServletResponse response, Long id) throws Exception {
		List<UserEnitity> allUserList = userRepo.findAll();

		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("UserList");
		//HSSFRow row = sheet.createRow(0);
		HSSFRow row = sheet.createRow(0);
		
		row.createCell(0).setCellValue("id");
		row.createCell(1).setCellValue("age");
		row.createCell(2).setCellValue("email");
		row.createCell(3).setCellValue("name");
		
		int datarowIndex=1;
		
		for( UserEnitity singleUser:allUserList) {
			
			HSSFRow row2 = sheet.createRow(datarowIndex);

			row2.createCell(0).setCellValue(singleUser.getId());
			row2.createCell(1).setCellValue(singleUser.getAge());
			row2.createCell(2).setCellValue(singleUser.getEmail());
			row2.createCell(3).setCellValue(singleUser.getName());
				
			datarowIndex++;
		}

		ServletOutputStream outputStream = response.getOutputStream();
	
		workBook.write(outputStream);
		workBook.close();
		outputStream.close();
		
//		
//		String subject ="User deatils List";
//		String body ="<hi>This is the User deatisl list ";
//		String to ="ak15.wannare@gmail.com";
//	
//		emailUtils.sendEmail(subject, body, to);
	}

	
}
