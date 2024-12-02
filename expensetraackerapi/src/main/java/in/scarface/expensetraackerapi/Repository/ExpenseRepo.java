package in.scarface.expensetraackerapi.Repository;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.scarface.expensetraackerapi.Entities.Expense;
import java.util.List;
import java.util.Optional;
import java.util.Date;


//import in.scarface.expensetraackerapi.entity.Expense;

@Repository
public interface ExpenseRepo  extends JpaRepository<Expense, Long> {

	//For Filtering Records based on Category
	//We use find By query with Caetgory
	//Select * from TBL_Expenses where ctaegory=?
	Page<Expense> findByCategory(Pageable page, String category);

	//Slect * from tbl_expenses where name LIKE '%keyword%'
	Page<Expense> findByNameContaining(String keyword,Pageable page);

	public List<Expense> findByDateBetween(Date endDate,Date startDate);
	
	//Created Query to get the details according to Login users
	public Page<Expense> findByUserId(Long userId,Pageable page);
	
	
	
	//Query : select * from tbl_expenses where user_id= ? and id =?
	Optional<Expense> findByUserIdAndId(Long userId,Long expenseId);
	
	//JPA to execute the Queries for the Method and create Category wise etc
	
	
	//Select * from tbl_expenses where user_id=? and category=?
	Page<Expense>  findByUserIdAndCategory(Long userId,String category,Pageable page);
	
	//
	Page<Expense> findByUserIdAndNameContaining(long userId,String keyword,Pageable page);
	
	
	Page<Expense>  findByUserIdAndDateBetween(Long userId,Date staDate , Date endDate ,Pageable page);
	
	
	//Created Query to get the details according to Login users
		public List<Expense> findByUserId(Long userId);
	
	
	
}