package com.dzq.win;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dzq.mail.ImapMail;

public class MainWin extends JFrame {
	JLabel imapServer = new JLabel("IMAP服务器：");
	JLabel username = new JLabel("用户名：");
	JLabel password = new JLabel("密码：");
	JLabel mailbox = new JLabel("邮箱名：");
	JTextField t_imapServer = new JTextField(20);
	JTextField t_username = new JTextField(20);
	JPasswordField t_password = new JPasswordField(20);
	JTextField t_mail = new JTextField(20);
	JButton start = new JButton("开始监听");
	JButton end = new JButton("停止监听");
	Box hBox0 = Box.createHorizontalBox();
	Box hBox1 = Box.createHorizontalBox();
	Box hBox2 = Box.createHorizontalBox();
	Box hBox3 = Box.createHorizontalBox();
	Box hBox4 = Box.createHorizontalBox();
	Box hBox5 = Box.createHorizontalBox();
	JPanel center = new JPanel();
	Font f2 = new Font("隶书", Font.PLAIN, 15);

	public static void main(String[] args) {
		JFrame mainwin = new MainWin();
		mainwin.setVisible(true);
	}

	public MainWin() {
		setTitle("监听新邮件");
		setSize(300, 250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();// 获取屏幕分辨率
		setLocation(screenSize.width / 4, screenSize.height / 4);// 位置
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		imapServer.setFont(f2);
		username.setFont(f2);
		password.setFont(f2);
		mailbox.setFont(f2);
		start.setFont(f2);
		end.setFont(f2);
		hBox1.add(imapServer);
		hBox1.add(t_imapServer);
		hBox2.add(username);
		hBox2.add(t_username);
		hBox3.add(password);
		hBox3.add(t_password);
		hBox4.add(mailbox);
		hBox4.add(t_mail);
		hBox5.add(start);
		hBox5.add(end);
		center.add(hBox1);
		center.add(Box.createRigidArea(new Dimension(5, 20)));
		center.add(hBox2);
		center.add(Box.createRigidArea(new Dimension(5, 20)));
		center.add(hBox3);
		center.add(Box.createRigidArea(new Dimension(5, 20)));
		center.add(hBox4);
		center.add(Box.createRigidArea(new Dimension(5, 20)));
		center.add(hBox5);
		add(center);
		imapServer.setPreferredSize(new Dimension(100, 20));
		username.setPreferredSize(new Dimension(100, 20));
		password.setPreferredSize(new Dimension(100, 20));
		mailbox.setPreferredSize(new Dimension(100, 20));
		start.addActionListener(new MyListener());
	}

	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String imapServer = t_imapServer.getText();
			String username = t_username.getText();
			String password = t_password.getText();
			String mailbox = t_mail.getText();
			ImapMail i1 = new ImapMail();
			ImapMail.user.setImapServer(imapServer);
			ImapMail.user.setMailbox(mailbox);
			ImapMail.user.setPassword(password);
			ImapMail.user.setUsername(username);
			Thread t1 = new Thread(i1);
			t1.start();
		}

	}
}
