/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.moonshine.var.access;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Provides low-level memory access and manipulation using the Unsafe class.
 * This class offers methods to perform various memory operations, such as reading
 * and writing primitive values, atomically updating fields, and working with native memory.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 30.08.2023 13:26
 * @implSpec This class uses the sun.misc.Unsafe class to access memory directly. It provides methods
 *     for performing various memory operations on different data types.
 *     The methods provided by this class are similar to those found in the sun.misc.Unsafe class,
 *     which is an internal and unsupported API in the Java platform. Direct use of sun.misc.Unsafe
 *     is discouraged, and developers are advised to use standard Java APIs whenever possible.
 *
 *     Example usages:
 *     <pre>{@code
 *         MemoryAccess memoryAccess = new MemoryAccess();
 *         byte[] byteArray = new byte[2];
 *
 *         // CAS 2d (1t index) element of a byte array
 *         long arrayBaseOffset = memoryAccess.arrayBaseOffset(byte[].class);
 *         byte expectedValue = 0;
 *         byte newValue = 12;
 *         long offset = arrayBaseOffset + 1;
 *         memoryAccess.compareAndSwapInt(byteArray, offset, expectedValue, newValue);
 *         }</pre>
 */
@SuppressWarnings("sunapi")
public final class MemoryAccess {

    /**
     * Constant representing the endianness of the system.
     */
    public static final Endianness SYSTEM_ENDIANNESS;

    /**
     * The instance of the Unsafe class obtained through reflection.
     */
    private static final Unsafe unsafe;

    static {
        // Retrieve the Unsafe class using reflection
        Class<?> unsafeClass = getUnsafeClass();
        // Obtain an instance of Unsafe using reflection
        unsafe = getUnsafeInstanceForClass(unsafeClass);
        // Determine the endianness of the underlying platform
        SYSTEM_ENDIANNESS = getSystemEndianness();
    }

    /**
     * Retrieves the Unsafe class using reflection.
     *
     * @return The Unsafe class.
     *
     * @throws Error if the Unsafe class cannot be found using reflection.
     * @implSpec This method uses Java reflection to obtain the `sun.misc.Unsafe` class instance.
     *     Reflection can have performance overhead and might not work in all environments.
     *     Additionally, access to the `sun.misc.Unsafe` class might be restricted in certain Java
     *     environments, which can lead to exceptions or errors when using this method.
     */
    private static Class<?> getUnsafeClass() {
        try {
            return Class.forName("sun.misc.Unsafe");
        } catch (ClassNotFoundException e) {
            throw new Error("Can't find the sun.misc.Unsafe", e);
        }
    }

    /**
     * Retrieves an instance of Unsafe using reflection.
     *
     * @param unsafeClass The Unsafe class.
     *
     * @return An instance of Unsafe.
     *
     * @throws Error if an instance of Unsafe cannot be obtained.
     * @implSpec This method uses reflection to access the private field 'theUnsafe'
     *     within the Unsafe class. It sets the field as accessible, retrieves the
     *     field value, and then resets the field accessibility to its original state.
     */
    private static Unsafe getUnsafeInstanceForClass(Class<?> unsafeClass) {
        try {
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Object obj = f.get(null);
            f.setAccessible(false);

            return (Unsafe) obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error("Can't get the Unsafe instance from sun.misc.Unsafe.theUnsafe", e);
        }
    }

    /**
     * Determines whether the underlying platform uses <b>big-endian</b> or
     * <b>little-endian</b> byte order.
     *
     * @return {@link MemoryAccess.Endianness#LE} if the platform uses little-endian byte order,
     *     {@link MemoryAccess.Endianness#BE} if the platform uses big-endian byte order.
     *
     * @throws Error if the byte order was be determined like middle-endian.
     * @throws Error if the byte order cannot be determined.
     * @implSpec This method uses a byte-order probe to determine whether the underlying
     *     platform uses big-endian or little-endian byte order. It allocates a 4-byte buffer
     *     using the {@code allocateMemory} method, writes the value
     *     {@code 0x04030201} to the buffer using the {@code putInt} method,
     *     reads the first byte of the buffer using the {@code getByte} method, and
     *     then deallocates the buffer using the {@code freeMemory} method.
     *     If the first byte of the buffer is {@code 0x01}, the platform uses
     *     little-endian byte order; if it is {@code 0x04}, the platform uses
     *     big-endian byte order.
     */
    private static Endianness getSystemEndianness() {
        long offset = unsafe.allocateMemory(4);
        unsafe.putInt(offset, 0x04030201);
        byte lsb = unsafe.getByte(offset);
        unsafe.freeMemory(offset);

        switch (lsb) {
            case 0x01:
                return Endianness.LE;
            case 0x04:
                return Endianness.BE;
            case 0x02:
                throw new Error("The middle-endian is an unexpected byte order");
            case 0x03:
            default:
                throw new Error("Failed to determine byte order");
        }
    }

