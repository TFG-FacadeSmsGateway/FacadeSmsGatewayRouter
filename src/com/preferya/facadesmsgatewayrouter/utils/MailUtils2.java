/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Sergio
 */
public class MailUtils2 {
    
    private Session session;
    
    public MailUtils2(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        this.session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("",""); //TODO: Rellenar solo el usuario y la contraseña de gmail (preferible)
                        }
                });
    }
    
    public void sendMessage(String mailTo, String subject, String text){
        try {
                Message _message = new MimeMessage(this.session);
                _message.setFrom(new InternetAddress("")); //TODO: rellenar con vuestra cuenta, preferible gmail.
                _message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(mailTo));
                _message.setSubject(subject);
                _message.setText(text);

                Transport.send(_message);

                System.out.println("Done");

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
    }
    
}
