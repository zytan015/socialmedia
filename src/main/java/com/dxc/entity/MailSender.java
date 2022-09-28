package com.dxc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailSender {
	
	private String recipient;
	private String password ;
	private String host;
	private int port;

}
