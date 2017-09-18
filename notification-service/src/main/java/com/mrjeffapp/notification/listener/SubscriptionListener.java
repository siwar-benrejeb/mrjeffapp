package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.ProductAddedSubscriptionEvent;
import com.mrjeffapp.event.StudentSubscriptionEvent;
import com.mrjeffapp.event.SubscriptionEvent;
import com.mrjeffapp.event.WeeklyReportEvent;
import com.mrjeffapp.notification.service.ProductAddedSubscriptionService;
import com.mrjeffapp.notification.service.StudentSubscriptionService;
import com.mrjeffapp.notification.service.SubscriptionService;
import com.mrjeffapp.notification.service.WeeklyReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@RabbitListener(queues = QUEUE_SUBSCRIPTION)
public class SubscriptionListener {
    private final static Logger LOG = LoggerFactory.getLogger(SubscriptionListener.class);
    private final StudentSubscriptionService studentSubscriptionService;
    private final SubscriptionService subscriptionService;
    private final WeeklyReportService weeklyReportService;
    private final ProductAddedSubscriptionService productAddedSubscriptionService;

    @Autowired
    public SubscriptionListener(StudentSubscriptionService studentSubscriptionService,
                                SubscriptionService subscriptionService,
                                WeeklyReportService weeklyReportService,
                                ProductAddedSubscriptionService productAddedSubscriptionService) {
        this.studentSubscriptionService = studentSubscriptionService;
        this.subscriptionService = subscriptionService;
        this.weeklyReportService = weeklyReportService;
        this.productAddedSubscriptionService = productAddedSubscriptionService;
    }

    @RabbitHandler
    public void receiveStudentSubscription(StudentSubscriptionEvent studentSubscriptionEvent) {


        LOG.info("Received: customer ={}", studentSubscriptionEvent);
        studentSubscriptionService.sendStudentSubscriptionEmail(studentSubscriptionEvent.getCustomerId());
        LOG.info("mail sent successfully");
    }

    @RabbitHandler
    public void receiveSubscription(SubscriptionEvent subscriptionEvent) {
        LOG.info("Received: customer ={}", subscriptionEvent);
        subscriptionService.sendSubscriptionEmail(subscriptionEvent.getCustomerId());
        LOG.info("mail sent successfully");

    }

    @RabbitHandler
    public void receiveWeeklyReport(WeeklyReportEvent weeklyReportEvent) {
        LOG.info("Received: customer ={}", weeklyReportEvent);
        weeklyReportService.sendWeeklyReportEmail(weeklyReportEvent.getCustomerId());
        LOG.info("mail sent successfully");

    }

    @RabbitHandler
    public void receiveProductAddedSubscription(ProductAddedSubscriptionEvent productAddedSubscriptionEvent) {
        LOG.info("Received: customer ={}", productAddedSubscriptionEvent);
        productAddedSubscriptionService.sendProductAddedSubscriptionEmail(productAddedSubscriptionEvent.getOrderId());
        LOG.info("mail sent successfully");

    }
}
