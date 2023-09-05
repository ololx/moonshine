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

package io.github.ololx.moonshine.util.concurrent.atomic;

import io.github.ololx.moonshine.util.function.ByteBinaryOperator;
import io.github.ololx.moonshine.util.function.ByteUnaryOperator;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Provides low-level memory access and manipulation using the Unsafe class.
 * This class offers methods to perform various memory operations, such as reading
 * and writing primitive values, atomically updating fields, and working with native memory.
 *
 * @implSpec This class uses the sun.misc.Unsafe class to access memory directly. It provides methods
 *     for performing various memory operations on different data types.
 *     The methods provided by this class are similar to those found in the sun.misc.Unsafe class,
 *     which is an internal and unsupported API in the Java platform. Direct use of sun.misc.Unsafe
 *     is discouraged, and developers are advised to use standard Java APIs whenever possible.
 *
 *     Example usages:
 *     <pre>{@code
 *     AtomicAccess memoryAccess = new AtomicAccess();
 *     byte[] byteArray = new byte[2];
 *
 *     // CAS 2d (1t index) element of a byte array
 *     long arrayBaseOffset = memoryAccess.arrayBaseOffset(byte[].class);
 *     byte expectedValue = 0;
 *     byte newValue = 12;
 *     long offset = arrayBaseOffset + 1;
 *     memoryAccess.compareAndSwapInt(byteArray, offset, expectedValue, newValue);
 *     }</pre>
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 30.08.2023 13:26
 */
@SuppressWarnings("sunapi")
class AtomicAccess {

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
         * @implSpec This method checks if the current byte order enum constant is equal to {@link #BE},
         *     indicating big-endian byte order.
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     Endianness endianness = Endianness.BE;
         *     boolean isBigEndian = endianness.isBigEndian();
         *     System.out.println("Is Big Endian: " + isBigEndian); // Output: true
         *     }</pre>
         *
         * @return true if big-endian, false otherwise.
         */
        public boolean isBigEndian() {
            return this.equals(BE);
        }

