package net.replaceitem.reconfigure.util;

import org.jetbrains.annotations.Nullable;

import java.util.Stack;

/**
 * Because the real one is still in preview and thread local sucks
 */
public class ScopedThreadLocal<T> {
    public final ThreadLocal<Stack<T>> threadLocal = ThreadLocal.withInitial(Stack::new);
    
    public ScopedValueCloseable with(T t) {
        threadLocal.get().push(t);
        return new ScopedValueCloseable(t);
    }
    
    @Nullable
    public T get() {
        Stack<T> stack = threadLocal.get();
        if(stack.isEmpty()) return null;
        return stack.peek();
    }
    
    public class ScopedValueCloseable implements AutoCloseable {
        private final T value;

        public ScopedValueCloseable(T value) {
            this.value = value;
        }

        @Override
        public void close() {
            Stack<T> stack = threadLocal.get();
            if(stack.peek() != value) {
                throw new IllegalStateException("Closing scoped value didn't have the same value anymore");
            }
            stack.pop();
        }
    }
}
