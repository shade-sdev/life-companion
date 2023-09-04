package dev.shade.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.shade.shared.exception.model.GlobalProblemDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.OutputStream;
import java.net.URI;

public class SecurityProblemDetail {

    @SneakyThrows
    public void response(HttpServletRequest request, HttpServletResponse response, Exception e) {
        GlobalProblemDetail globalProblemDetail = new GlobalProblemDetail("ACCESS_DENIED",
                                                                          e.getMessage(),
                                                                          HttpStatus.FORBIDDEN,
                                                                          URI.create(request.getRequestURI()));

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(responseStream, globalProblemDetail);
        responseStream.flush();
    }
}
