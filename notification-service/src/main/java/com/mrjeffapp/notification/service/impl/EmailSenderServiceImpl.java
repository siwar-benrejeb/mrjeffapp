package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.model.Email;
import com.mrjeffapp.notification.service.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final static Logger LOG = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private static final String EMAIL_CONTENT_TYPE = "text/html";
    private static final String EMAIL_ENCODING_UTF8 = "utf-8";
    private final JavaMailSender javaMailSender;
    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Email sendEmail(Email email) {
        try {
            buildAndSendEmail(email);
            LOG.info("email sent: email={}", email);

        } catch (MessagingException e) {
            LOG.error("Error sending email: emailTypeCode=" + email.getEmailTypeCode() + ", destination=" + email.getDestination(), e);
        }

        return email;
    }

    private void buildAndSendEmail(Email email) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setContent(email.getContent(), EMAIL_CONTENT_TYPE);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, EMAIL_ENCODING_UTF8);
        helper.setSubject(email.getSubject());
        helper.setTo(email.getDestination());
        javaMailSender.send(mimeMessage);
        LOG.debug("Trying to send email: {}", email);

        LOG.debug("Trying to send email: {}", email);
    }

}
