package net.yuanmomo.framework.util.json;

import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class StdSerializerProvider extends DefaultSerializerProvider {
    private static final long serialVersionUID = 1L;

    public StdSerializerProvider() { super(); }

    protected StdSerializerProvider(SerializerProvider src,
            SerializationConfig config,SerializerFactory f) {
        super(src, config, f);
    }

    @Override
    public StdSerializerProvider createInstance(SerializationConfig config,
            SerializerFactory jsf) {
        return new StdSerializerProvider(this, config, jsf);
    }
}