    /**
     * Returns the base offset of the provided array class.
     *
     * @param arrayClass The class of the array.
     *
     * @return The base offset of the array class.
     *
     * @implSpec This method returns the base offset that should be added to the index of an element in the array
     *     to access the actual memory location of the element. The base offset is platform-dependent and may
     *     vary between different array classes.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Get the base offset of a byte array
     *         int baseOffset = memoryAccess.arrayBaseOffset(byte[].class);
     *         System.out.println("Base Offset: " + baseOffset);
     *         }</pre>
     */
    public final int arrayBaseOffset(final Class<?> arrayClass) {
        return unsafe.arrayBaseOffset(arrayClass);
    }

    /**
     * Returns the offset of the specified field of the given class.
     *
     * @param objectClass The class in which to find the field.
     * @param fieldName   The name of the field whose offset should be obtained.
     *
     * @return The offset of the field.
     *
     * @throws IllegalArgumentException if the field with the specified name does not exist in the class.
     * @implSpec This method uses {@link sun.misc.Unsafe#objectFieldOffset(java.lang.reflect.Field)} to obtain the offset of the field with
     *     the specified name in the given class. The offset can be used to access the field's value in objects of the
     *     class. If a field with the specified name does not exist, a {@link NoSuchFieldException} will be thrown,
     *     wrapped in an {@link IllegalArgumentException}.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     MemoryAccess memoryAccess = new MemoryAccess();
     *
     *     // Get the field offset of a specific field in a class
     *     long fieldOffset = memoryAccess.objectFieldOffset(MyClass.class, "myField");
     *     System.out.println("Field Offset: " + fieldOffset);
     *     }</pre>
     */
    public final long objectFieldOffset(final Class<?> objectClass, String fieldName) {
        try {
            return unsafe.objectFieldOffset(objectClass.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(String.format("No such field %s.%s", fieldName, objectClass), e);
        }
    }

    /**
     * Returns the index scale of the provided array class.
     *
     * @param arrayClass The class of the array.
     *
     * @return The index scale of the array class.
     *
     * @implSpec This method returns the index scale, which is the factor that should be multiplied with the index
     *     to compute the byte offset between elements in the array. The index scale is platform-dependent and
     *     may vary between different array classes.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Get the index scale of an int array
     *         int indexScale = memoryAccess.arrayIndexScale(int[].class);
     *         System.out.println("Index Scale: " + indexScale);
     *         }</pre>
     */
    public final int arrayIndexScale(final Class<?> arrayClass) {
        return unsafe.arrayIndexScale(arrayClass);
    }

    /**
     * Returns the value of a byte at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the byte
     * @param offset the memory offset at which to read the byte
     *
     * @return the byte value read from the specified memory offset
     *
     * @implSpec This method reads a byte value from the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is accessible and
     *     contains a valid byte value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Reading a byte from a memory location
     *         Object obj = ...; // The object containing the memory location
     *         long offset = ...; // The memory offset
     *         byte value = memoryAccess.getByte(obj, offset);
     *         }</pre>
     */
    public final byte getByte(final Object obj, final long offset) {
        return unsafe.getByte(obj, offset);
    }

    /**
     * Writes a byte value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the byte
     * @param offset the memory offset at which to write the byte
     * @param value  the byte value to write
     *
     * @implSpec This method writes a byte value to the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is writable.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Writing a byte to a memory location
     *         Object obj = ...; // The object containing the memory location
     *         long offset = ...; // The memory offset
     *         byte value = ...; // The byte value to write
     *         memoryAccess.putByte(obj, offset, value);
     *         }</pre>
     */
    public final void putByte(final Object obj, final long offset, final byte value) {
        unsafe.putByte(obj, offset, value);
    }

    /**
     * Returns the value of an int at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the int
     * @param offset the memory offset at which to read the int
     *
     * @return the int value read from the specified memory offset
     *
     * @implSpec This method reads an int value from the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is accessible and
     *     contains a valid int value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Reading an int from a memory location
     *         Object obj = ...; // The object containing the memory location
     *         long offset = ...; // The memory offset
     *         int value = memoryAccess.getInt(obj, offset);
     *         }</pre>
     */
    public final int getInt(final Object obj, final long offset) {
        return unsafe.getInt(obj, offset);
    }

    /**
     * Writes an int value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the int
     * @param offset the memory offset at which to write the int
     * @param value  the int value to write
     *
     * @implSpec This method writes an int value to the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is writable.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Writing an int to a memory location
     *         Object obj = ...; // The object containing the memory location
     *         long offset = ...; // The memory offset
     *         int value = ...; // The int value to write
     *         memoryAccess.putInt(obj, offset, value);
     *         }</pre>
     */
    public final void putInt(final Object obj, final long offset, final int value) {
        unsafe.putInt(obj, offset, value);
    }

    /**
     * Writes a volatile byte value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile byte
     * @param offset the memory offset at which to write the volatile byte
     * @param value  the volatile byte value to write
     *
     * @implSpec This method writes the specified volatile byte value to the memory location at the offset.
     *     The volatile modifier ensures that the write operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object to which to write the volatile byte
     *         long offset = ...; // The memory offset at which to write the volatile byte
     *         byte value = ...; // The volatile byte value to write
     *         memoryAccess.putByteVolatile(obj, offset, value);
     *         }</pre>
     */
    public final void putByteVolatile(final Object obj, final long offset, final byte value) {
        unsafe.putByteVolatile(obj, offset, value);
    }

    /**
     * Writes a volatile int value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile int
     * @param offset the memory offset at which to write the volatile int
     * @param value  the volatile int value to write
     *
     * @implSpec This method writes the specified volatile int value to the memory location at the offset.
     *     The volatile modifier ensures that the write operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object to which to write the volatile int
     *         long offset = ...; // The memory offset at which to write the volatile int
     *         int value = ...; // The volatile int value to write
     *         memoryAccess.putIntVolatile(obj, offset, value);
     *         }</pre>
     */
    public final void putIntVolatile(final Object obj, final long offset, final int value) {
        unsafe.putIntVolatile(obj, offset, value);
    }

    /**
     * Atomically adds the given value to the byte field at the specified memory offset
     * of the provided object and returns the previous value of the byte field.
     *
     * @param obj    the object containing the byte field
     * @param offset the memory offset of the byte field
     * @param delta  the value to add to the byte field
     *
     * @return the previous value of the byte field before the addition
     *
     * @implSpec This method atomically adds the given value to the byte field at the specified
     *     offset and returns the previous value of the byte field before the addition. The operation
     *     ensures that the byte field is updated atomically to avoid race conditions.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the byte field
     *         long offset = ...; // The memory offset of the byte field
     *         byte delta = ...; // The value to add to the byte field
     *         byte previousValue = memoryAccess.getAndAddByte(obj, offset, delta);
     *         }</pre>
     */
    public final byte getAndAddByte(final Object obj, final long offset, final byte delta) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = (byte) ((expected + delta) & 0xFF);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Returns the value of the volatile byte field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile byte
     * @param offset the memory offset at which to read the volatile byte
     *
     * @return the volatile byte value read from the specified memory offset
     *
     * @implSpec This method reads the value of the volatile byte field at the specified offset.
     *     The volatile modifier ensures that the read operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the volatile byte field
     *         long offset = ...; // The memory offset of the volatile byte field
     *         byte value = memoryAccess.getByteVolatile(obj, offset);
     *         }</pre>
     */
    public final byte getByteVolatile(final Object obj, final long offset) {
        return unsafe.getByteVolatile(obj, offset);
    }

