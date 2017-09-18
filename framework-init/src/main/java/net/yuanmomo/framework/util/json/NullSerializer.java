package net.yuanmomo.framework.util.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

@JacksonStdImpl
public class NullSerializer extends StdSerializer<Object> {

	public final static NullSerializer instance = new NullSerializer();
	private static final long serialVersionUID = -1508630355231278776L;

	private NullSerializer() {
		super(Object.class);
	}

	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonGenerationException {
		if (value instanceof Number) {
			jgen.writeString("0");
		}
		jgen.writeString("");
	}

	@Override
	public JsonNode getSchema(SerializerProvider provider, Type typeHint)
			throws JsonMappingException {
		return createSchemaNode("null");
	}

	@Override
	public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor,
			JavaType typeHint) throws JsonMappingException {
		visitor.expectNullFormat(typeHint);
	}
}
