
/**
 * @author Rohit Thorawade
 * @Purpose Created Jms so has to send mail to a particular emailID
 *
 */
package com.bridgelabz.fundoo.jwt.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Jms
{

	@Autowired
	JavaMailSender javamailsender;

	/**
	 * @param emailId -EmailId of the user
	 * @param token   -Token generated
	 * @Purpose -SendMail on the email of given user emailId
	 */
	public void sendMail(String emailId, String token) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("rohit.com");
		mail.setTo(emailId);
		mail.setText(token);
		mail.setSubject("Verification Token");

		javamailsender.send(mail);
	}

}
