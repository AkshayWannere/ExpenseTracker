package in.scarface.expensetraackerapi.Entities;

public class JwtResponse {

	
	private  String jwtToken;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public JwtResponse() {
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
	
	
	 
	
}