        /**
         * Checks if the byte order is little-endian.
         *
         * @implSpec This method checks if the current byte order enum constant is equal to {@link #LE},
         *     indicating little-endian byte order.
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     Endianness endianness = Endianness.LE;
         *     boolean isLittleEndian = endianness.isLittleEndian();
         *     System.out.println("Is Little Endian: " + isLittleEndian); // Output: true
         *     }</pre>
         *
         * @return true if little-endian, false otherwise.
         */
        public boolean isLittleEndian() {
            return this.equals(LE);
        }
    }

    /**
     * Constant representing the endianness of the system.
     */
    public static final Endianness ENDIANNESS;

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
        ENDIANNESS = getEndianness();
    }

    private AtomicAccess() {}

    /**
     * Retrieves the Unsafe class using reflection.
     *
     * @implSpec This method uses Java reflection to obtain the `sun.misc.Unsafe` class instance.
     *     Reflection can have performance overhead and might not work in all environments.
     *     Additionally, access to the `sun.misc.Unsafe` class might be restricted in certain Java
     *     environments, which can lead to exceptions or errors when using this method.
     *
     * @return The Unsafe class.
     *
     * @throws RuntimeException if the Unsafe class cannot be found.
     * @throws RuntimeException if the Unsafe class cannot be found using reflection.
     */
    private static Class<?> getUnsafeClass() throws RuntimeException {
        try {
            return Class.forName("sun.misc.Unsafe");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves an instance of Unsafe using reflection.
     *
     * @implSpec This method uses reflection to access the private field 'theUnsafe'
     *     within the Unsafe class. It sets the field as accessible, retrieves the
     *     field value, and then resets the field accessibility to its original state.
     *
     * @param unsafeClass The Unsafe class.
     *
     * @return An instance of Unsafe.
     *
     * @throws RuntimeException if an instance of Unsafe cannot be obtained.
     */
    private static Unsafe getUnsafeInstanceForClass(Class<?> unsafeClass) throws RuntimeException {
        try {
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Object obj = f.get(null);
            f.setAccessible(false);

            return (Unsafe) obj;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines whether the underlying platform uses <b>big-endian</b> or
     * <b>little-endian</b> byte order.
     *
     * @implSpec This method uses a byte-order probe to determine whether the underlying
     *     platform uses big-endian or little-endian byte order. It allocates a 4-byte buffer
     *     using the {@code allocateMemory} method, writes the value
     *     {@code 0x04030201} to the buffer using the {@code putInt} method,
     *     reads the first byte of the buffer using the {@code getByte} method, and
     *     then deallocates the buffer using the {@code freeMemory} method.
     *     If the first byte of the buffer is {@code 0x01}, the platform uses
     *     little-endian byte order; if it is {@code 0x04}, the platform uses
     *     big-endian byte order.
     *
     * @return {@link Endianness#LE} if the platform uses little-endian byte order,
     *     {@link Endianness#BE} if the platform uses big-endian byte order.
     *
     * @throws RuntimeException if the byte order cannot be determined.
     */
    private static Endianness getEndianness() {
        long offset = unsafe.allocateMemory(4);
        unsafe.putInt(offset, 0x04030201);
        byte lsb = (byte) unsafe.getByte(offset);
        unsafe.freeMemory(offset);

        switch (lsb) {
            case 0x01:
                return Endianness.LE;
            case 0x04:
                return Endianness.BE;
            case 0x02:
                throw new RuntimeException("The middle-endian is an unexpected byte order");
            case 0x03:
            default:
                throw new RuntimeException("Failed to determine byte order");
        }
    }

    /**
     * Retrieves the endianness of the system.
     *
     * @implSpec  This method returns a predefined constant representing the endianness of the system,
     *     which is determined during the initialization of the application.
     *     The actual endianness of the system hardware is unlikely to change while the application is running.
     *     Endianness refers to the byte order in which multi-byte data types are stored in memory.
     *     It can be either {@link Endianness#BE} (most significant byte first) or
     *     {@link Endianness#LE} (least significant byte first).
     *     This information can be crucial when working with binary data formats that are shared
     *     between systems with different endianness.
     *
     * @see Endianness
     * @see <a href="https://en.wikipedia.org/wiki/Endianness">Endianness</a>
     *
     *     Example usages:
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Getting the system endianness
     *     Endianness systemEndianness = memoryAccess.endianness();
     *     if (systemEndianness.isBigEndian()) {
     *         System.out.println("System uses big-endian.");
     *     } else {
     *         System.out.println("System uses little-endian.");
     *     }
     *     }</pre>
     *
     * @return The endianness of the system, indicating how multibyte data is stored.
     */
    public final Endianness endianness() {
        return ENDIANNESS;
    }

    /**
     * Returns the base offset of the provided array class.
     *
     * @implSpec This method returns the base offset that should be added to the index of an element in the array
     *     to access the actual memory location of the element. The base offset is platform-dependent and may
     *     vary between different array classes.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Get the base offset of a byte array
     *     int baseOffset = memoryAccess.arrayBaseOffset(byte[].class);
     *     System.out.println("Base Offset: " + baseOffset);
     *     }</pre>
     *
     * @param arrayClass The class of the array.
     *
     * @return The base offset of the array class.
     */
    public final int arrayBaseOffset(final Class<?> arrayClass) {
        return unsafe.arrayBaseOffset(arrayClass);
    }

    /**
     * Returns the index scale of the provided array class.
     *
     * @implSpec This method returns the index scale, which is the factor that should be multiplied with the index
     *     to compute the byte offset between elements in the array. The index scale is platform-dependent and
     *     may vary between different array classes.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Get the index scale of an int array
     *     int indexScale = memoryAccess.arrayIndexScale(int[].class);
     *     System.out.println("Index Scale: " + indexScale);
     *     }</pre>
     *
     * @param arrayClass The class of the array.
     *
     * @return The index scale of the array class.
     */
    public final int arrayIndexScale(final Class<?> arrayClass) {
        return unsafe.arrayIndexScale(arrayClass);
    }

    /**
     * Returns the value of a byte at the specified memory offset of the given object.
     *
     * @implSpec This method reads a byte value from the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is accessible and
     *     contains a valid byte value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Reading a byte from a memory location
     *     Object obj = ...; // The object containing the memory location
     *     long offset = ...; // The memory offset
     *     byte value = memoryAccess.getByte(obj, offset);
     *     }</pre>
     *
     * @param obj    the object from which to read the byte
     * @param offset the memory offset at which to read the byte
     *
     * @return the byte value read from the specified memory offset
     */
    public final byte getByte(final Object obj, final long offset) {
        return unsafe.getByte(obj, offset);
    }

    /**
     * Writes a byte value to the specified memory offset of the given object.
     *
     * @implSpec This method writes a byte value to the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is writable.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Writing a byte to a memory location
     *     Object obj = ...; // The object containing the memory location
     *     long offset = ...; // The memory offset
     *     byte value = ...; // The byte value to write
     *     memoryAccess.putByte(obj, offset, value);
     *     }</pre>
     *
     * @param obj    the object to which to write the byte
     * @param offset the memory offset at which to write the byte
     * @param value  the byte value to write
     */
    public final void putByte(final Object obj, final long offset, final byte value) {
        unsafe.putByte(obj, offset, value);
    }

    /**
     * Returns the value of an int at the specified memory offset of the given object.
     *
     * @implSpec This method reads an int value from the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is accessible and
     *     contains a valid int value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Reading an int from a memory location
     *     Object obj = ...; // The object containing the memory location
     *     long offset = ...; // The memory offset
     *     int value = memoryAccess.getInt(obj, offset);
     *     }</pre>
     *
     * @param obj    the object from which to read the int
     * @param offset the memory offset at which to read the int
     *
     * @return the int value read from the specified memory offset
     */
    public final int getInt(final Object obj, final long offset) {
        return unsafe.getInt(obj, offset);
    }

    /**
     * Writes an int value to the specified memory offset of the given object.
     *
     * @implSpec This method writes an int value to the memory location specified by the object and offset.
     *     It is the caller's responsibility to ensure that the memory at the specified offset is writable.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Writing an int to a memory location
     *     Object obj = ...; // The object containing the memory location
     *     long offset = ...; // The memory offset
     *     int value = ...; // The int value to write
     *     memoryAccess.putInt(obj, offset, value);
     *     }</pre>
     *
     * @param obj    the object to which to write the int
     * @param offset the memory offset at which to write the int
     * @param value  the int value to write
     */
    public final void putInt(final Object obj, final long offset, final int value) {
        unsafe.putInt(obj, offset, value);
    }

    /**
     * Atomically compares the byte field at the specified memory offset of the provided object
     * with the expected value, and if they are equal, updates the field to the new value.
     *
     * @implSpec This method atomically compares the byte field at the specified offset to the expected value.
     *     If the current value matches the expected value, the method updates the byte field to the new value
     *     and returns {@code true}. Otherwise, it returns {@code false}.
     *     This operation is useful for performing atomic updates on shared byte variables.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the byte field
     *     long offset = ...; // The memory offset of the byte field
     *     byte expected = ...; // The expected value of the byte field
     *     byte newValue = ...; // The new value to set the byte field to
     *     boolean success = memoryAccess.compareAndSwapByte(obj, offset, expected, newValue);
     *     }</pre>
     *
     * @param obj      the object containing the byte field
     * @param offset   the memory offset of the byte field
     * @param expected the expected value of the byte field
     * @param newValue the new value to set the byte field to
     *
     * @return {@code true} if the comparison and update were successful, {@code false} otherwise
     */
    public boolean compareAndSwapByte(Object obj, long offset, byte expected, byte newValue) {
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
     * Atomically compares the int field at the specified memory offset of the provided object
     * with the expected value, and if they are equal, updates the field to the new value.
     *
     * @implSpec This method atomically compares the value of the int field at the specified offset with
     *     the expected value. If they are equal, the int field is updated with the new value.
     *     This operation is performed atomically and is useful for managing concurrent updates to shared fields.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the int field
     *     long offset = ...; // The memory offset of the int field
     *     int expected = ...; // The expected value of the int field
     *     int newValue = ...; // The new value to set the int field to
     *     boolean success = memoryAccess.compareAndSwapInt(obj, offset, expected, newValue);
     *     }</pre>
     *
     * @param obj      the object containing the int field
     * @param offset   the memory offset of the int field
     * @param expected the expected value of the int field
     * @param newValue the new value to set the int field to
     *
     * @return {@code true} if the comparison and update were successful, {@code false} otherwise
     */
    public boolean compareAndSwapInt(Object obj, long offset, int expected, int newValue) {
        return unsafe.compareAndSwapInt(obj, offset, expected, newValue);
    }

    /**
     * Returns the value of the volatile byte field at the specified memory offset of the provided object.
     *
     * @implSpec This method reads the value of the volatile byte field at the specified offset.
     *     The volatile modifier ensures that the read operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the volatile byte field
     *     long offset = ...; // The memory offset of the volatile byte field
     *     byte value = memoryAccess.getByteVolatile(obj, offset);
     *     }</pre>
     *
     * @param obj    the object from which to read the volatile byte
     * @param offset the memory offset at which to read the volatile byte
     *
     * @return the volatile byte value read from the specified memory offset
     */
    public byte getByteVolatile(Object obj, long offset) {
        return unsafe.getByteVolatile(obj, offset);
    }

    /**
     * Writes a volatile byte value to the specified memory offset of the given object.
     *
     * @implSpec This method writes the specified volatile byte value to the memory location at the offset.
     *     The volatile modifier ensures that the write operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object to which to write the volatile byte
     *     long offset = ...; // The memory offset at which to write the volatile byte
     *     byte value = ...; // The volatile byte value to write
     *     memoryAccess.putByteVolatile(obj, offset, value);
     *     }</pre>
     *
     * @param obj    the object to which to write the volatile byte
     * @param offset the memory offset at which to write the volatile byte
     * @param value  the volatile byte value to write
     */
    public void putByteVolatile(Object obj, long offset, byte value) {
        unsafe.putByteVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile int field at the specified memory offset of the provided object.
     *
     * @implSpec This method reads the value of the volatile int field at the specified offset.
     *     The volatile modifier ensures that the read operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the volatile int field
     *     long offset = ...; // The memory offset of the volatile int field
     *     int value = memoryAccess.getIntVolatile(obj, offset);
     *     }</pre>
     *
     * @param obj    the object from which to read the volatile int
     * @param offset the memory offset at which to read the volatile int
     *
     * @return the volatile int value read from the specified memory offset
     */
    public int getIntVolatile(Object obj, long offset) {
        return unsafe.getIntVolatile(obj, offset);
    }

    /**
     * Writes a volatile int value to the specified memory offset of the given object.
     *
     * @implSpec This method writes the specified volatile int value to the memory location at the offset.
     *     The volatile modifier ensures that the write operation is not optimized or reordered by the compiler,
     *     making it suitable for use in concurrent programming.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object to which to write the volatile int
     *     long offset = ...; // The memory offset at which to write the volatile int
     *     int value = ...; // The volatile int value to write
     *     memoryAccess.putIntVolatile(obj, offset, value);
     *     }</pre>
     *
     * @param obj    the object to which to write the volatile int
     * @param offset the memory offset at which to write the volatile int
     * @param value  the volatile int value to write
     */
    public void putIntVolatile(Object obj, long offset, int value) {
        unsafe.putIntVolatile(obj, offset, value);
    }

    /**
     * Atomically adds the given value to the byte field at the specified memory offset
     * of the provided object and returns the previous value of the byte field.
     *
     * @implSpec This method atomically adds the given value to the byte field at the specified
     *     offset and returns the previous value of the byte field before the addition. The operation
     *     ensures that the byte field is updated atomically to avoid race conditions.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the byte field
     *     long offset = ...; // The memory offset of the byte field
     *     byte delta = ...; // The value to add to the byte field
     *     byte previousValue = memoryAccess.getAndAddByte(obj, offset, delta);
     *     }</pre>
     *
     * @param obj    the object containing the byte field
     * @param offset the memory offset of the byte field
     * @param delta  the value to add to the byte field
     *
     * @return the previous value of the byte field before the addition
     */
    public byte getAndAddByte(Object obj, long offset, byte delta) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = (byte) ((expected + delta) & 0xFF);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Atomically adds the given value to the int field at the specified memory offset
     * of the provided object.
     *
     * @implSpec This method atomically adds the given value to the int field at the specified offset.
     *     The addition is performed atomically, and the method returns the previous value of the int field before the
     *     addition.
     *     This operation is useful for performing atomic updates on shared integer variables.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the int field
     *     long offset = ...; // The memory offset of the int field
     *     int delta = ...; // The value to add to the int field
     *     int previousValue = memoryAccess.getAndAddInt(obj, offset, delta);
     *     }</pre>
     *
     * @param obj    the object containing the int field
     * @param offset the memory offset of the int field
     * @param delta  the value to add to the int field
     *
     * @return the previous value of the int field before the addition
     */
    public int getAndAddInt(Object obj, long offset, int delta) {
        return unsafe.getAndAddInt(obj, offset, delta);
    }

    /**
     * Atomically sets the byte field at the specified memory offset of the provided object
     * to the given new value and returns the previous value of the byte field.
     *
     * @implSpec This method atomically sets the byte field at the specified offset to the new value
     *     and returns the previous value of the byte field before the update. The operation ensures that
     *     the byte field is updated atomically to avoid race conditions.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the byte field
     *     long offset = ...; // The memory offset of the byte field
     *     byte newValue = ...; // The new value to set the byte field to
     *     byte previousValue = memoryAccess.getAndSetByte(obj, offset, newValue);
     *     }</pre>
     *
     * @param obj      the object containing the byte field
     * @param offset   the memory offset of the byte field
     * @param newValue the new value to set the byte field to
     *
     * @return the previous value of the byte field before the update
     */
    public byte getAndSetByte(Object obj, long offset, byte newValue) {
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
     * @implSpec This method atomically sets the int field at the specified offset to the given new value.
     *     The method returns the previous value of the int field before the update.
     *     This operation is useful for performing atomic updates on shared integer variables.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     Object obj = ...; // The object containing the int field
     *     long offset = ...; // The memory offset of the int field
     *     int newValue = ...; // The new value to set the int field to
     *     int previousValue = memoryAccess.getAndSetInt(obj, offset, newValue);
     *     }</pre>
     *
     * @param obj      the object containing the int field
     * @param offset   the memory offset of the int field
     * @param newValue the new value to set the int field to
     *
     * @return the previous value of the int field before the update
     */
    public int getAndSetInt(Object obj, long offset, int newValue) {
        return unsafe.getAndSetInt(obj, offset, newValue);
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteUnaryOperator} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @implSpec This method atomically applies the provided update function to the byte value at the specified
     *     memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Define a byte update function that increments the value by 1
     *     ByteUnaryOperator incrementByOne = value -> (byte) (value + 1);
     *
     *     // Get the current byte value at the offset and update it atomically
     *     byte oldValue = memoryAccess.getAndUpdateByte(myObject, 0, incrementByOne);
     *
     *     // 'oldValue' contains the previous value, and the value at offset is incremented by 1.
     *     }</pre>
     *
     * @param obj      the object containing the byte value
     * @param offset   the memory offset of the byte value
     * @param updating the function to apply to the byte value
     *
     * @return the old byte value that was replaced
     */
    public final byte getAndUpdateByte(Object obj, long offset, ByteUnaryOperator updating) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = updating.applyAsByte(expected);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteUnaryOperator} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @implSpec This method atomically applies the provided update function to the byte value at the specified
     *     memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Define a byte update function that doubles the value
     *     ByteUnaryOperator doubleValue = value -> (byte) (value * 2);
     *
     *     // Get the current byte value at the offset, double it, and update it atomically
     *     byte newValue = memoryAccess.updateAndGetByte(myObject, 0, doubleValue);
     *
     *     // 'newValue' contains the updated value.
     *     }</pre>
     *
     * @param obj      the object containing the byte value
     * @param offset   the memory offset of the byte value
     * @param updating the function to apply to the byte value
     *
     * @return the new byte value
     */
    public final byte updateAndGetByte(final Object obj, final long offset, final ByteUnaryOperator updating) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = updating.applyAsByte(expected);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return newValue;
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteBinaryOperator} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @implSpec This method atomically applies the provided accumulator function to the byte value at the
     *     specified memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Define a byte binary operator that adds the update value to the byte value
     *     ByteBinaryOperator addValue = (currentValue, update) -> (byte) (currentValue + update);
     *
     *     // Get the current byte value at the offset, add 10 to it, and update it atomically
     *     byte oldValue = memoryAccess.getAndAccumulateByte(myObject, 0, (byte) 10, addValue);
     *
     *     // 'oldValue' contains the previous value, and the value at offset is incremented by 10.
     *     }</pre>
     *
     * @param obj          the object containing the byte value
     * @param offset       the memory offset of the byte value
     * @param update       the value to combine with the byte value
     * @param accumulation the function to apply to the byte value and the update value
     *
     * @return the old byte value that was replaced
     */
    public final byte getAndAccumulateByte(final Object obj,
                                           final long offset,
                                           final byte update,
                                           final ByteBinaryOperator accumulation) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = accumulation.applyAsByte(expected, update);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return expected;
    }

    /**
     * Atomically gets the byte value from the specified memory offset, applies the provided
     * {@link ByteBinaryOperator} to calculate a new byte value, and atomically sets the memory
     * location to the new value.
     *
     * @implSpec This method atomically applies the provided accumulator function to the byte value at the
     *     specified memory offset and ensures that the update is performed atomically.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *     AtomicAccess memoryAccess = new AtomicAccess();
     *
     *     // Define a byte binary operator that subtracts the update value from the byte value
     *     ByteBinaryOperator subtractValue = (currentValue, update) -> (byte) (currentValue - update);
     *
     *     // Get the current byte value at the offset, subtract 5 from it, and update it atomically
     *     byte newValue = memoryAccess.accumulateAndGetByte(myObject, 0, (byte) 5, subtractValue);
     *
     *     // 'newValue' contains the updated value.
     *     }</pre>
     *
     * @param obj          the object containing the byte value
     * @param offset       the memory offset of the byte value
     * @param update       the value to combine with the byte value
     * @param accumulation the function to apply to the byte value and the update value
     *
     * @return the new byte value
     */
    public final byte accumulateAndGetByte(Object obj, long offset, byte update, ByteBinaryOperator accumulation) {
        byte expected;
        byte newValue;

        do {
            expected = getByteVolatile(obj, offset);
            newValue = accumulation.applyAsByte(expected, update);
        } while (!compareAndSwapByte(obj, offset, expected, newValue));

        return newValue;
    }

    /**
     * A utility class to provide atomic operations on byte arrays.
     *
     * @implSpec This class is designed for atomic operations on byte arrays using underlying
     *     atomic memory access semantics provided by {@link AtomicAccess}. The operations include volatile gets/sets,
     *     atomic compare-and-swap, get-and-add, get-and-update, and accumulate operations.
     *
     * @see AtomicAccess
     */
    static class ByteArray {

        /**
         * Represents the class of a byte array.
         */
        private static final Class<?> ARRAY_CLASS = byte[].class;

        /**
         * The offset of the base address of the byte array.
         */
        private static final int ARRAY_BASE_OFFSET;

        /**
         * The offset shift for indexing into the byte array.
         */
        private static final int ARRAY_INDEX_OFFSET_SHIFT;

        /**
         * Instance of {@code AtomicAccess} for atomic operations.
         */
        private static final AtomicAccess ATOMIC_ACCESS = new AtomicAccess();

        static {
            ARRAY_BASE_OFFSET = ATOMIC_ACCESS.arrayBaseOffset(ARRAY_CLASS);

            int indexScale = ATOMIC_ACCESS.arrayIndexScale(ARRAY_CLASS);
            if ((indexScale & (indexScale - 1)) != 0) {
                throw new Error("The byte[] index scale is not a power of two");
            }

            ARRAY_INDEX_OFFSET_SHIFT = 31 - Integer.numberOfLeadingZeros(indexScale);
        }

        /**
         * Default constructor.
         */
        public ByteArray() {}

        /**
         * Validates the index against the byte array bounds and calculates the byte offset.
         *
         * @implSpec This method calculates the offset for the given index in the byte array
         *     based on the internal configuration set during class initialization.
         *
         * @param array The byte array to be checked.
         * @param i     The index to be checked.
         *
         * @return The calculated byte offset.
         * @throws IndexOutOfBoundsException if the index is out of bounds.
         */
        private static long checkedByteOffset(final byte[] array, final int i) {
            if (i < 0 || i >= array.length) {
                throw new IndexOutOfBoundsException(String.format(
                    "The index %s out of a bounds [%s, %s]", i, 0, array.length - 1
                ));
            }

            return ((long) i << ARRAY_INDEX_OFFSET_SHIFT) + ARRAY_BASE_OFFSET;
        }

        /**
         * Returns the byte value at the given index of the byte array using volatile semantics.
         *
         * @implSpec This method leverages {@link AtomicAccess#getByteVolatile(Object, long)}
         *     to achieve volatile memory access semantics.
         *
         * @see AtomicAccess#getByteVolatile(Object, long)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *             AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *             byte[] array = {1, 2, 3, 4, 5};
         *             byte value = byteArray.getVolatile(array, 2);
         *             System.out.println(value); // Prints 3
         *             }</pre>
         *
         * @param array The byte array to fetch the value from.
         * @param i     The index of the element to fetch.
         *
         * @return The byte value at the given index.
         */
        public byte getVolatile(final byte[] array, final int i) {
            return ATOMIC_ACCESS.getByteVolatile(array, checkedByteOffset(array, i));
        }

        /**
         * Sets the byte value at the given index of the byte array using volatile semantics.
         *
         * @implSpec This method uses {@link AtomicAccess#putByteVolatile(Object, long, byte)}
         *     to provide volatile memory semantics.
         *
         * @see AtomicAccess#putByteVolatile(Object, long, byte)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byteArray.putVolatile(array, 2, (byte) 8);
         *     }</pre>
         *
         * @param array    The byte array to update.
         * @param i        The index of the element to update.
         * @param newValue The new byte value to set.
         */
        public void putVolatile(final byte[] array, final int i, final byte newValue) {
            ATOMIC_ACCESS.putByteVolatile(array, checkedByteOffset(array, i), newValue);
        }

        /**
         * Atomically sets the element at position {@code i} to the given {@code newValue}
         * and returns the old value.
         *
         * @implSpec This method uses {@link AtomicAccess#getAndSetByte(Object, long, byte)}
         *     for atomic updates.
         *
         * @see AtomicAccess#getAndSetByte(Object, long, byte)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byte oldValue = byteArray.getAndSet(array, 2, (byte) 8);
         *     System.out.println(oldValue); // Prints 3
         *     }</pre>
         *
         * @param array    The byte array to update.
         * @param i        The index of the element to update.
         * @param newValue The new byte value to set.
         *
         * @return The previous value at the given index.
         */
        public byte getAndSet(final byte[] array, final int i, final byte newValue) {
            return ATOMIC_ACCESS.getAndSetByte(array, checkedByteOffset(array, i), newValue);
        }

        /**
         * Atomically updates the byte at the given index with the expected value.
         *
         * @implSpec This method uses {@link AtomicAccess#compareAndSwapByte(Object, long, byte, byte)}
         *     for atomic comparison and swap.
         *
         * @see AtomicAccess#compareAndSwapByte(Object, long, byte, byte)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     boolean swapped = byteArray.compareAndSwap(array, 2, (byte) 3, (byte) 8);
         *     System.out.println(swapped); // Prints true
         *     }</pre>
         *
         * @param array  The byte array to update.
         * @param i      The index of the element to update.
         * @param expect The expected byte value.
         * @param update The new byte value to set if the current value is {@code expect}.
         *
         * @return {@code true} if successful; or {@code false} if the actual value
         *     was not equal to the expected value.
         */
        public boolean compareAndSwap(final byte[] array, final int i, final byte expect, final byte update) {
            return ATOMIC_ACCESS.compareAndSwapByte(array, checkedByteOffset(array, i), expect, update);
        }

        /**
         * Atomically adds the given value to the element at the specified index.
         *
         * @implSpec This method uses {@link AtomicAccess#getAndAddByte(Object, long, byte)}
         *     for atomic addition.
         *
         * @see AtomicAccess#getAndAddByte(Object, long, byte)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byte oldValue = byteArray.getAndAdd(array, 2, (byte) 2);
         *     System.out.println(oldValue); // Prints 3
         *     }</pre>
         *
         * @param array The byte array to update.
         * @param i     The index of the element to update.
         * @param delta The value to add.
         *
         * @return The previous value at the given index.
         */
        public byte getAndAdd(final byte[] array, final int i, final byte delta) {
            return ATOMIC_ACCESS.getAndAddByte(array, checkedByteOffset(array, i), delta);
        }

        /**
         * Atomically updates the byte at the given index using the provided update function.
         *
         * @implSpec This method uses {@link AtomicAccess#getAndUpdateByte(Object, long, ByteUnaryOperator)}
         *     for atomic updates.
         *
         * @see AtomicAccess#getAndUpdateByte(Object, long, ByteUnaryOperator)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byte oldValue = byteArray.getAndUpdate(array, 2, val -> (byte) (val + 1));
         *     System.out.println(oldValue); // Prints 3
         *     }</pre>
         *
         * @param array          The byte array to update.
         * @param i              The index of the element to update.
         * @param updateFunction A unary operator function to apply.
         *
         * @return The previous value at the given index.
         */
        public byte getAndUpdate(final byte[] array, final int i, final ByteUnaryOperator updateFunction) {
            return ATOMIC_ACCESS.getAndUpdateByte(array, checkedByteOffset(array, i), updateFunction);
        }

        /**
         * Atomically updates the byte at the given index using the provided update function,
         * and then returns the updated value.
         *
         * @implSpec This method uses {@link AtomicAccess#updateAndGetByte(Object, long, ByteUnaryOperator)}
         *     for atomic updates.
         *
         * @see AtomicAccess#updateAndGetByte(Object, long, ByteUnaryOperator)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byte updatedValue = byteArray.updateAndGet(array, 2, val -> (byte) (val + 1));
         *     System.out.println(updatedValue); // Prints 4
         *     }</pre>
         *
         * @param array          The byte array to update.
         * @param i              The index of the element to update.
         * @param updateFunction A unary operator function to apply.
         *
         * @return The updated value at the given index.
         */
        public byte updateAndGet(final byte[] array, final int i, final ByteUnaryOperator updateFunction) {
            return ATOMIC_ACCESS.updateAndGetByte(array, checkedByteOffset(array, i), updateFunction);
        }

        /**
         * Atomically updates the byte at the given index using the accumulator function and given update.
         *
         * @implSpec This method uses
         *     {@link AtomicAccess#getAndAccumulateByte(Object, long, byte, ByteBinaryOperator)}
         *     for atomic accumulation.
         *
         * @see AtomicAccess#getAndAccumulateByte(Object, long, byte, ByteBinaryOperator)
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byte oldValue = byteArray.getAndAccumulate(array, 2, (byte) 2, (a, b) -> (byte) (a + b));
         *     System.out.println(oldValue); // Prints 3
         *     }</pre>
         *
         * @param array               The byte array to update.
         * @param i                   The index of the element to update.
         * @param update              The byte value to use with the accumulator function.
         * @param accumulatorFunction A binary operator function to apply.
         *
         * @return The previous value at the given index.
         */
        public byte getAndAccumulate(final byte[] array,
                                     final int i,
                                     final byte update,
                                     final ByteBinaryOperator accumulatorFunction) {
            return ATOMIC_ACCESS.getAndAccumulateByte(array, checkedByteOffset(array, i), update, accumulatorFunction);
        }

        /**
         * Atomically updates the byte at the given index using the accumulator function and given update,
         * and then returns the updated value.
         *
         * @see AtomicAccess#accumulateAndGetByte(Object, long, byte, ByteBinaryOperator)
         *
         * @implSpec This method uses
         *     {@link AtomicAccess#accumulateAndGetByte(Object, long, byte, ByteBinaryOperator)}
         *     for atomic accumulation.
         *
         *     <p><strong>Example usage:</strong></p>
         *     <pre>{@code
         *     AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
         *     byte[] array = {1, 2, 3, 4, 5};
         *     byte updatedValue = byteArray.accumulateAndGet(array, 2, (byte) 2, (a, b) -> (byte) (a + b));
         *     System.out.println(updatedValue); // Prints 5
         *     }</pre>
         *
         * @param array               The byte array to update.
         * @param i                   The index of the element to update.
         * @param update              The byte value to use with the accumulator function.
         * @param accumulatorFunction A binary operator function to apply.
         *
         * @return The updated value at the given index.
         */
        public byte accumulateAndGet(final byte[] array,
                                     final int i,
                                     final byte update,
                                     final ByteBinaryOperator accumulatorFunction) {
            return ATOMIC_ACCESS.accumulateAndGetByte(array, checkedByteOffset(array, i), update, accumulatorFunction);
        }
    }
}
