/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <br/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.bytes;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * This class is a wrapper around the {@link sun.misc.Unsafe} and
 * {@link jdk.internal.misc.Unsafe} classes. It is used to access certain
 * low-level memory-related operations that are not accessible through standard
 * Java APIs.
 *
 * @implNote
 * This class is not thread-safe and should be used with
 * caution. In particular, the behavior of the methods provided by this class is
 * undefined if called concurrently from multiple threads.
 *
 * @implSpec
 * This class relies on the availability of the {@code sun.misc.Unsafe} or
 * {@code jdk.internal.misc.Unsafe} class and the ability to access its
 * internal methods using reflection.
 *
 * project moonshine
 * created 23.02.2023 11:06
 *
 * @author Alexander A. Kropotin
 */
final class UnsafeHelper {

    public static final UnsafeHelper INSTANCE = new UnsafeHelper();

    private static final Class<?> UNSAFE_CLASS = unsafeClass();

    private static final Object UNSAFE_INSTANCE = getUnsafeInstance();

    private static final MethodHandle ALLOCATE_MEMORY_HANDLE = allocateMemoryHandle();

    private static final MethodHandle PUT_SHORT_HANDLE = putShortHandle();

    private static final MethodHandle FREE_MEMORY_HANDLE = freeMemoryHandle();

    private static final MethodHandle GET_BYTE_HANDLE = getByteHandle();

    private UnsafeHelper() {}

    public static UnsafeHelper getInstance() {
        return INSTANCE;
    }

    /**
     * Determines whether the underlying platform is <b>big-endian</b> or
     * <b>little-endian</b>.
     *
     * @implSpec
     * This method uses a byte-order probe to determine whether the underlying
     * platform is big-endian or little-endian. It allocates a 2-byte buffer
     * using the {@code allocateMemory} method, writes the value
     * {@codee 0x10000001} to the buffer using the {@code putShort} method,
     * reads the first byte of the buffer using the {@code getByte} method, and
     * then deallocates the buffer using the {@code freeMemory} method.
     * If the first byte of the buffer is {@code 0x01}, the platform is
     * little-endian; if it is {@code 0x10}, the platform is big-endian.
     *
     * @return {@code true} if the platform is big-endian, {@code false} if it
     * is little-endian.
     *
     * @throws RuntimeException if an error occurs during executing.
     */
    public boolean isBigEndian() {
        try (MemoryBlock block = new MemoryBlock(2)) {
            long a = block.getAddress();

            PUT_SHORT_HANDLE.invoke(UNSAFE_INSTANCE, a, (short) 0x10000001);
            byte b = (byte) GET_BYTE_HANDLE.invoke(UNSAFE_INSTANCE, a);

            switch (b) {
                case 0x01:
                    return false;
                case 0x10:
                    return true;
                default:
                    assert false;
                    return false;
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getUnsafeInstance() {
        return fieldInstance("theUnsafe");
    }

    private static MethodHandle getByteHandle() {
        return methodHandle("getByte", MethodType.methodType(byte.class, long.class));
    }

    private static MethodHandle allocateMemoryHandle() {
        return methodHandle("allocateMemory", MethodType.methodType(long.class, long.class));
    }

    private static MethodHandle putShortHandle() {
        return methodHandle("putShort", MethodType.methodType(void.class, long.class, short.class));
    }

    private static MethodHandle freeMemoryHandle() {
        return methodHandle("freeMemory", MethodType.methodType(void.class, long.class));
    }

    private static Class<?> unsafeClass() {
        try {
            return Class.forName("sun.misc.Unsafe");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName("jdk.internal.misc.Unsafe");
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    private static MethodHandle methodHandle(String methodName, MethodType methodType) {
        try {
            return MethodHandles.publicLookup().findVirtual(UNSAFE_CLASS, methodName, methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object fieldInstance(String fieldName) {
        try {
            Field f = UNSAFE_CLASS.getDeclaredField(fieldName);
            f.setAccessible(true);
            Object obj = f.get(null);
            f.setAccessible(false);

            return obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    final static class MemoryBlock implements AutoCloseable {

        private final long address;

        private MemoryBlock(long size) {
            try {
                this.address = (long) ALLOCATE_MEMORY_HANDLE.invoke(UNSAFE_INSTANCE, size);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public static MemoryBlock allocate(long size) {
            return new MemoryBlock(size);
        }

        public void free() {
            try {
                FREE_MEMORY_HANDLE.invoke(UNSAFE_INSTANCE, this.address);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public long getAddress() {
            return address;
        }

        @Override
        public void close() {
            this.free();
        }
    }
}
