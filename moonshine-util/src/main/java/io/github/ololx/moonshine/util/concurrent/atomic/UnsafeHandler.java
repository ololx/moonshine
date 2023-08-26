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

package io.github.ololx.moonshine.util.concurrent.atomic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * This class is a wrapper around the {@code sun.misc.Unsafe} and
 * {@code jdk.internal.misc.Unsafe} classes. It is used to access certain
 * low-level memory-related operations that are not accessible through standard
 * Java APIs.
 *
 * @implSpec
 * <p>This class relies on the availability of the {@code sun.misc.Unsafe} or
 * {@code jdk.internal.misc.Unsafe} class and the ability to access its
 * internal methods using reflection.</p>
 * <p>The class assumes that the sun.misc.Unsafe class is available and can be
 * loaded via the system class loader.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * UnsafeHelper unsafeHelper = UnsafeHelper.getInstance();
 * boolean isBigEndian = unsafeHelper.isBigEndian();
 * }</pre>
 *
 * project moonshine
 * created 25.08.2023 17:47
 *
 * @author Alexander A. Kropotin
 */
final class UnsafeHandler {

    /**
     * The singleton instance of the UnsafeHelper class.
     */
    public static final UnsafeHandler INSTANCE = new UnsafeHandler();

    /**
     * The class object for sun.misc.Unsafe.
     */
    public static final Class<?> UNSAFE_CLASS = unsafeClass();

    /**
     * The singleton instance of sun.misc.Unsafe.
     */
    public static final Object UNSAFE_INSTANCE = getUnsafeInstance();

    private static final MethodHandle PUT = putByteVolatileHandle();

    private static final MethodHandle GET = getByteVolatileHandle();

    private static final MethodHandle OFFSET = arrayBaseOffsetHandle();

    private static final MethodHandle SCALE = arrayIndexScaleHandle();

    /**
     * Override constructor by defaults (implicit public constructor).
     * Because utility class are not meant to be instantiated.
     */
    private UnsafeHandler() {}

    /**
     * Returns the singleton instance of the UnsafeHelper class.
     *
     * @return the UnsafeHelper instance
     */
    public static UnsafeHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Returns an instance of the Unsafe class, which can be used to perform
     * low-level operations that are not otherwise possible in Java.
     *
     * @return an instance of the Unsafe class
     */
    private static Object getUnsafeInstance() {
        return fieldInstance("theUnsafe");
    }