    /**
     * Atomically compares the byte field at the specified memory offset of the provided object
     * with the expected value, and if they are equal, updates the field to the new value.
     *
     * @param obj      the object containing the byte field
     * @param offset   the memory offset of the byte field
     * @param expected the expected value of the byte field
     * @param newValue the new value to set the byte field to
     *
     * @return {@code true} if the comparison and update were successful, {@code false} otherwise
     *
     * @implSpec This method atomically compares the byte field at the specified offset to the expected value.
     *     If the current value matches the expected value, the method updates the byte field to the new value
     *     and returns {@code true}. Otherwise, it returns {@code false}.
     *     This operation is useful for performing atomic updates on shared byte variables.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the byte field
     *         long offset = ...; // The memory offset of the byte field
     *         byte expected = ...; // The expected value of the byte field
     *         byte newValue = ...; // The new value to set the byte field to
     *         boolean success = memoryAccess.compareAndSwapByte(obj, offset, expected, newValue);
     *         }</pre>
     */
    public final boolean compareAndSwapByte(final Object obj,
                                            final long offset,
                                            final byte expected,
                                            final byte newValue) {
        long intWordOffset = offset & ~3;

        int byteWordShift = (int) (offset & 3) << 3;
        if (endianness().isBigEndian()) {
            byteWordShift = 24 - byteWordShift;
        }

        int byteWordMask = 0xFF << byteWordShift;
        int maskedUpdate = (newValue & 0xFF) << byteWordShift;

        int oldIntWordValue;
        int newIntWordValue;

        do {
            oldIntWordValue = getIntVolatile(obj, intWordOffset);

            if ((byte) ((oldIntWordValue >> byteWordShift) & 0xFF) != expected) {
                return false;
            }

            newIntWordValue = (oldIntWordValue & ~byteWordMask) | maskedUpdate;
        } while (!compareAndSwapInt(obj, intWordOffset, oldIntWordValue, newIntWordValue));

        return true;
    }

