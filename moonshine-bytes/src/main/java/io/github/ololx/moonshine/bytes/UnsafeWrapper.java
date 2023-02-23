/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
 * project moonshine
 * created 23.02.2023 11:06
 *
 * @author Alexander A. Kropotin
 */
public final class UnsafeWrapper {

    public static final UnsafeWrapper INSTANCE = new UnsafeWrapper();

    private static final Class<?> theUnsafeClass = unsafeClass();

    private static final Object theUnsafeInstance = getUnsafeInstance();

    private static final MethodHandle allocateMemoryHandle = allocateMemoryHandle();

    private static final MethodHandle putShortHandle = putShortHandle();

    private static final MethodHandle freeMemoryHandle = freeMemoryHandle();

    private static final MethodHandle getByteHandle = getByteHandle();

    private UnsafeWrapper() {}

    public static UnsafeWrapper getInstance() {
        return INSTANCE;
    }

    public boolean isBigEndian() {
        try {
            long a = (long) allocateMemoryHandle.invoke(theUnsafeInstance, 2);
            try {
                putShortHandle.invoke(theUnsafeInstance, a, (short) 0x10000001);
                byte b = (byte) getByteHandle.invoke(theUnsafeInstance, a);

                switch (b) {
                    case 0x01:
                        return false;
                    case 0x10:
                        return true;
                    default:
                        assert false;
                        return false;
                }
            } finally {
                freeMemoryHandle.invoke(theUnsafeInstance, a);
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
            return Class.forName("jdk.internal.ref.Unsafe");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName("sun.misc.Unsafe");
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    private static MethodHandle methodHandle(String methodName, MethodType methodType) {
        try {
            return MethodHandles.publicLookup().findVirtual(theUnsafeClass, methodName, methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object fieldInstance(String fieldName) {
        try {
            Field f = theUnsafeClass.getDeclaredField(fieldName);
            f.setAccessible(true);

            return f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
