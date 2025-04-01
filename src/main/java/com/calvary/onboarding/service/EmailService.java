package com.calvary.onboarding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public String sendWelcomeEmail(String to, String firstName, String lastName, String password) {
		String subject = "Welcome to Nehemiah Global Partners!";
		String body = "<!DOCTYPE html>" + "<html>" + "<head>" + "<style>"
				+ "body { font-family: DM_Sans, sans-serif; background-color: #c71e4c; padding: 20px; }"
				+ ".container { background-color: #ffffff; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); }"
				+ ".header { background-color: #4CAF50; color: #ffffff; text-align: center; padding: 15px; font-size: 22px; font-weight: bold; border-radius: 8px 8px 0 0; }"
				+ ".content { padding: 20px; text-align: center; font-size: 16px; color: #333333; }"
				+ ".footer { text-align: center; padding: 10px; font-size: 14px; color: #777777; }"
				+ ".password-box { background-color: #f8f9fa; padding: 10px; border-radius: 5px; font-size: 18px; font-weight: bold; display: inline-block; margin-top: 10px; }"
				+ "</style>" + "</head>" + "<body>" + "<div class='container'>"
				+ "<div class='header'>Welcome to Nehemiah Global Partners!</div>" + "<div class='content'>"
				+ "<p>Hello <strong>" + firstName + " " + lastName + "</strong>,</p>"
				+ "<p>We are thrilled to have you join our platform. Here are your login details:</p>"
				+ "<p class='password-box'>Your First-Time Generated Password: <strong>" + password + "</strong></p>"
				+ "<p>Please change your password after logging in for security purposes.</p>"
				+ "<p>Thank you for joining us!</p>" + "</div>"
				+ "<div class='footer'>© 2025 Nehemiah Global Partners. All rights reserved.</div>" + "</div>" + "</body>"
				+ "</html>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(sender);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true); // true indicates HTML content

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error sending email.";
		}

		return "Email sent successfully!";
	}
}