    /**
     * Retrieves the endianness of the system.
     *
     * @return The endianness of the system, indicating how multibyte data is stored.
     *
     * @implSpec This method returns a predefined constant representing the endianness of the system,
     *     which is determined during the initialization of the application.
     *     The actual endianness of the system hardware is unlikely to change while the application is running.
     *     Endianness refers to the byte order in which multi-byte data types are stored in memory.
     *     It can be either {@link MemoryAccess.Endianness#BE} (most significant byte first) or
     *     {@link MemoryAccess.Endianness#LE} (least significant byte first).
     *     This information can be crucial when working with binary data formats that are shared
     *     between systems with different endianness.
     * @see MemoryAccess.Endianness
     * @see <a href="https://en.wikipedia.org/wiki/Endianness">Endianness</a>
     *
     *     Example usages:
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Getting the system endianness
     *         Endianness systemEndianness = memoryAccess.endianness();
     *         if (systemEndianness.isBigEndian()) {
     *             System.out.println("System uses big-endian.");
     *         } else {
     *             System.out.println("System uses little-endian.");
     *         }
     *         }</pre>
     */
    public final Endianness endianness() {
        return SYSTEM_ENDIANNESS;
    }

    /**
     * Returns the value of the volatile int field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile int
     * @param offset the memory offset at which to read the volatile int
     *
     * @return the volatile int value read from the specified memory offset
     *
     * @implSpec This method reads the value of the volatile int field at the specified offset.
     *     The volatile modifier ensures that the read operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the volatile int field
     *         long offset = ...; // The memory offset of the volatile int field
     *         int value = memoryAccess.getIntVolatile(obj, offset);
     *         }</pre>
     */
    public final int getIntVolatile(final Object obj, final long offset) {
        return unsafe.getIntVolatile(obj, offset);
    }

