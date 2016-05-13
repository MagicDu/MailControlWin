package com.dzq.mail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;

import com.dzq.domain.User;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class ImapMail implements Runnable {

	private static String protocol = "imap";//默认是imap的协议，因为imap可以获取新邮件，pop3不太清楚
	private static Properties props;
	private static Session session;
	private static IMAPStore store;
	private static IMAPFolder folder;
	private static String message;
	private volatile static boolean running = true;
	private static long cycle = 1000;
	public static User user=new User();
	
	/**
	 * 初始化连接的函数
	 */
	public static void connect() {
		try {
			props = new Properties();
			props.setProperty("mail.store.protocol", protocol);
			props.setProperty("mail.imap.host", user.getImapServer());
			session = Session.getInstance(props);
			store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器
			store.connect(user.getUsername(), user.getPassword());
			folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
			//folder.open(Folder.READ_WRITE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 接收到主题后，调用dos命令的函数
	 * @param message
	 */

	public static void excuteCMD(String message){
		String cmdStr="cmd /c "+message;
		try {
			Runtime.getRuntime().exec(cmdStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		connect();
		while (running) {
			try {
				folder.open(Folder.READ_WRITE);
				Message[] messages = folder.getMessages();
				for (int i = 0; i < messages.length; i++) {
					String subject = messages[i].getSubject();
					String reply = messages[i].getReplyTo()[0].toString();
					// 截取发件人邮箱地址
					reply = reply.substring(reply.indexOf("<") + 1,
							reply.indexOf(">"));
					// 获取邮件所有的标记
					Flags flags = messages[i].getFlags();
					// 先判断邮件是否已读
					if (!flags.contains(Flags.Flag.SEEN)) {
						// System.out.println(reply);
						// 判断邮件来源与指定来源是否一致
						if (reply.equals(user.getMailbox())) {
							// 如果一致，就打印邮件主题
							System.out.println(subject);
							message=subject;
							excuteCMD(message);
							// 将邮件设置为已读
							messages[i].setFlag(Flags.Flag.SEEN, true);
						} else {
							System.out.println("没有来自指定邮箱的邮件");
						}
					} else {
						System.out.println("没有未读邮件");
					}
				}
				if (folder != null)
					folder.close(true);
				if (store != null)
					store.close();
			} catch (Throwable t) {
				connect();
				t.printStackTrace();
			}
			try {
				Thread.sleep(cycle);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
