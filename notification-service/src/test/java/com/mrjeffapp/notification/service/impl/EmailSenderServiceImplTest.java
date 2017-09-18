package com.mrjeffapp.notification.service.impl;

import com.mrjeffapp.notification.model.Email;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailSenderServiceImplTest {
    private static final String EMAIL_CONTENT_TYPE = "text/html";
    private static final String EMAIL_ENCODING_UTF8 = "utf-8";

    private static final String EMAIL_TYPE = "EMAIL_TYPE";
    private static final String EMAIL_DESTINATION = "EMAIL_DESTINATION@test.com";
    private static final String EMAIL_SUBJECT = "EMAIL_SUBJECT";
    private static final String EMAIL_CONTENT = "EMAIL_CONTENT";

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;

    @Mock
    private MimeMessage mimeMessage;

    private Email email;

    @Before
    public void setUp() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        email = new Email(EMAIL_TYPE, EMAIL_DESTINATION, EMAIL_SUBJECT, EMAIL_CONTENT);
    }

    @Test
    public void testEmailSent() throws Exception {
        emailSenderService.sendEmail(email);

        verify(mimeMessage).setSubject(EMAIL_SUBJECT, EMAIL_ENCODING_UTF8);
        verify(mimeMessage).setContent(EMAIL_CONTENT, EMAIL_CONTENT_TYPE);

        Address destinationAddress = Arrays.stream(InternetAddress.parse(EMAIL_DESTINATION)).findFirst().get();
        verify(mimeMessage).setRecipient(Message.RecipientType.TO, destinationAddress);

        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    public void testEmailNotSentOnFailure() throws Exception {
        doThrow(new MessagingException()).when(mimeMessage).setContent(EMAIL_CONTENT, EMAIL_CONTENT_TYPE);

        emailSenderService.sendEmail(email);

        verify(javaMailSender, never()).send(mimeMessage);
    }

}
