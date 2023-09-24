package dev.shade.shared.event.user;

import dev.shade.shared.event.ActionType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Value
@Builder
public class UserEvent extends ApplicationEvent {

    ActionType actionType;

    UUID userId;

    String email;

    public UserEvent(ActionType actionType, UUID userId, String email) {
        super(actionType);
        this.actionType = actionType;
        this.userId = userId;
        this.email = email;
    }

}
