/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class.Email;

import java.awt.HeadlessException;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.swing.JOptionPane;

/**
 *
 * @author Vikum
 */
public class Email {

    private String seaddress;
    private String sepassword;

    private Properties props;

    private Session se;
    private Message message;

    private Thread sending;

    public Email() {
        props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    private void setProfile(String seaddress, String sepassword) {
        this.seaddress = seaddress;
        this.sepassword = sepassword;
    }

    public void startSession(String seaddress, String sepassword) {
        setProfile(seaddress, sepassword);
        se = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(seaddress, sepassword);
                    }
                }
        );
    }

    public void sendEmail(String re, String sub, String text, String path) {
        sending = new Thread() {
            @Override
            public void run() {
                try {
                    message = new MimeMessage(se);
                    message.setFrom(new InternetAddress(seaddress));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(re));
                    message.setSubject(sub);
                    if (path.equals("")) {
                        message.setText(text);
                    } else {
                        message.setContent(attachFile(text, path));
                    }
                    Transport.send(message);
                    JOptionPane.showMessageDialog(null, "Email is been send!");
                } catch (MessagingException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "Email sendig failed.");
                }
                sending.stop();
            }
        };
        sending.start();
    }

    public void sendEmail(String re, String sub, String text) {
        sendEmail(re, sub, text, null);
    }

    public Multipart attachFile(String text, String path) {
        try {
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(text);
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp);

            mbp = new MimeBodyPart();
            DataSource ds = new FileDataSource(path);
            mbp.setDataHandler(new DataHandler(ds));
            mbp.setFileName("my");
            mp.addBodyPart(mbp);
            return mp;
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Attaching file faild.");
        }
        return null;
    }
    
    

}
