package com.mrjeffapp.notification.service;

import com.mrjeffapp.notification.model.Email;

public interface EmailSenderService {

    Email sendEmail(Email email);

}
