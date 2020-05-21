package com.doo9104.project.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    //텍스트 메일
    public void sendMail(String subject,String to,String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject(subject);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setText(content,"utf-8", "html");
        message.setSentDate(new Date());
        javaMailSender.send(message);
    }

    // 첨부파일 메일
    public void sendMailAttachment(String subject,String to,String content,String filepath,String filename) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(subject);
        helper.setSubject(to);
        helper.setText(content,true);

        FileSystemResource file
                = new FileSystemResource(new File(filepath));
        helper.addAttachment(filename, file);
        javaMailSender.send(message);

    }

}
