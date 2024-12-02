package in.scarface.expensetraackerapi.Security;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import in.scarface.expensetraackerapi.Entities.UserEnitity;
import in.scarface.expensetraackerapi.ExceptionHandling.ResourceNotFoundException;
import in.scarface.expensetraackerapi.Repository.UserRepo;

@Service
@Primary
public class CustomUserDetailsClass implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEnitity existingUser = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not present with email " + email));

		 // Encode the password retrieved from the database
    //    String encodedPassword = passwordEncoder.encode(existingUser.getPassword());

     // Return UserDetails with encoded password
//        return org.springframework.security.core.userdetails.User
//                .withUsername(existingUser.getEmail())
//                .password(existingUser.getPassword())
   //             .build();
        
		return new org.springframework.security.core.userdetails.
				User(existingUser.getEmail(), 
						existingUser.getPassword(), 
						new ArrayList<>());

        

	}

}
