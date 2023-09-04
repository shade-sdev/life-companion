package dev.shade.shared.exception.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class GlobalProblemDetailSerializer extends StdSerializer<GlobalProblemDetail> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss:SSS");

    public GlobalProblemDetailSerializer() {
        this(null);
    }

    public GlobalProblemDetailSerializer(Class<GlobalProblemDetail> t) {
        super(t);
    }

    @Override
    public void serialize(GlobalProblemDetail value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", value.getType().toString());
        gen.writeStringField("title", value.getTitle());
        gen.writeNumberField("status", value.getStatus());
        gen.writeStringField("detail", value.getDetail());
        gen.writeStringField("instance", Optional.of(value).map(GlobalProblemDetail::getInstance)
                                                 .map(String::valueOf)
                                                 .orElse(StringUtils.EMPTY));
        gen.writeStringField("timestamp", value.getTimestamp().format(FORMATTER));
        gen.writeEndObject();
    }
}
