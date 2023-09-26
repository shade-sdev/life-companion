package dev.shade.application.service.user.impl;

import dev.shade.shared.event.user.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public UserEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(UserEvent userEvent) {
        publisher.publishEvent(userEvent);
    }
}
