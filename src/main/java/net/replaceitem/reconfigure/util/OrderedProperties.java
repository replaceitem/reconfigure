package net.replaceitem.reconfigure.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class OrderedProperties extends Properties {
    private final LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();

    @Override
    public synchronized Object put(Object key, Object value) {
        linkedHashMap.put(key, value);
        return super.put(key, value);
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return linkedHashMap.entrySet();
    }
}
