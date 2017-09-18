package com.mrjeffapp.provider.listener.domain;

import com.mrjeffapp.provider.domain.Authorization;
import com.mrjeffapp.provider.domain.event.AuthorizationAuthorizedEvent;
import com.mrjeffapp.provider.repository.AuthorizationRepository;
import com.mrjeffapp.provider.service.Notification.NotificationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorizationEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationEventListener.class);
    private NotificationServiceImpl notificationServiceImpl;
    private AuthorizationRepository authorizationRepository;

    @Autowired
    public AuthorizationEventListener(NotificationServiceImpl notificationServiceImpl,
                                      AuthorizationRepository authorizationRepository) {
        this.notificationServiceImpl = notificationServiceImpl;
        this.authorizationRepository = authorizationRepository;
    }

    @Transactional
    @EventListener
    public void handleAuthorizationAuthorizedEvent(AuthorizationAuthorizedEvent event) {

        event.getAuthorizationsAuthorized().forEach(
                authAuthorizedId-> {
                    Authorization auth = authorizationRepository.findOne(authAuthorizedId);
                    notificationServiceImpl.generateNotificationByAuthorization(
                            event.getUserOperator(),
                            auth.getProvider().getId(),
                            event.getDescription(),
                            auth.getProduct().getId()
                    );
                }
        );
    }
}