    /**
     * Atomically compares the int field at the specified memory offset of the provided object
     * with the expected value, and if they are equal, updates the field to the new value.
     *
     * @param obj      the object containing the int field
     * @param offset   the memory offset of the int field
     * @param expected the expected value of the int field
     * @param newValue the new value to set the int field to
     *
     * @return {@code true} if the comparison and update were successful, {@code false} otherwise
     *
     * @implSpec This method atomically compares the value of the int field at the specified offset with
     *     the expected value. If they are equal, the int field is updated with the new value.
     *     This operation is performed atomically and is useful for managing concurrent updates to shared fields.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the int field
     *         long offset = ...; // The memory offset of the int field
     *         int expected = ...; // The expected value of the int field
     *         int newValue = ...; // The new value to set the int field to
     *         boolean success = memoryAccess.compareAndSwapInt(obj, offset, expected, newValue);
     *         }</pre>
     */
    public final boolean compareAndSwapInt(final Object obj,
                                           final long offset,
                                           final int expected,
                                           final int newValue) {
        return unsafe.compareAndSwapInt(obj, offset, expected, newValue);
    }

    /**
     * Atomically adds the given value to the int field at the specified memory offset
     * of the provided object.
     *
     * @param obj    the object containing the int field
     * @param offset the memory offset of the int field
     * @param delta  the value to add to the int field
     *
     * @return the previous value of the int field before the addition
     *
     * @implSpec This method atomically adds the given value to the int field at the specified offset.
     *     The addition is performed atomically, and the method returns the previous value of the int field before the
     *     addition.
     *     This operation is useful for performing atomic updates on shared integer variables.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the int field
     *         long offset = ...; // The memory offset of the int field
     *         int delta = ...; // The value to add to the int field
     *         int previousValue = memoryAccess.getAndAddInt(obj, offset, delta);
     *         }</pre>
     */
    public final int getAndAddInt(final Object obj, final long offset, final int delta) {
        return unsafe.getAndAddInt(obj, offset, delta);
    }

