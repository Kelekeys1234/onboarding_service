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

	public String sendWelcomeEmail(String to, String firstName, String lastName, String password, String userId) {
		String subject = "Welcome to Nehemiah Global Partners!";
		String body = "@Kelly  <html lang='en'>\r\n" + "  <head>\r\n" + "    <meta charset='UTF-8' />\r\n"
				+ "    <meta name='viewport' content='width=device-width, initial-scale=1.0' />\r\n"
				+ "    <title>MNGP Email template</title>\r\n" + "  </head>\r\n" + "  <body\r\n"
				+ "    style='font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;'\r\n"
				+ "  >\r\n" + "    <table\r\n" + "      width='100%'\r\n" + "      cellpadding='0'\r\n"
				+ "      cellspacing='0'\r\n" + "      style='background-color: #f4f4f4; padding: 20px;'\r\n"
				+ "    >\r\n" + "      <tr>\r\n" + "        <td align='center'>\r\n" + "          <table\r\n"
				+ "            width='600px'\r\n" + "            cellpadding='0'\r\n"
				+ "            cellspacing='0'\r\n"
				+ "            style='background-color: #ffffff; padding: 20px; border-radius: 8px;'\r\n"
				+ "          >\r\n" + "            <tr>\r\n"
				+ "              <td align='center' style='padding: 10px 0;'>\r\n"
				+ "                <h1 style='color: #c71e4c; font-size: 24px; margin: 0;'>Congratulations on your first level registration!</h1>\r\n"
				+ "              </td>\r\n" + "            </tr>\r\n" + "            <tr>\r\n"
				+ "              <td style='padding: 20px 0; color: #333333;'>\r\n"
				+ "                <p style='font-size: 16px; margin: 0;'>Dear\r\n" + firstName + " " + lastName
				+ "                  ,</p>\r\n" + "                <p style='font-size: 16px; margin: 0;'>\r\n"
				+ "                  We are happy to inform you that your first level registration with\r\n"
				+ "                  <strong>Mission - NGP</strong> was successful. 🎉\r\n" + "                </p>\r\n"
				+ "                <br/>\r\n" + "                <p style='font-size: 16px; margin: 0;'>\r\n"
				+ "                  <b>What is your next step?</b> \r\n" + "                  <br/>\r\n"
				+ "                  <br/>\r\n"
				+ "                  Your next step, is to quickly make the payment of your desired donation amount.\r\n"
				+ "                </p>\r\n" + "              </td>\r\n" + "            </tr>\r\n"
				+ "            <tr>\r\n" + "              <td style='padding: 10px 0; color: #333333;'>\r\n"
				+ "                <p style='font-size: 16px; margin: 0;'>\r\n"
				+ "                  This payment, qualifies you for a new membership experience with us.\r\n"
				+ "                </p>\r\n" + "                <p style='font-size: 16px; margin: 10px 0;'>\r\n"
				+ "                  Click on the link below to make payment.... it is that simple.\r\n"
				+ "                </p>\r\n" + "              </td>\r\n" + "            </tr>\r\n"
				+ "            <tr>\r\n" + "              <td style=\"margin: 5px 0\" align=\"center\">\r\n"
				+ "                <a href=https://mngp.vercel.app/payment?$" + userId
				+ " style=\"text-decoration: none; border: 1px solid #c71e4c; padding: 16px; margin: 5px 0; color: white; background-color: #c71e4c; display: block; width: 40%\">Proceed to payment</a>\r\n"
				+ "              </td>\r\n" + "            </tr>\r\n" + "            <tr>\r\n"
				+ "              <td style='padding: 10px 0; color: #333333;'>\r\n"
				+ "                <p style='font-size: 16px; margin: 0;'>\r\n"
				+ "                  Warm regards, <br/>\r\n"
				+ "                 <strong> Mission-NGP Partnership Team</strong>\r\n" + "                </p>\r\n"
				+ "              </td>\r\n" + "            </tr>\r\n" + "            <tr>\r\n" + "              <td\r\n"
				+ "                align='center'\r\n"
				+ "                style='padding: 20px 0; color: #999999; font-size: 12px;'\r\n"
				+ "              >\r\n"
				+ "                <p style='margin: 0; font-size: 14px;'>© 2025 Mission-NGP. All rights reserved.</p>\r\n"
				+ "              </td>\r\n" + "            </tr>\r\n" + "          </table>\r\n" + "        </td>\r\n"
				+ "      </tr>\r\n" + "    </table>\r\n" + "  </body>\r\n" + "</html>";

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
