package in.scarface.expensetraackerapi.Utils;

import java.util.Properties;

//import org.hibernate.cfg.Environment;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {

    @Autowired
    private Environment env;
    
    @Lazy
    @Autowired
    private JavaMailSender mailsender;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail.host", "smtp.gmail.com"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port", "587")));
        mailSender.setUsername(env.getProperty("spring.mail.username", "akshaywannare15@gmail.com"));
        mailSender.setPassword(env.getProperty("spring.mail.password", "lvcednyctcpzazzm"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", env.getProperty("spring.mail.properties.mail.smtp.auth", "true"));
        props.put("mail.smtp.starttls.enable", env.getProperty("spring.mail.properties.mail.smtp.starttls.enable", "true"));

        return mailSender;
    }

}