    /**
     * Returns the Unsafe class.
     *
     * @return the Unsafe class
     * @throws UnsafeHelperException if the Unsafe class cannot be found
     */
    private static Class<?> unsafeClass() throws UnsafeHelperException {
        try {
            return Class.forName("sun.misc.Unsafe");
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName("jdk.internal.misc.Unsafe");
            } catch (ClassNotFoundException e1) {
                throw new UnsafeHelperException(e1);
            }
        }
    }

    /**
     * Returns the value of the specified field of the Unsafe class.
     *
     * @param fieldName the name of the field
     * @return the value of the specified field of the Unsafe class
     * @throws UnsafeHelperException if the specified field cannot be found or
     * accessed
     */
    private static Object fieldInstance(String fieldName) throws UnsafeHelperException {
        try {
            Field f = UNSAFE_CLASS.getDeclaredField(fieldName);
            f.setAccessible(true);
            Object obj = f.get(null);
            f.setAccessible(false);

            return obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new UnsafeHelperException(e);
        }
    }

    public final int arrayBaseOffset(Class<?> clazz) {
        try {
            return (int) OFFSET.invoke(UNSAFE_INSTANCE, clazz);
        } catch (Throwable e) {
            throw new UnsafeHelperException(e);
        }
    }

    static MethodHandle arrayBaseOffsetHandle() {
        return methodHandle("arrayBaseOffset", MethodType.methodType(int.class, Class.class));
    }

    static MethodHandle arrayIndexScaleHandle() {
        return methodHandle("arrayIndexScale", MethodType.methodType(int.class, Class.class));
    }

    public final int arrayIndexScale(Class<?> clazz) {
        try {
            return (int) SCALE.invoke(UNSAFE_INSTANCE, clazz);
        } catch (Throwable e) {
            throw new UnsafeHelperException(e);
        }
    }

    /**
     * Returns a MethodHandle for the getByte method of the Unsafe class.
     *
     * @return a MethodHandle for the getByte method of the Unsafe class
     */
    public final int getByteVolatile(Object obj, long l) {
        try {
            return (int) GET.invoke(UNSAFE_INSTANCE, obj, l);
        } catch (Throwable e) {
            throw new UnsafeHelperException(e);
        }
    }

    static MethodHandle getByteVolatileHandle() {
        return methodHandle(
                "getIntVolatile",
                MethodType.methodType(int.class, Object.class, long.class)
        );
    }

    public final void putByteVolatile(Object obj, long offset, int b) {
        try {
            PUT.invoke(UNSAFE_INSTANCE, obj, offset, b);
        } catch (Throwable e) {
            throw new UnsafeHelperException(e);
        }
    }

    static MethodHandle putByteVolatileHandle() {
        return methodHandle(
                "putIntVolatile",
                MethodType.methodType(void.class, Object.class, long.class, int.class)
        );
    }

    public final boolean compareAndSwapByte(Object obj, long offset, int expect, int update) {
        try {
            MethodHandle methodHandle = methodHandle(
                    "compareAndSwapInt",
                    MethodType.methodType(boolean.class, Object.class, long.class, int.class, int.class)
            );

            return (boolean) methodHandle.invoke(UNSAFE_INSTANCE, obj, offset, expect, update);
        } catch (Throwable e) {
            throw new UnsafeHelperException(e);
        }
    }

    /**
     * Returns a MethodHandle for the specified method of the Unsafe class.
     *
     * @param methodName the name of the method
     * @param methodType the type of the method
     * @return a MethodHandle for the specified method of the Unsafe class
     * @throws UnsafeHelperException if the specified method cannot be found or
     * accessed
     */
    private static MethodHandle methodHandle(String methodName, MethodType methodType) throws UnsafeHelperException {
        try {
            return MethodHandles.lookup().findVirtual(UNSAFE_CLASS, methodName, methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new UnsafeHelperException(e);
        }
    }

    /**
     * The UnsafeHelperException class is a runtime exception that is thrown by
     * the UnsafeHelper class when an error occurs.
     *
     * <p>The class provides constructors for creating an exception with a
     * message, a cause, or both.</p>
     *
     * <p>The class is serializable, and defines a serialVersionUID to ensure
     * compatibility between different versions.</p>
     *
     * @apiNote
     * This class is not meant to be subclassed by clients.
     *
     * @implSpec
     * This class is implemented as a subclass of RuntimeException.
     */
    public static final class UnsafeHelperException extends RuntimeException {

        private static final long serialVersionUID = -1234567890L;

        /**
         * Constructs a new UnsafeHelperException with the specified detail
         * message.
         *
         * @apiNote
         * This constructor is intended to be used when an error occurs in
         * the UnsafeHelper class.
         *
         * @implSpec
         * This constructor throws an instance of UnsafeHelperException with
         * the specified detail message.
         *
         * <p><strong>Example usage:</strong></p>
         * <pre>{@code
         * try {
         *     // Some code that uses UnsafeHelper
         * } catch (Throwable t) {
         *     throw new UnsafeHelperException("Error occurred while using UnsafeHelper", t);
         * }
         * }</pre>
         *
         * @param message the detail message (which is saved for later
         * retrieval by the Throwable.getMessage() method)
         */
        public UnsafeHelperException(String message) {
            super(message);
        }

        /**
         * Constructs a new UnsafeHelperException with the specified cause.
         *
         * @apiNote
         * This constructor is intended to be used when an error occurs in the
         * UnsafeHelper class.
         *
         * @implSpec
         * This constructor throws an instance of UnsafeHelperException with
         * the specified cause.
         *
         * <p><strong>Example usage:</strong></p>
         * <pre>{@code
         * try {
         *     // Some code that uses UnsafeHelper
         * } catch (Throwable t) {
         *     throw new UnsafeHelperException(t);
         * }
         * }</pre>
         *
         * @param cause the cause (which is saved for later retrieval by the
         * Throwable.getCause() method). A null value is allowed and indicates
         * that the cause is nonexistent or unknown.
         */
        public UnsafeHelperException(Throwable cause) {
            super(cause);
        }

        /**
         * Constructs a new UnsafeHelperException with the specified detail
         * message and cause.
         *
         * @apiNote
         * This constructor is intended to be used when an error occurs in the
         * UnsafeHelper class.
         *
         * @implSpec  This constructor throws an instance of
         * UnsafeHelperException with the specified detail message and cause.
         *
         * <p><strong>Example usage:</strong></p>
         * <pre>{@code
         * try {
         *     // Some code that uses UnsafeHelper
         * } catch (Throwable t) {
         *     throw new UnsafeHelperException("Error occurred while using UnsafeHelper", t);
         * }
         * }</pre>
         *
         * @param message the detail message (which is saved for later
         * retrieval by the Throwable.getMessage() method)
         * @param cause the cause (which is saved for later retrieval by the
         * Throwable.getCause() method). A null value is allowed and indicates
         * that the cause is nonexistent or unknown.
         */
        public UnsafeHelperException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
