package dev.shade.shared.exception.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.LocalDateTime;

@JsonSerialize(using = GlobalProblemDetailSerializer.class)
@Getter
@EqualsAndHashCode(callSuper = false)
public class GlobalProblemDetail extends ProblemDetail {

    private final LocalDateTime timestamp;

    public GlobalProblemDetail(String title, String detail, HttpStatus status) {
        this.setTitle(title);
        this.setStatus(status.value());
        this.setDetail(detail);
        this.setType(URI.create("http://localhost/api/errors/" + status.getReasonPhrase().replace(" ", "-").toLowerCase()));
        this.timestamp = LocalDateTime.now();
    }

    public GlobalProblemDetail(String title, String detail, HttpStatus status, URI instance) {
        this.setTitle(title);
        this.setStatus(status.value());
        this.setDetail(detail);
        this.setInstance(instance);
        this.setType(URI.create("http://localhost/api/errors/" + status.getReasonPhrase().replace(" ", "-").toLowerCase()));
        this.timestamp = LocalDateTime.now();
    }

}
