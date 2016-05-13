# MailControlWin
A java project which can be used to control your computer by your email's subject
初学java，写了一个运用javamail远程控制电脑的小工程,
实现原理：
java mail
多线程
Runtime
实现思路：
运用多线程不断遍历邮箱新邮件，获取指定邮箱发送的新邮件的主题，调用Runtime.getRuntime.exec()方法执行dos命令
此工程存在诸多不足,连接imap服务器时根据网络会有异常，多线程死循环也存在不足，界面草草，将会在今后的深入学习中继续改进
