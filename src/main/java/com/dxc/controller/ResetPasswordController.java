package com.dxc.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dxc.entity.ApiResponse;
import com.dxc.entity.Email;
import com.dxc.entity.Users;
import com.dxc.service.UserService;

@RestController
@RequestMapping("/api")
public class ResetPasswordController {
	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}") 
	private String sender;

	@PostMapping("/forgotpassword")
	public ApiResponse<String> processForgotPassword(@RequestParam String email) throws UnsupportedEncodingException, MessagingException {
		String token = userService.updateResetPasswordToken(email);
		
		if (!token.startsWith("Could")) {
			String resetPasswordLink = "Your reset password token is :" + token
					+ "\nThe token will expire in 1 hour ";
			Email sendEmail = new Email(email, resetPasswordLink, "Reset Password");
			sendEmail(sendEmail);
			return new ApiResponse<String>(HttpStatus.OK.value(), "We have sent a reset password link to your email. Please check.", null);
		}
		return new ApiResponse<String>(HttpStatus.FORBIDDEN.value(), "Failed to send reset password email.", null);
	}

	public void sendEmail(Email email) throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(sender);
		helper.setTo(sender);
		helper.setText(email.getMsgBody());
		helper.setSubject(email.getSubject());

		// Sending the mail
		mailSender.send(message);

	}

	@PostMapping("/resetpassword")
	public ApiResponse<Users> processResetPassword(@RequestParam String token, @RequestParam String password) {

		Users users = userService.getByResetPasswordToken(token);

		if (users == null) {
			return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Token not found.", null);
		} else {
			userService.updatePassword(users, password);
			return new ApiResponse<>(HttpStatus.OK.value(), "You have successfully changed your password.", null);
		}
	}
}
