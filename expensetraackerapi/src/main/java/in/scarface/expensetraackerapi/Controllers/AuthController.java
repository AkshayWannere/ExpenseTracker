package in.scarface.expensetraackerapi.Controllers;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.scarface.expensetraackerapi.Entities.JwtResponse;
import in.scarface.expensetraackerapi.Entities.LoginModel;
import in.scarface.expensetraackerapi.Entities.UserModel;
import in.scarface.expensetraackerapi.ExceptionHandling.ResourceNotFoundException;
import in.scarface.expensetraackerapi.Security.CustomUserDetailsClass;
import in.scarface.expensetraackerapi.Services.UserService;
import in.scarface.expensetraackerapi.Utils.JwtTokenUtils;
import jakarta.validation.Valid;

@RestController
public class AuthController {

//	@Autowired 
//	private UserService userService;
//	
//	@Autowired
//    @Qualifier("customUserDetailsClass") // Specify the exact bean name
//	private CustomUserDetailsClass userDeatilsService;
//	
//	
//	@Autowired
//	private AuthenticationManager authenicationManager;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	@Autowired
//	private JwtTokenUtils jwtUtil;
//	
//	@PostMapping("/login")
//	public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login){
//
//			try {
//				authenticate(login.getEmail(),login.getPasswordd());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			final UserDetails userDetails = userDeatilsService.loadUserByUsername(login.getEmail());
//			final String token =jwtUtil.generateToken(userDetails);
//	
//			//Sending token to client
//			 return new ResponseEntity<JwtResponse>(new JwtResponse(token) ,HttpStatus.OK);
//		}
//		
//private void authenticate(String email, String passwordd) throws Exception {
//
//	try {
//		authenicationManager.authenticate(new UsernamePasswordAuthenticationToken(email,passwordd));
//
//	} catch (DisabledException e) {
//		throw new Exception ("User disabled");
//	}catch (BadCredentialsException e) {
//		throw new Exception ("Bad  Credentials");
//	}
//	}


//	@GetMapping("/ilogin")
//	public ResponseEntity<String> ilogin(){
//		return new ResponseEntity<String>("User Login",HttpStatus.OK);
//	}
	
//	@GetMapping("/iilogin")
//	public ResponseEntity<String> iilogin(Authentication auth){
//		return new ResponseEntity<String>("Welocme " + auth.getName() + " Checking in meonory autheication",HttpStatus.OK);
//	}
	
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	@Qualifier("customUserDetailsClass")
	private CustomUserDetailsClass userDetailsService;
	


	@Autowired
	private JwtTokenUtils jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {
		
		authenticate(login.getEmail(), login.getPasswordd());
		
		//we need to generate the jwt token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}
	
	private void authenticate(String email, String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("User disabled");
		} catch (BadCredentialsException e) {
			throw new Exception("Bad Credentials");
		}
		
	}
	
	
	
	
	
	
	
	
	
	

	@PostMapping("/createUsers")
	public ResponseEntity<List<UserModel>> createUsers( @Valid  @RequestBody UserModel user )
	{
		List<UserModel> deatilssafterSavinginDb = userService.createUser(user);
		if(deatilssafterSavinginDb!=null) {
			return new ResponseEntity<>(deatilssafterSavinginDb, HttpStatus.CREATED);
		}else{
			throw new ResourceNotFoundException("Fill details ");
		}
	}
	
	
}



//@PostMapping("/login")
//public ResponseEntity<String> login(@RequestBody LoginModel login){
//
//	try {
//		Authentication authenticate = authenicationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPasswordd()));
//		SecurityContextHolder.getContext().setAuthentication(authenticate);
//		 return new ResponseEntity<>("User Login successful", HttpStatus.OK);
//	}catch (Exception e) {
//        return new ResponseEntity<>("Bad credentials", HttpStatus.UNAUTHORIZED);
//    }
//	
//
//}