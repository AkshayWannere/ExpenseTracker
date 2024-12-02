package in.scarface.expensetraackerapi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.scarface.expensetraackerapi.Entities.UserEnitity;

@Repository
public interface UserRepo extends JpaRepository<UserEnitity, Long> {

//TO handle existing users
	
	public Boolean existsByEmail(String email);
	
	
	Optional<UserEnitity> findByEmail(String email);
	
	
	
	

}
