package it.zano.shareride.utils;

import java.io.IOException;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalTimeTypeSerializer extends JsonSerializer<LocalTime> {

	private static final DateTimeFormatter TIME_FORMAT = ISODateTimeFormat.timeNoMillis();

	@Override
	public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeString(TIME_FORMAT.print(value));

	}

}
