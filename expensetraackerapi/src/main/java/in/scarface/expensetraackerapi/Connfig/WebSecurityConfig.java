package in.scarface.expensetraackerapi.Connfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.scarface.expensetraackerapi.Security.CustomUserDetailsClass;
import in.scarface.expensetraackerapi.Security.JwtRequestFilter;
import in.scarface.expensetraackerapi.Utils.JwtTokenUtils;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	//Sop whenever we add the Security dependency 
	//We get eeoors like it seems you’re encountering an issue with your Spring Boot application where the @PostMapping and @DeleteMapping annotations are not working as expected after adding Spring Security. Let’s address this!
	//When you add Spring Security to your application, it enables Cross-Site Request Forgery (CSRF) protection by default. This means that requests like POST, PUT, and DELETE require a CSRF token to be included. If the token is missing or invalid, you’ll encounter a 403 Forbidden error.
	//Here are a couple of options to resolve this issue:
	//Enable or Diable CSRF so here I Disable it 
		//	http.csrf().disable()
		
	//2 methpd to configure partof webadpater b 
//	confiure(HttpSecurity http) -> helps use to customize methods Http request method
//	configure(Authmeicationmanager auth) ->helps  us for the login related
//	
	//Currenlty the class websecurityAdpater is Been depeacted after 3version
	//So java People felt instead of extsinf this clas and overriding its method
	//They ask us too use security filter chain and create Cutomization  the Http Requests
	
	//And ALso the Ant matchers has Been Depecated So use Request MNatchers


	
	@Autowired
	public CustomUserDetailsClass customuserdetails;
	
	 @Autowired
	    private JwtRequestFilter authenticationJwtTokenFilter;
	
	 @Autowired
	   private  JwtTokenUtils jwtTokenUtil;
	   // private final CustomUserDetailsClass userDetailsService;
	 
//	
//	public JwtRequestFilter authenticationJwtTokenFilter() {
//		return new JwtRequestFilter();
//	}

	
	
	//@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{

		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/createUsers").permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtRequestFilter(jwtTokenUtil, customuserdetails), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.build();
        }

	
//	 @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	        auth.userDetailsService(		)
//	           .passwordEncoder(passwordEncoder());
//	    }
	 
	 @Bean
	    public CustomUserDetailsClass customUserDetailsService() {
	        return new CustomUserDetailsClass(); // Instantiate and return your CustomUserDetailsClass
	    }
	 
	 @Bean
	 public    static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		 
	 }
	 
	 
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	 
	 
	 @Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			
			authProvider.setUserDetailsService(customUserDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());
			
			return authProvider;
		}
		
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//#######	Check this ApproachOnece  #######	
		
//		http.csrf(csrf -> csrf.disable())
//			.authorizeHttpRequests( i -> i.requestMatchers("/login","/createUsers").permitAll()
//			.anyRequest().authenticated())
//			.httpBasic();
//		
//		return http.build();	

	
	
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	public UserDetailsService userDetailsService(){
//	
//		UserDetails admin =User.builder()
//							.username("admin")
//							.password(passwordEncoder().encode())
//							.roles("admin")
//							.build();
//		
//		UserDetails user =User.builder()
//				.username("Pranay")
//				.password("12344567")
//				.roles("User")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user,admin);
//	
//	}
//	
	
	
	
	
