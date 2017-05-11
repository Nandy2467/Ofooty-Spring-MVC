package com.nandeep.ofooty.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	public static boolean sendEmail(String subject,String bodyMessage){
	    
        Properties properties=new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");
        Session session=Session.getDefaultInstance(properties,
              new javax.mail.Authenticator() 
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("ofootynandeep@gmail.com","ofooty2467");
                }
            }        
        );
     
    
        try
        {
            Message message= new MimeMessage(session);
            message.setFrom(new InternetAddress("ofooty2467@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("6179522948@tmomail.net"));
            message.setSubject(subject);
            message.setText(bodyMessage);
            Transport.send(message);
        }
        catch(Exception e)
        {
            return false;
        }
    
    return true;  
}
	
}
