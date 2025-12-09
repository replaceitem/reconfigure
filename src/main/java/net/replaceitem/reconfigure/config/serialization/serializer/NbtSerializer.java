package net.replaceitem.reconfigure.config.serialization.serializer;

import net.minecraft.nbt.*;
import net.replaceitem.reconfigure.config.serialization.*;
import org.jspecify.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NbtSerializer extends Serializer<Tag, CompoundTag> {
    
    private static final NbtMarshaller MARSHALLER = new NbtMarshaller();

    public NbtSerializer(@Nullable Consumer<CompoundTag> preLoad, @Nullable Consumer<CompoundTag> preWrite) {
        super(preLoad, preWrite);
    }

    @Override
    protected void write(OutputStream outputStream, CompoundTag compound) throws IOException {
        NbtIo.write(compound, new DataOutputStream(outputStream));
    }

    @Override
    protected CompoundTag read(InputStream inputStream) throws IOException {
        return NbtIo.read(new DataInputStream(inputStream), NbtAccounter.unlimitedHeap());
    }

    @Override
    protected void load(SerializationTarget target, CompoundTag compound) {
        for (String key : compound.keySet()) {
            Tag value = compound.get(key);
            if(value == null) continue;
            this.setProperty(target, key, value);
        }
    }

    @Override
    protected CompoundTag save(SerializationTarget target) {
        CompoundTag nbtCompound = new CompoundTag();
        for (SerializationTarget.SerializationProperty<?> property : target.getProperties()) {
            Tag value = this.getProperty(property);
            nbtCompound.put(property.getId().getPath(), value);
        }
        return nbtCompound;
    }

    @Override
    public String getFileExtension() {
        return "nbt";
    }

    @Override
    public Marshaller<Tag> getMarshaller() {
        return MARSHALLER;
    }
    
    private static class NbtMarshaller extends Marshaller<Tag> {

        @Override
        public Tag marshall(Intermediary<?> intermediary) {
            return switch (intermediary) {
                case Intermediary.IntermediaryString intermediaryString -> StringTag.valueOf(intermediaryString.getValue());
                case Intermediary.IntermediaryInteger intermediaryInteger -> IntTag.valueOf(intermediaryInteger.getValue());
                case Intermediary.IntermediaryDouble intermediaryDouble -> DoubleTag.valueOf(intermediaryDouble.getValue());
                case Intermediary.IntermediaryBoolean intermediaryBoolean -> ByteTag.valueOf(intermediaryBoolean.getValue());
                case Intermediary.IntermediaryList intermediaryList -> createStringList(intermediaryList.getValue());
            };
        }

        @Override
        protected String unmarshallString(Tag value) throws SerializationException {
            return value.asString().orElseThrow(() -> new SerializationException("Expected a string"));
        }

        @Override
        protected Integer unmarshallInteger(Tag value) throws SerializationException {
            if (!(value instanceof NumericTag nbtNumber)) throw new SerializationException("Expected a number");
            return nbtNumber.intValue();
        }

        @Override
        protected Double unmarshallDouble(Tag value) throws SerializationException {
            if (!(value instanceof NumericTag nbtNumber)) throw new SerializationException("Expected a number");
            return nbtNumber.doubleValue();
        }

        @Override
        protected Boolean unmarshallBoolean(Tag value) throws SerializationException {
            if (!(value instanceof ByteTag nbtNumber)) throw new SerializationException("Expected a byte");
            return nbtNumber.byteValue() != 0;
        }

        @Override
        protected List<String> unmarshallList(Tag value) throws SerializationException {
            if (!(value instanceof ListTag nbtList)) throw new SerializationException("Expected a list");
            return new ArrayList<>(nbtList.stream().map(Tag::toString).toList());
        }

        private static ListTag createStringList(List<String> strings) {
            ListTag nbtList = new ListTag();
            nbtList.addAll(strings.stream().map(StringTag::valueOf).toList());
            return nbtList;
        }
    }
}
