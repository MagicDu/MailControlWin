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

	private static String protocol = "imap";//Ĭ����imap��Э�飬��Ϊimap���Ի�ȡ���ʼ���pop3��̫���
	private static Properties props;
	private static Session session;
	private static IMAPStore store;
	private static IMAPFolder folder;
	private static String message;
	private volatile static boolean running = true;
	private static long cycle = 1000;
	public static User user=new User();
	
	/**
	 * ��ʼ�����ӵĺ���
	 */
	public static void connect() {
		try {
			props = new Properties();
			props.setProperty("mail.store.protocol", protocol);
			props.setProperty("mail.imap.host", user.getImapServer());
			session = Session.getInstance(props);
			store = (IMAPStore) session.getStore("imap"); // ʹ��imap�Ự���ƣ����ӷ�����
			store.connect(user.getUsername(), user.getPassword());
			folder = (IMAPFolder) store.getFolder("INBOX"); // �ռ���
			//folder.open(Folder.READ_WRITE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���յ�����󣬵���dos����ĺ���
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
					// ��ȡ�����������ַ
					reply = reply.substring(reply.indexOf("<") + 1,
							reply.indexOf(">"));
					// ��ȡ�ʼ����еı��
					Flags flags = messages[i].getFlags();
					// ���ж��ʼ��Ƿ��Ѷ�
					if (!flags.contains(Flags.Flag.SEEN)) {
						// System.out.println(reply);
						// �ж��ʼ���Դ��ָ����Դ�Ƿ�һ��
						if (reply.equals(user.getMailbox())) {
							// ���һ�£��ʹ�ӡ�ʼ�����
							System.out.println(subject);
							message=subject;
							excuteCMD(message);
							// ���ʼ�����Ϊ�Ѷ�
							messages[i].setFlag(Flags.Flag.SEEN, true);
						} else {
							System.out.println("û������ָ��������ʼ�");
						}
					} else {
						System.out.println("û��δ���ʼ�");
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
