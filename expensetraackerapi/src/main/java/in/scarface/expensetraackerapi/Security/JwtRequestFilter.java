package in.scarface.expensetraackerapi.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.scarface.expensetraackerapi.Utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	/*
	@Autowired
	private JwtTokenUtils jwtTokenUtil;
//	
	@Autowired
	@Qualifier("customUserDetailsClass")
	private CustomUserDetailsClass userDetailsService;
//	
//	
// //private JwtTokenUtils jwtTokenUtil;
//    
//    
	
    @Autowired
    @PostConstruct
    public void init() {
        this.jwtTokenUtil = new JwtTokenUtils();
   }
   */
	
//	private final JwtTokenUtils jwtTokenUtil;
//    private final CustomUserDetailsClass userDetailsService;
//
//    @Autowired
//    public JwtRequestFilter(JwtTokenUtils jwtTokenUtil, CustomUserDetailsClass userDetailsService) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.userDetailsService = userDetailsService;
//    }
	
    private final JwtTokenUtils jwtTokenUtil;
    private final CustomUserDetailsClass userDetailsService;

    @Autowired
    public JwtRequestFilter(JwtTokenUtils jwtTokenUtil,     CustomUserDetailsClass userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String jwtToken = null;
		String username = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unable to get JWT token");
			} catch (ExpiredJwtException e) {
				throw new RuntimeException("Jwt token has expired");
			}
			
		}
		
		//Once we get the token, validate the token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	

}
