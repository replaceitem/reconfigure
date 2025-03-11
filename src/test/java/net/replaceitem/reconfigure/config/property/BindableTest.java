package net.replaceitem.reconfigure.config.property;

import net.minecraft.util.Identifier;
import net.replaceitem.reconfigure.api.Bindable;
import net.replaceitem.reconfigure.config.ValidatorList;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BindableTest {
    private final Identifier ID = Identifier.of("test", "dummy");
    
    @Test
    void testListenerWhenChanged() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "a", new ValidatorList<>());
        MockListener<String> mockListener = new MockListener<>();

        mockListener.assertNotInvoked(() -> property.addListener(mockListener));
        mockListener.assertInvoked(() -> property.set("b"), "b");
        mockListener.assertNotInvoked(() -> property.set("b"));
        mockListener.assertInvoked(property::reset, "a");
        mockListener.assertNotInvoked(property::reset);
    }
    
    @Test
    void testListenerAddRemove() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "a", new ValidatorList<>());
        MockListener<String> mockListener = new MockListener<>();
        property.addListener(mockListener);
        mockListener.assertInvoked(() -> property.set("b"), "b");
        property.addListener(mockListener);
        mockListener.assertInvoked(() -> property.set("c"), "c", 2);
        mockListener.assertNotInvoked(() -> property.removeListener(mockListener));
        mockListener.assertInvoked(() -> property.set("d"), "d");
        mockListener.assertNotInvoked(() -> property.removeListener(mockListener));
        mockListener.assertNotInvoked(() -> property.set("d"));
    }
    
    @Test
    void testObserve() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "a", new ValidatorList<>());
        MockListener<String> mockListener = new MockListener<>();
        property.set("b");
        mockListener.assertInvoked(() -> property.observe(mockListener), "b");
        mockListener.assertInvoked(() -> property.set("c"), "c");
        mockListener.assertNotInvoked(() -> property.removeListener(mockListener));
        mockListener.assertNotInvoked(() -> property.set("d"));
    }
    
    @Test
    void testMapping() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "a", new ValidatorList<>());
        Bindable<String> mapped = property.map(s -> s.repeat(2));
        assertEquals("aa", mapped.get());
        property.set("b");
        assertEquals("bb", mapped.get());

        MockListener<String> mapMock = new MockListener<>();
        mapped.addListener(mapMock);
        mapMock.assertInvoked(() -> {
            property.set("c");
        }, "cc");
    }
    
    @Test
    void testMappingLazy() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "a", new ValidatorList<>());
        MockListener<String> mapMock = new MockListener<>();
        Function<String,String> mapping = s -> {
            mapMock.accept(s);
            return s;
        };
        mapMock.assertNotInvoked(() -> property.map(mapping));
    }
    
    @Test
    void testMappingMeoized() {
        PropertyImpl<String> property = new PropertyImpl<>(ID, "a", new ValidatorList<>());
        MockListener<String> mapMock = new MockListener<>();
        Function<String,String> mapping = s -> {
            mapMock.accept(s);
            return s.repeat(2);
        };
        Bindable<String> mapped = property.map(mapping);
        mapMock.assertInvoked(mapped::get, "a");
        mapMock.assertNotInvoked(mapped::get);
        property.set("b");
        mapMock.assertInvoked(mapped::get, "b");
    }
    
    
    private static class MockListener<T> implements Consumer<T> {
        private int invocations = 0;
        private int expectedInvocations = 0;
        @Nullable private T expectedValue;
        
        @Override
        public void accept(T value) {
            if(!Objects.equals(value, expectedValue) && expectedInvocations != 0) fail("Expected listener to be invoked with %s but was invoked with %s".formatted(expectedValue, value));
            invocations++;
        }
        
        public void assertInvoked(Runnable runnable, @Nullable T value) {
            assertInvoked(runnable, value, 1);
        }
        
        public void assertNotInvoked(Runnable runnable) {
            assertInvoked(runnable, null, 0);
        }
        
        private static String amountToString(int times) {
            if(times == 0) return "never";
            if(times == 1) return "once";
            return times + " times";
        }
        
        public void assertInvoked(Runnable runnable, @Nullable T value, int times) {
            expectedInvocations = times;
            invocations = 0;
            expectedValue = value;
            runnable.run();
            if(invocations != times) {
                fail("Expected listener to be executed %s, but was %s".formatted(amountToString(times), amountToString(invocations)));
            }
        }
    }
}
