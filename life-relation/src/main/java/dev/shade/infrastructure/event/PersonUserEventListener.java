package dev.shade.infrastructure.event;

import dev.shade.domain.person.Person;
import dev.shade.domain.repository.PersonRepository;
import dev.shade.shared.event.ActionType;
import dev.shade.shared.event.user.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

@Component
public class PersonUserEventListener {

    private final PersonRepository personRepository;

    @Autowired
    public PersonUserEventListener(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @TransactionalEventListener(value = UserEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void handleUserEvent(UserEvent userEvent) {
        if (Objects.requireNonNull(userEvent.getActionType()) == ActionType.CREATION) {
            personRepository.save(Person.initFromUser(userEvent.getUserId(), userEvent.getEmail()));
        }
    }

}
