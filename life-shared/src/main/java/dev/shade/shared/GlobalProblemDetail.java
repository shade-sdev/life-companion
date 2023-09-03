package dev.shade.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.LocalDateTime;

public class GlobalProblemDetail extends ProblemDetail {

    public GlobalProblemDetail(String title, String detail, HttpStatus status) {
        this.setTitle(title);
        this.setStatus(status.value());
        this.setDetail(detail);
        this.setType(URI.create("http://localhost/api/errors/" + status.getReasonPhrase().replace(" ", "-").toLowerCase()));
        this.setProperty("timestamp", LocalDateTime.now());
    }

}
