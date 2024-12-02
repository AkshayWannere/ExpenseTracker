package in.scarface.expensetraackerapi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import in.scarface.expensetraackerapi.Entities.UserEnitity;
import in.scarface.expensetraackerapi.Entities.UserModel;
import in.scarface.expensetraackerapi.Repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	//Now i have use Sprinf contecxt In getloggned user user
	//so will not take data from the Url
	//
	
	public List<UserModel> createUser(UserModel user);
	
	//For amdin
	public List<UserEnitity> getallUsers( );
	
	//public UserEnitity getSingleUser(Long id);
	public UserEnitity getSingleUser();
	
	//public UserEnitity updateDetails(Long id,UserModel user);
	public UserEnitity updateDetails(UserModel user);
	
	//public String deleteUser(Long id);
	public String deleteUser();

	public UserEnitity getLoggedInUser();
	
	public void reportRdf(HttpServletResponse response, Long id) throws Exception;
	
}
