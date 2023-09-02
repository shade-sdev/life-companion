package dev.shade.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import java.io.OutputStream;
import java.net.URI;
import java.time.Instant;

public class SecurityProblemDetail {

    @SneakyThrows
    public void response(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ProblemDetail re = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        re.setType(URI.create("http://shade-platform.com/errors/forbidden"));
        re.setTitle(HttpStatus.FORBIDDEN.getReasonPhrase());
        re.setInstance(URI.create(request.getRequestURI()));
        re.setProperty("timestamp", Instant.now());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(responseStream, re);
        responseStream.flush();
    }
}
