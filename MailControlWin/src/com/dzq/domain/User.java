package com.dzq.domain;

public class User {
	private String imapServer;//���imap������
	private String username;//�����¼��
	private String password;//�����¼����
	private String mailbox;//��Ҫ���յ��ĸ������������
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
