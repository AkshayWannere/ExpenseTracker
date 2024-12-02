package in.scarface.expensetraackerapi.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	public JavaMailSender mailsender;
	
	public void setMailSender(JavaMailSender mailSender) { // No return type required
        this.mailsender = mailSender;
    }
	
	public boolean sendEmail(String subject,String body,String to){ {

	 try { 
		 MimeMessage mimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setTo(to);
			System.out.println("Trying to send email");
			
			Transport.send(mimeMessage);
			//mailsender.send(mimeMessage);
			System.out.println(" send email done");

	 }catch(Exception e){
			e.printStackTrace();
		}
	
	return true;	
	}

	}
}
	

