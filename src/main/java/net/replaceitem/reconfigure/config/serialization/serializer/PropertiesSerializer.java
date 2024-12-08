package net.replaceitem.reconfigure.config.serialization.serializer;

import com.google.gson.JsonParseException;
import net.replaceitem.reconfigure.config.serialization.*;
import net.replaceitem.reconfigure.util.OrderedProperties;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

public class PropertiesSerializer extends CharSerializer<String> {
    
    private static final PropertiesMarshaller MARSHALLER = new PropertiesMarshaller();

    @Override
    public Marshaller<String> getMarshaller() {
        return MARSHALLER;
    }

    @Override
    public void write(SerializationTarget target, Writer writer) throws IOException, JsonParseException {
        Properties properties = new OrderedProperties();
        for (SerializationTarget.SerializationProperty<?> holder : target.getProperties()) {
            properties.setProperty(holder.getId().getPath(), this.getProperty(holder));
        }
        properties.store(writer, target.getNamespace());
    }

    @Override
    public void read(SerializationTarget target, Reader reader) throws IOException, JsonParseException {
        Properties properties = new Properties();
        properties.load(reader);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            this.setProperty(target, entry.getKey().toString(), entry.getValue().toString());
        }
    }

    @Override
    public String getFileExtension() {
        return "properties";
    }
    
    private static class PropertiesMarshaller extends Marshaller<String> {

        @Override
        public String marshall(Intermediary<?> intermediary) {
            return switch (intermediary) {
                case Intermediary.IntermediaryString intermediaryString -> intermediaryString.getValue();
                case Intermediary.IntermediaryInteger intermediaryInteger -> Integer.toString(intermediaryInteger.getValue());
                case Intermediary.IntermediaryDouble intermediaryDouble -> Double.toString(intermediaryDouble.getValue());
                case Intermediary.IntermediaryBoolean intermediaryBoolean -> Boolean.toString(intermediaryBoolean.getValue());
            };
        }

        @Override
        protected String unmarshallString(String value) {
            return value;
        }

        @Override
        protected Integer unmarshallInteger(String value) throws SerializationException {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new SerializationException(e);
            } 
        }

        @Override
        protected Double unmarshallDouble(String value) throws SerializationException {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                throw new SerializationException(e);
            }
        }

        @Override
        protected Boolean unmarshallBoolean(String value) throws SerializationException {
            if(value.equalsIgnoreCase("true")) return true;
            if(value.equalsIgnoreCase("false")) return false;
            throw new SerializationException("Could not parse boolean. Expected 'true' or 'false'");
        }
    }
}
