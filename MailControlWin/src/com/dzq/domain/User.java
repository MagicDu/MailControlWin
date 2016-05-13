package com.dzq.domain;

public class User {
	private String imapServer;//你的imap服务器
	private String username;//邮箱登录名
	private String password;//邮箱登录密码
	private String mailbox;//需要接收的哪个邮箱的邮箱名
	public String getImapServer() {
		return imapServer;
	}
	public void setImapServer(String imapServer) {
		this.imapServer = imapServer;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	
}
