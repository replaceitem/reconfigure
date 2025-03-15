package net.replaceitem.reconfigure.config.serialization.builder;

import net.replaceitem.reconfigure.api.serializer.PropertiesSerializerBuilder;
import net.replaceitem.reconfigure.config.serialization.serializer.PropertiesSerializer;

import java.util.Properties;

public class PropertiesSerializerBuilderImpl extends SerializerBuilderImpl<PropertiesSerializerBuilder, PropertiesSerializer, Properties> implements PropertiesSerializerBuilder {
    @Override
    public PropertiesSerializer build() {
        return new PropertiesSerializer(this.preLoad, this.preWrite);
    }
}
