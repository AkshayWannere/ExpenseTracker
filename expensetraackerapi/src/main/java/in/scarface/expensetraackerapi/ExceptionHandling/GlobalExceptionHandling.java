package in.scarface.expensetraackerapi.ExceptionHandling;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling  extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex,WebRequest request)
	{
		ErrorObject errorObj= new ErrorObject();
		errorObj.setMessage(ex.getMessage());
		errorObj.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorObj.setTimestamp(new Date());
	
		return new ResponseEntity<>(errorObj,HttpStatus.NOT_FOUND);
		
	}
	
//
//URL :{{url}}/expenses/1
//User Putting : {{url}}/expenses/1we
//
//SO got this Exception As Tester adding String Value in Integer
//	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex ,WebRequest request)
	{
		ErrorObject obj = new ErrorObject();
		obj.setMessage(ex.getMessage());
		obj.setStatusCode(HttpStatus.BAD_REQUEST.value());
		obj.setTimestamp(new Date());
		
		return  new ResponseEntity<>(obj,HttpStatus.BAD_REQUEST);
	}
	
	
	///How to Handle Genralized Exception
	//Like StackOverflow,Memeory issue etc
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> unexpectedException(Exception ex,WebRequest request){
		
		ErrorObject obj = new ErrorObject();
		obj.setMessage(ex.getMessage());
		obj.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		obj.setTimestamp(new Date());
		
		return new ResponseEntity<>(obj,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String,Object> body = new HashMap<>();
		body.put("timestamp",new Date());
		body.put("statuscode", HttpStatus.BAD_REQUEST.value());
		
		List<String> errosList = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map( i -> i.getDefaultMessage())
			.collect(Collectors.toList());
		
		body.put("Messages", errosList);

		
		return new ResponseEntity<Object>(body,HttpStatus.BAD_REQUEST);
	}
	//Now Getting this response after this 
	//{
//    "statuscode": "BAD_REQUEST",
//    "Messages": [
//        "Expense name mustv be at least3 charcater"
//    ],
//    "timestamp": "2024-05-08T07:10:50.166+00:00"
	
	
	@ExceptionHandler(UserAlreadyExists.class)
	public ResponseEntity<ErrorObject> UserAlreadyExists(UserAlreadyExists ex ,WebRequest request)
	{
		
		ErrorObject obj = new ErrorObject();
		obj.setMessage(ex.getMessage());
		obj.setStatusCode(HttpStatus.CONFLICT.value());
		obj.setTimestamp(new Date());		
		
		return new ResponseEntity<ErrorObject>(obj,HttpStatus.CONFLICT);		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	

