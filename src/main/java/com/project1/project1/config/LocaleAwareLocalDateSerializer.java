package com.project1.project1.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class LocaleAwareLocalDateSerializer extends StdSerializer<LocalDate> {

    public LocaleAwareLocalDateSerializer() {
        super(LocalDate.class);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                        .withLocale(LocaleContextHolder.getLocale());
        gen.writeString(value.format(formatter));
    }
}
