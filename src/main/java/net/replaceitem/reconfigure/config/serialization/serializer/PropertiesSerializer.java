package net.replaceitem.reconfigure.config.serialization.serializer;

import com.google.gson.JsonParseException;
import net.replaceitem.reconfigure.config.serialization.*;
import net.replaceitem.reconfigure.util.OrderedProperties;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PropertiesSerializer extends CharSerializer<String, Properties> {
    
    private static final PropertiesMarshaller MARSHALLER = new PropertiesMarshaller();

    public PropertiesSerializer(@Nullable Consumer<Properties> preLoad, @Nullable Consumer<Properties> preWrite) {
        super(preLoad, preWrite);
    }

    @Override
    public Marshaller<String> getMarshaller() {
        return MARSHALLER;
    }

    @Override
    protected Properties read(Reader reader) throws IOException, JsonParseException {
        Properties properties = new Properties();
        properties.load(reader);
        return properties;
    }

    @Override
    protected void write(Writer writer, Properties compound) throws IOException {
        compound.store(writer, "");
    }

    @Override
    protected void load(SerializationTarget target, Properties compound) {
        for (Map.Entry<Object, Object> entry : compound.entrySet()) {
            this.setProperty(target, entry.getKey().toString(), entry.getValue().toString());
        }
    }

    @Override
    protected Properties save(SerializationTarget target) {
        Properties properties = new OrderedProperties();
        for (SerializationTarget.SerializationProperty<?> holder : target.getProperties()) {
            properties.setProperty(holder.getId().getPath(), this.getProperty(holder));
        }
        return properties;
    }

    @Override
    public String getFileExtension() {
        return "properties";
    }
    
    private static class PropertiesMarshaller extends Marshaller<String> {

        private static final String ESCAPED_COMMA_REPLACEMENT = Matcher.quoteReplacement("\\,");

        @Override
        public String marshall(Intermediary<?> intermediary) {
            return switch (intermediary) {
                case Intermediary.IntermediaryString intermediaryString -> intermediaryString.getValue();
                case Intermediary.IntermediaryInteger intermediaryInteger -> Integer.toString(intermediaryInteger.getValue());
                case Intermediary.IntermediaryDouble intermediaryDouble -> Double.toString(intermediaryDouble.getValue());
                case Intermediary.IntermediaryBoolean intermediaryBoolean -> Boolean.toString(intermediaryBoolean.getValue());
                case Intermediary.IntermediaryList intermediaryList -> intermediaryList.getValue().stream().map(s -> s.replaceAll(",", ESCAPED_COMMA_REPLACEMENT)).collect(Collectors.joining(","));
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
        
        private static final Pattern SPLIT_LIST_REGEX = Pattern.compile("(?<!\\\\),");

        @Override
        protected List<String> unmarshallList(String value) {
            return new ArrayList<>(SPLIT_LIST_REGEX.splitAsStream(value).map(s -> s.replace("\\,", ",")).toList());
        }
    }
}
