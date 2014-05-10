/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.preferya.facadesmsgatewayrouter.utils;

import java.util.Map;
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
public class MailsUtils {
    
    private Session session;
    
    private String smtpHost;
    private String socketPort;
    private String port;
    private String auth;
    
    private String from;
    private String subject;
    
    public MailsUtils(){
        
        FileReadUtils fu = new FileReadUtils("mailprops.txt");
        Map<String, String> mapSetting = fu.getMapProviderPropSettings();
        
        this.smtpHost = mapSetting.get("smtphost");
        this.socketPort = mapSetting.get("socketport");
        this.port = mapSetting.get("port");
        this.auth = mapSetting.get("auth");
        this.from = mapSetting.get("from");
        this.subject = mapSetting.get("subject");
        
        Properties props = new Properties();
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.socketFactory.port", this.socketPort);
        props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", this.auth);
        props.put("mail.smtp.port", this.port);

        this.session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("",""); //TODO: Rellenar solo el usuario y la contraseña de gmail (preferible)
                        }
                });
    }
    
    public void sendMessage(String mailTo, String text){
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