    /**
     * Atomically sets the byte field at the specified memory offset of the provided object
     * to the given new value and returns the previous value of the byte field.
     *
     * @param obj      the object containing the byte field
     * @param offset   the memory offset of the byte field
     * @param newValue the new value to set the byte field to
     *
     * @return the previous value of the byte field before the update
     *
     * @implSpec This method atomically sets the byte field at the specified offset to the new value
     *     and returns the previous value of the byte field before the update. The operation ensures that
     *     the byte field is updated atomically to avoid race conditions.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the byte field
     *         long offset = ...; // The memory offset of the byte field
     *         byte newValue = ...; // The new value to set the byte field to
     *         byte previousValue = memoryAccess.getAndSetByte(obj, offset, newValue);
     *         }</pre>
     */
    public final byte getAndSetByte(final Object obj, final long offset, final byte newValue) {
        byte expected;

        do {
            expected = getByteVolatile(obj, offset);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Atomically sets the int field at the specified memory offset of the provided
     * object to the given new value.
     *
     * @param obj      the object containing the int field
     * @param offset   the memory offset of the int field
     * @param newValue the new value to set the int field to
     *
     * @return the previous value of the int field before the update
     *
     * @implSpec This method atomically sets the int field at the specified offset to the given new value.
     *     The method returns the previous value of the int field before the update.
     *     This operation is useful for performing atomic updates on shared integer variables.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         Object obj = ...; // The object containing the int field
     *         long offset = ...; // The memory offset of the int field
     *         int newValue = ...; // The new value to set the int field to
     *         int previousValue = memoryAccess.getAndSetInt(obj, offset, newValue);
     *         }</pre>
     */
    public final int getAndSetInt(final Object obj, final long offset, final int newValue) {
        return unsafe.getAndSetInt(obj, offset, newValue);
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteUnaryFunction} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @param obj      the object containing the byte value
     * @param offset   the memory offset of the byte value
     * @param updating the function to apply to the byte value
     *
     * @return the old byte value that was replaced
     *
     * @implSpec This method atomically applies the provided update function to the byte value at the specified
     *     memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Define a byte update function that increments the value by 1
     *         ByteUnaryOperator incrementByOne = value -> (byte) (value + 1);
     *
     *         // Get the current byte value at the offset and update it atomically
     *         byte oldValue = memoryAccess.getAndUpdateByte(myObject, 0, incrementByOne);
     *
     *         // 'oldValue' contains the previous value, and the value at offset is incremented by 1.
     *         }</pre>
     */
    public final byte getAndUpdateByte(final Object obj, final long offset, final ByteUnaryFunction updating) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = updating.apply(expected);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteUnaryFunction} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @param obj      the object containing the byte value
     * @param offset   the memory offset of the byte value
     * @param updating the function to apply to the byte value
     *
     * @return the new byte value
     *
     * @implSpec This method atomically applies the provided update function to the byte value at the specified
     *     memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Define a byte update function that doubles the value
     *         ByteUnaryOperator doubleValue = value -> (byte) (value * 2);
     *
     *         // Get the current byte value at the offset, double it, and update it atomically
     *         byte newValue = memoryAccess.updateAndGetByte(myObject, 0, doubleValue);
     *
     *         // 'newValue' contains the updated value.
     *         }</pre>
     */
    public final byte updateAndGetByte(final Object obj, final long offset, final ByteUnaryFunction updating) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = updating.apply(expected);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return newValue;
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteBinaryFunction} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @param obj          the object containing the byte value
     * @param offset       the memory offset of the byte value
     * @param update       the value to combine with the byte value
     * @param accumulation the function to apply to the byte value and the update value
     *
     * @return the old byte value that was replaced
     *
     * @implSpec This method atomically applies the provided accumulator function to the byte value at the
     *     specified memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Define a byte binary operator that adds the update value to the byte value
     *         ByteBinaryOperator addValue = (currentValue, update) -> (byte) (currentValue + update);
     *
     *         // Get the current byte value at the offset, add 10 to it, and update it atomically
     *         byte oldValue = memoryAccess.getAndAccumulateByte(myObject, 0, (byte) 10, addValue);
     *
     *         // 'oldValue' contains the previous value, and the value at offset is incremented by 10.
     *         }</pre>
     */
    public final byte getAndAccumulateByte(final Object obj,
                                           final long offset,
                                           final byte update,
                                           final ByteBinaryFunction accumulation) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = accumulation.apply(expected, update);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteBinaryFunction} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @param obj          the object containing the byte value
     * @param offset       the memory offset of the byte value
     * @param update       the value to combine with the byte value
     * @param accumulation the function to apply to the byte value and the update value
     *
     * @return the new byte value
     *
     * @implSpec This method atomically applies the provided accumulator function to the byte value at the
     *     specified memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         MemoryAccess memoryAccess = new MemoryAccess();
     *
     *         // Define a byte binary operator that subtracts the update value from the byte value
     *         ByteBinaryOperator subtractValue = (currentValue, update) -> (byte) (currentValue - update);
     *
     *         // Get the current byte value at the offset, subtract 5 from it, and update it atomically
     *         byte newValue = memoryAccess.accumulateAndGetByte(myObject, 0, (byte) 5, subtractValue);
     *
     *         // 'newValue' contains the updated value.
     *         }</pre>
     */
    public final byte accumulateAndGetByte(final Object obj,
                                           final long offset,
                                           final byte update,
                                           final ByteBinaryFunction accumulation) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = accumulation.apply(expected, update);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return newValue;
    }

    /**
     * Enumeration representing different byte orders (endianness).
     */
    public enum Endianness {
        /**
         * Little-endian byte order.
         */
        LE,

        /**
         * Big-endian byte order.
         */
        BE;

        /**
         * Checks if the byte order is big-endian.
         *
         * @return true if big-endian, false otherwise.
         *
         * @implSpec This method checks if the current byte order enum constant is equal to {@link #BE},
         *     indicating big-endian byte order.
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *         Endianness endianness = Endianness.BE;
         *         boolean isBigEndian = endianness.isBigEndian();
         *         System.out.println("Is Big Endian: " + isBigEndian); // Output: true
         *         }</pre>
         */
        public boolean isBigEndian() {
            return this.equals(BE);
        }

        /**
         * Checks if the byte order is little-endian.
         *
         * @return true if little-endian, false otherwise.
         *
         * @implSpec This method checks if the current byte order enum constant is equal to {@link #LE},
         *     indicating little-endian byte order.
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *         Endianness endianness = Endianness.LE;
         *         boolean isLittleEndian = endianness.isLittleEndian();
         *         System.out.println("Is Little Endian: " + isLittleEndian); // Output: true
         *         }</pre>
         */
        public boolean isLittleEndian() {
            return this.equals(LE);
        }
    }
}
