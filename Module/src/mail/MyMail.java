package mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class MyMail {
	
	public static void main(String[] args) throws Exception {
//		String dest = "liuyunchao@zju.edu.cn";
		String dest = "490888198@qq.com";
//		String dest = "stariy.zju@gmail.com";
//		new MyMail().sendByQQ(dest);
		new MyMail().sendBy163(dest);
	}
	
	public void sendByZJU(String dest) throws Exception{
		Properties pros = new Properties();
		pros.put("mail.transport.protocol", "smtp");
		pros.put("mail.smtp.auth", "true");
		pros.put("mail.host", "zjuem.zju.edu.cn");
		Session session = Session.getInstance(pros, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("liuyunchao","12345");
			}
		});
		session.setDebug(true);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("490888198@qq.com"));
		message.setSubject("生日快乐！");
		message.setRecipients(RecipientType.TO, InternetAddress.parse(dest));
		message.setContent("<span style='color:red'>生日快乐！</span>", "text/html;charset=gbk");
		
		Transport transport = session.getTransport();
		Transport.send(message);
		transport.close();		
	}
	
	public void sendByQQ(String dest) throws Exception{
		Properties pros = new Properties();
		pros.put("mail.transport.protocol", "smtp");
		pros.put("mail.smtp.auth", "true");
		pros.put("mail.host", "smtp.qq.com");
		Session session = Session.getInstance(pros, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("490888198@qq.com","lyc357405131");
			}
		});
		session.setDebug(true);
		
		Message message = new MimeMessage(session);
		message.setFrom(getMailAddress("柳云超","490888198@qq.com"));
		message.setSubject("生日快乐！");
		message.setRecipients(RecipientType.TO, InternetAddress.parse(dest));
		message.setContent("<span style='color:red'>生日快乐！</span>", "text/html;charset=gbk");
		
		Transport transport = session.getTransport();
		Transport.send(message);
		transport.close();		
	}
	
	public void sendBy163(String dest) throws Exception{
		Properties pros = new Properties();
		pros.put("mail.transport.protocol", "smtp");
		pros.put("mail.smtp.auth", "true");
		pros.put("mail.host", "smtp.163.com");
		Session session = Session.getInstance(pros, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("zzhxlyc@163.com","82639446");
			}
		});
		session.setDebug(true);
		
		Message message = new MimeMessage(session);
		message.setFrom(getMailAddress("柳云超","zzhxlyc@163.com"));
		message.setSubject("生日快乐！");
		message.setRecipients(RecipientType.TO, InternetAddress.parse(dest));
		message.setContent("<span style='color:red'>生日快乐！</span>", "text/html;charset=gbk");
		
		Transport transport = session.getTransport();
		Transport.send(message);
		transport.close();		
	}
	
	private InternetAddress getMailAddress(String name, String mail){
		try {
			return new InternetAddress("\"" + MimeUtility.encodeText(name) + "\" <" + mail +">");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
