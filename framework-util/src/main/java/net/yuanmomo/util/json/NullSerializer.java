/** 
 * Project Name : Tools
 * Package Name : net.yuanmomo.tools.plugin.spring.springmvc.json
 * Created on   : 2014-1-6下午3:09:46
 * File Name    : NullSerializer.java
 *
 * Author       : Hongbin.Yuan
 * Blog         : yuanmomo.net
 * Company      : 成都逗溜网科技有限公司  
 */

package net.yuanmomo.util.json;

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

/**
 * ClassName : NullSerializer Function : TODO ADD FUNCTION. Reason : TODO ADD
 * REASON. Date : 2014-1-6 下午3:09:46
 * 
 * @author : Hongbin.Yuan
 * @version
 * @since JDK 1.7
 * @see
 */
@JacksonStdImpl
public class NullSerializer extends StdSerializer<Object> {
	public final static NullSerializer instance = new NullSerializer();

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
