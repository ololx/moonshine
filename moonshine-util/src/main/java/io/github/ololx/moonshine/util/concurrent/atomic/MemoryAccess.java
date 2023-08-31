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
 *     MemoryAccess memoryAccess = new MemoryAccess();
 *
 *     // Allocate memory and perform operations
 *     long address = memoryAccess.allocateMemory(4);
 *     memoryAccess.putInt(address, 42);
 *     int value = memoryAccess.getInt(address);
 *     memoryAccess.freeMemory(address);
 *
 *     // Atomic operations
 *     Object obj = new Object();
 *     long offset = memoryAccess.objectFieldOffset(obj.getClass().getDeclaredField("fieldName"));
 *     memoryAccess.compareAndSwapInt(obj, offset, expectedValue, newValue);
 *     }</pre>
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 30.08.2023 13:26
 */
@SuppressWarnings("sunapi")
public final class MemoryAccess {

    /**
     * Enumeration representing different byte orders (endianness).
     */
    public enum Endianness {
        /**
         * Middle-endian byte order.
         */
        ME,

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
         *     Example usage:
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
         * Checks if the byte order is middle-endian.
         *
         * @implSpec This method checks if the current byte order enum constant is equal to {@link #ME},
         *     indicating middle-endian byte order.
         *
         *     Example usage:
         *     <pre>{@code
         *     Endianness endianness = Endianness.ME;
         *     boolean isMiddleEndian = endianness.isMiddleEndian();
         *     System.out.println("Is Middle Endian: " + isMiddleEndian); // Output: true
         *     }</pre>
         *
         * @return true if middle-endian, false otherwise.
         */
        public boolean isMiddleEndian() {
            return this.equals(ME);
        }

        /**
         * Checks if the byte order is little-endian.
         *
         * @implSpec This method checks if the current byte order enum constant is equal to {@link #LE},
         *     indicating little-endian byte order.
         *
         *     Example usage:
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
     *     little-endian byte order; if it is {@code 0x02}, the platform uses
     *     middle-endian (PDP-11) byte order; if it is {@code 0x04}, the platform uses
     *     big-endian byte order.
     *
     * @return {@link Endianness#LE} if the platform uses little-endian byte order,
     *     {@link Endianness#ME} if the platform uses big-endian byte order,
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
            case 0x02:
                return Endianness.ME;
            case 0x03:
            default:
                throw new RuntimeException("Failed to determine byte order");
            case 0x04:
                return Endianness.BE;
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
     *     // Getting the system endianness
     *     Endianness systemEndianness = endianness();
     *     if (systemEndianness == Endianness.BIG_ENDIAN) {
     *         System.out.println("System uses big-endian.");
     *     } else {
     *         System.out.println("System uses little-endian.");
     *     }
     *     }</pre>
     *
     * @return The endianness of the system, indicating how multi-byte data is stored.
     */
    public Endianness endianness() {
        return ENDIANNESS;
    }

    /**
     * Returns the memory page size of the system.
     *
     * @implSpec The memory page size is the smallest unit of memory that an operating system's memory management
     *     uses. It can affect various aspects of memory allocation and performance. Understanding the
     *     memory page size can be important when optimizing memory usage and managing memory-mapped files.
     * @see <a href="https://en.wikipedia.org/wiki/Page_(computer_memory)">Memory Page</a>
     * @see <a href="https://en.wikipedia.org/wiki/Memory-mapped_file">Memory-Mapped File</a>
     *
     *     Example usage:
     *     <pre>{@code
     *     // Getting the system memory page size
     *     int systemPageSize = pageSize();
     *     System.out.println("System memory page size: " + systemPageSize + " bytes");
     *     }</pre>
     *
     * @return The memory page size in bytes.
     */
    public int pageSize() {
        return unsafe.pageSize();
    }

    /**
     * Returns the size of native pointers in bytes.
     *
     * @implSpec The size of native pointers determines the largest addressable unit of memory in the system.
     *     This information is important when dealing with memory manipulation and direct memory access.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Getting the system's native pointer size
     *     int pointerSize = addressSize();
     *     System.out.println("System native pointer size: " + pointerSize + " bytes");
     *     }</pre>
     *
     * @return The size of native pointers in bytes.
     */
    public int addressSize() {
        return unsafe.addressSize();
    }

    /**
     * Retrieves the memory address at the specified offset.
     *
     * @implSpec This method reads the memory address at the specified offset using the Unsafe class.
     *     The behavior is undefined if the offset is not within the bounds of allocated memory.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Allocate memory and get the address at a specific offset
     *     long memoryAddress = allocateMemory(16); // Allocates 16 bytes of memory
     *     long offset = 8; // Offset within the allocated memory
     *     long address = getAddress(memoryAddress + offset);
     *     System.out.println("Memory address at offset " + offset + ": " + address);
     *     }</pre>
     *
     * @param offset The memory offset.
     *
     * @return The memory address at the offset.
     */
    public long getAddress(long offset) {
        return unsafe.getAddress(offset);
    }

    /**
     * Allocates a block of native memory of the given size.
     *
     * @implSpec This method allocates a block of native memory using the Unsafe class.
     *     The allocated memory should be freed after use by calling `unsafe.freeMemory(address)`.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Allocate memory and use the allocated memory address
     *     long memorySize = 1024; // 1 KB of memory
     *     long memoryAddress = allocateMemory(memorySize);
     *     System.out.println("Allocated memory address: " + memoryAddress);
     *
     *     // Don't forget to free the allocated memory after use
     *     // unsafe.freeMemory(memoryAddress);
     *     }</pre>
     *
     * @param bytes The size of memory to allocate in bytes.
     *
     * @return The starting address of the allocated memory block.
     */
    public long allocateMemory(long bytes) {
        return unsafe.allocateMemory(bytes);
    }

    /**
     * Resizes a previously allocated block of native memory.
     *
     * @implSpec This method resizes a previously allocated block of native memory using the Unsafe class.
     *     The content of the original block is copied to the new block if necessary.
     *     The behavior is undefined if the provided address is not a valid allocated memory block.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Allocate initial memory
     *     long initialSize = 64;
     *     long initialAddress = allocateMemory(initialSize);
     *
     *     // Reallocate memory to a larger size
     *     long newSize = 128;
     *     long newAddress = reallocateMemory(initialAddress, newSize);
     *     System.out.println("New memory address after reallocation: " + newAddress);
     *     }</pre>
     *
     * @param address The starting address of the memory block.
     * @param bytes   The new size of the memory block in bytes.
     *
     * @return The new starting address of the memory block.
     */
    public long reallocateMemory(long address, long bytes) {
        return unsafe.reallocateMemory(address, bytes);
    }

    /**
     * Frees a previously allocated block of native memory.
     *
     * @implSpec This method frees a previously allocated block of native memory using the Unsafe class.
     *     After calling this method, the memory block will no longer be accessible,
     *     and the address will be invalid for further use.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Allocate memory and use it
     *     long memorySize = 256;
     *     long memoryAddress = allocateMemory(memorySize);
     *
     *     // Free the allocated memory when done using it
     *     freeMemory(memoryAddress);
     *     System.out.println("Memory has been freed.");
     *     }</pre>
     *
     * @param address The starting address of the memory block to free.
     */
    public void freeMemory(long address) {
        unsafe.freeMemory(address);
    }

    /**
     * Fills a range of memory with a specified value.
     *
     * @implSpec This method fills range of memory within the given object starting from the specified offset.
     *     The specified number of bytes will be set to the provided value.
     *     The behavior is undefined if the provided object is not of the appropriate type to hold the memory,
     *     or if the offset and size exceed the bounds of the object's memory.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Create a byte array and fill part of it with a value
     *     byte[] byteArray = new byte[10];
     *     setMemory(byteArray, 2, 4, (byte) 42);
     *     System.out.println(Arrays.toString(byteArray)); // [0, 0, 42, 42, 42, 42, 0, 0, 0, 0]
     *     }</pre>
     *
     * @param obj    The object whose memory is being filled.
     * @param offset The starting offset of the memory region.
     * @param bytes  The number of bytes to fill.
     * @param value  The value to fill the memory with.
     */
    public void setMemory(Object obj, long offset, long bytes, byte value) {
        unsafe.setMemory(obj, offset, bytes, value);
    }

    /**
     * Copies a range of memory from one object to another.
     *
     * @implSpec This method copies a range of memory from the source object to the destination object.
     *     The specified number of bytes will be copied from the source offset to the destination offset.
     *     The behavior is undefined if the provided objects are not of the appropriate type to hold the memory,
     *     or if the offset and size exceed the bounds of the objects' memory.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Create two byte arrays and copy a portion of one array to the other
     *     byte[] srcArray = {1, 2, 3, 4, 5};
     *     byte[] destArray = new byte[3];
     *     copyMemory(srcArray, 1, destArray, 0, 3);
     *     System.out.println(Arrays.toString(destArray)); // [2, 3, 4]
     *     }</pre>
     *
     * @param srcObj     The source object.
     * @param srcOffset  The starting offset of the source memory region.
     * @param destObj    The destination object.
     * @param destOffset The starting offset of the destination memory region.
     * @param bytes      The number of bytes to copy.
     */
    public void copyMemory(Object srcObj, long srcOffset, Object destObj, long destOffset, long bytes) {
        unsafe.copyMemory(srcObj, srcOffset, destObj, destOffset, bytes);
    }

    /**
     * Returns the base offset of the provided array class.
     *
     * @implSpec This method returns the base offset that should be added to the index of an element in the array
     *     to access the actual memory location of the element. The base offset is platform-dependent and may
     *     vary between different array classes.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Get the base offset of a byte array
     *     int baseOffset = arrayBaseOffset(byte[].class);
     *     System.out.println("Base Offset: " + baseOffset);
     *     }</pre>
     *
     * @param arrayClass The class of the array.
     *
     * @return The base offset of the array class.
     */
    public int arrayBaseOffset(Class<?> arrayClass) {
        return unsafe.arrayBaseOffset(arrayClass);
    }

    /**
     * Returns the index scale of the provided array class.
     *
     * @implSpec This method returns the index scale, which is the factor that should be multiplied with the index
     *     to compute the byte offset between elements in the array. The index scale is platform-dependent and
     *     may vary between different array classes.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Get the index scale of an int array
     *     int indexScale = arrayIndexScale(int[].class);
     *     System.out.println("Index Scale: " + indexScale);
     *     }</pre>
     *
     * @param arrayClass The class of the array.
     *
     * @return The index scale of the array class.
     */
    public int arrayIndexScale(Class<?> arrayClass) {
        return unsafe.arrayIndexScale(arrayClass);
    }

    /**
     * Returns the value of a byte at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the byte
     * @param offset the memory offset at which to read the byte
     *
     * @return the byte value read from the specified memory offset
     */
    public byte getByte(Object obj, long offset) {
        return unsafe.getByte(obj, offset);
    }

    /**
     * Writes a byte value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the byte
     * @param offset the memory offset at which to write the byte
     * @param value  the byte value to write
     */
    public void putByte(Object obj, long offset, byte value) {
        unsafe.putByte(obj, offset, value);
    }

    /**
     * Returns the value of a short at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the short
     * @param offset the memory offset at which to read the short
     *
     * @return the short value read from the specified memory offset
     */
    public short getShort(Object obj, long offset) {
        return unsafe.getShort(obj, offset);
    }

    /**
     * Writes a short value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the short
     * @param offset the memory offset at which to write the short
     * @param value  the short value to write
     */
    public void putShort(Object obj, long offset, short value) {
        unsafe.putShort(obj, offset, value);
    }

    /**
     * Returns the value of a char at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the char
     * @param offset the memory offset at which to read the char
     *
     * @return the char value read from the specified memory offset
     */
    public char getChar(Object obj, long offset) {
        return unsafe.getChar(obj, offset);
    }

    /**
     * Writes a char value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the char
     * @param offset the memory offset at which to write the char
     * @param value  the char value to write
     */
    public void putChar(Object obj, long offset, char value) {
        unsafe.putChar(obj, offset, value);
    }

    /**
     * Returns the value of an int at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the int
     * @param offset the memory offset at which to read the int
     *
     * @return the int value read from the specified memory offset
     */
    public int getInt(Object obj, long offset) {
        return unsafe.getInt(obj, offset);
    }

    /**
     * Writes an int value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the int
     * @param offset the memory offset at which to write the int
     * @param value  the int value to write
     */
    public void putInt(Object obj, long offset, int value) {
        unsafe.putInt(obj, offset, value);
    }

    /**
     * Returns the value of a boolean at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the boolean
     * @param offset the memory offset at which to read the boolean
     *
     * @return the boolean value read from the specified memory offset
     */
    public boolean getBoolean(Object obj, long offset) {
        return unsafe.getBoolean(obj, offset);
    }

    /**
     * Writes a boolean value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the boolean
     * @param offset the memory offset at which to write the boolean
     * @param value  the boolean value to write
     */
    public void putBoolean(Object obj, long offset, boolean value) {
        unsafe.putBoolean(obj, offset, value);
    }

    /**
     * Returns the value of a float at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the float
     * @param offset the memory offset at which to read the float
     *
     * @return the float value read from the specified memory offset
     */
    public float getFloat(Object obj, long offset) {
        return unsafe.getFloat(obj, offset);
    }

    /**
     * Writes a float value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the float
     * @param offset the memory offset at which to write the float
     * @param value  the float value to write
     */
    public void putFloat(Object obj, long offset, float value) {
        unsafe.putFloat(obj, offset, value);
    }

    /**
     * Returns the value of a long at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the long
     * @param offset the memory offset at which to read the long
     *
     * @return the long value read from the specified memory offset
     */
    public long getLong(Object obj, long offset) {
        return unsafe.getLong(obj, offset);
    }

    /**
     * Writes a long value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the long
     * @param offset the memory offset at which to write the long
     * @param value  the long value to write
     */
    public void putLong(Object obj, long offset, long value) {
        unsafe.putLong(obj, offset, value);
    }

    /**
     * Returns the value of a double at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the double
     * @param offset the memory offset at which to read the double
     *
     * @return the double value read from the specified memory offset
     */
    public double getDouble(Object obj, long offset) {
        return unsafe.getDouble(obj, offset);
    }

    /**
     * Writes a double value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the double
     * @param offset the memory offset at which to write the double
     * @param value  the double value to write
     */
    public void putDouble(Object obj, long offset, double value) {
        unsafe.putDouble(obj, offset, value);
    }

    /**
     * Returns the reference to an object at the specified memory offset of the given object.
     *
     * @param obj    the object from which to read the reference
     * @param offset the memory offset at which to read the reference
     *
     * @return the object reference read from the specified memory offset
     */
    public Object getObject(Object obj, long offset) {
        return unsafe.getObject(obj, offset);
    }

    /**
     * Writes a reference to an object to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the reference
     * @param offset the memory offset at which to write the reference
     * @param value  the object reference to write
     */
    public void putObject(Object obj, long offset, Object value) {
        unsafe.putObject(obj, offset, value);
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
     */
    public boolean compareAndSwapInt(Object obj, long offset, int expected, int newValue) {
        return unsafe.compareAndSwapInt(obj, offset, expected, newValue);
    }

    /**
     * Atomically compares the long field at the specified memory offset of the provided object
     * with the expected value, and if they are equal, updates the field to the new value.
     *
     * @param obj      the object containing the long field
     * @param offset   the memory offset of the long field
     * @param expected the expected value of the long field
     * @param newValue the new value to set the long field to
     *
     * @return {@code true} if the comparison and update were successful, {@code false} otherwise
     */
    public boolean compareAndSwapLong(Object obj, long offset, long expected, long newValue) {
        return unsafe.compareAndSwapLong(obj, offset, expected, newValue);
    }

    /**
     * Atomically compares the reference field at the specified memory offset of the provided object
     * with the expected value, and if they are equal, updates the field to the new value.
     *
     * @param obj      the object containing the reference field
     * @param offset   the memory offset of the reference field
     * @param expected the expected value of the reference field
     * @param newValue the new value to set the reference field to
     *
     * @return {@code true} if the comparison and update were successful, {@code false} otherwise
     */
    public boolean compareAndSwapObject(Object obj, long offset, Object expected, Object newValue) {
        return unsafe.compareAndSwapObject(obj, offset, expected, newValue);
    }

    /**
     * Returns the value of the volatile reference field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile reference
     * @param offset the memory offset at which to read the volatile reference
     *
     * @return the volatile reference value read from the specified memory offset
     */
    public Object getObjectVolatile(Object obj, long offset) {
        return unsafe.getObjectVolatile(obj, offset);
    }

    /**
     * Writes a volatile reference value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile reference
     * @param offset the memory offset at which to write the volatile reference
     * @param value  the volatile reference value to write
     */
    public void putObjectVolatile(Object obj, long offset, Object value) {
        unsafe.putObjectVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile int field at the specified memory offset of the provided object.
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
     * @param obj    the object to which to write the volatile int
     * @param offset the memory offset at which to write the volatile int
     * @param value  the volatile int value to write
     */
    public void putIntVolatile(Object obj, long offset, int value) {
        unsafe.putIntVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile boolean field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile boolean
     * @param offset the memory offset at which to read the volatile boolean
     *
     * @return the volatile boolean value read from the specified memory offset
     */
    public boolean getBooleanVolatile(Object obj, long offset) {
        return unsafe.getBooleanVolatile(obj, offset);
    }

    /**
     * Writes a volatile boolean value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile boolean
     * @param offset the memory offset at which to write the volatile boolean
     * @param value  the volatile boolean value to write
     */
    public void putBooleanVolatile(Object obj, long offset, boolean value) {
        unsafe.putBooleanVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile byte field at the specified memory offset of the provided object.
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
     * @param obj    the object to which to write the volatile byte
     * @param offset the memory offset at which to write the volatile byte
     * @param value  the volatile byte value to write
     */
    public void putByteVolatile(Object obj, long offset, byte value) {
        unsafe.putByteVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile short field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile short
     * @param offset the memory offset at which to read the volatile short
     *
     * @return the volatile short value read from the specified memory offset
     */
    public short getShortVolatile(Object obj, long offset) {
        return unsafe.getShortVolatile(obj, offset);
    }

    /**
     * Writes a volatile short value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile short
     * @param offset the memory offset at which to write the volatile short
     * @param value  the volatile short value to write
     */
    public void putShortVolatile(Object obj, long offset, short value) {
        unsafe.putShortVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile char field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile char
     * @param offset the memory offset at which to read the volatile char
     *
     * @return the volatile char value read from the specified memory offset
     */
    public char getCharVolatile(Object obj, long offset) {
        return unsafe.getCharVolatile(obj, offset);
    }

    /**
     * Writes a volatile char value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile char
     * @param offset the memory offset at which to write the volatile char
     * @param value  the volatile char value to write
     */
    public void putCharVolatile(Object obj, long offset, char value) {
        unsafe.putCharVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile long field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile long
     * @param offset the memory offset at which to read the volatile long
     *
     * @return the volatile long value read from the specified memory offset
     */
    public long getLongVolatile(Object obj, long offset) {
        return unsafe.getLongVolatile(obj, offset);
    }

    /**
     * Writes a volatile long value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile long
     * @param offset the memory offset at which to write the volatile long
     * @param value  the volatile long value to write
     */
    public void putLongVolatile(Object obj, long offset, long value) {
        unsafe.putLongVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile float field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile float
     * @param offset the memory offset at which to read the volatile float
     *
     * @return the volatile float value read from the specified memory offset
     */
    public float getFloatVolatile(Object obj, long offset) {
        return unsafe.getFloatVolatile(obj, offset);
    }

    /**
     * Writes a volatile float value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile float
     * @param offset the memory offset at which to write the volatile float
     * @param value  the volatile float value to write
     */
    public void putFloatVolatile(Object obj, long offset, float value) {
        unsafe.putFloatVolatile(obj, offset, value);
    }

    /**
     * Returns the value of the volatile double field at the specified memory offset of the provided object.
     *
     * @param obj    the object from which to read the volatile double
     * @param offset the memory offset at which to read the volatile double
     *
     * @return the volatile double value read from the specified memory offset
     */
    public double getDoubleVolatile(Object obj, long offset) {
        return unsafe.getDoubleVolatile(obj, offset);
    }

    /**
     * Writes a volatile double value to the specified memory offset of the given object.
     *
     * @param obj    the object to which to write the volatile double
     * @param offset the memory offset at which to write the volatile double
     * @param value  the volatile double value to write
     */
    public void putDoubleVolatile(Object obj, long offset, double value) {
        unsafe.putDoubleVolatile(obj, offset, value);
    }

    /**
     * Writes an ordered object value to the specified memory offset of the given object.
     * This method provides similar memory semantics as a volatile write.
     *
     * @param obj    the object to which to write the ordered object
     * @param offset the memory offset at which to write the ordered object
     * @param value  the ordered object value to write
     */
    public void putOrderedObject(Object obj, long offset, Object value) {
        unsafe.putOrderedObject(obj, offset, value);
    }

    /**
     * Writes an ordered int value to the specified memory offset of the given object.
     * This method provides similar memory semantics as a volatile write.
     *
     * @param obj    the object to which to write the ordered int
     * @param offset the memory offset at which to write the ordered int
     * @param value  the ordered int value to write
     */
    public void putOrderedInt(Object obj, long offset, int value) {
        unsafe.putOrderedInt(obj, offset, value);
    }

    /**
     * Writes an ordered long value to the specified memory offset of the given object.
     * This method provides similar memory semantics as a volatile write.
     *
     * @param obj    the object to which to write the ordered long
     * @param offset the memory offset at which to write the ordered long
     * @param value  the ordered long value to write
     */
    public void putOrderedLong(Object obj, long offset, long value) {
        unsafe.putOrderedLong(obj, offset, value);
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
     */
    public int getAndAddInt(Object obj, long offset, int delta) {
        return unsafe.getAndAddInt(obj, offset, delta);
    }

    /**
     * Atomically adds the given value to the long field at the specified memory offset
     * of the provided object.
     *
     * @param obj    the object containing the long field
     * @param offset the memory offset of the long field
     * @param delta  the value to add to the long field
     *
     * @return the previous value of the long field before the addition
     */
    public long getAndAddLong(Object obj, long offset, long delta) {
        return unsafe.getAndAddLong(obj, offset, delta);
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
     */
    public int getAndSetInt(Object obj, long offset, int newValue) {
        return unsafe.getAndSetInt(obj, offset, newValue);
    }

    /**
     * Atomically sets the long field at the specified memory offset of the provided
     * object to the given new value.
     *
     * @param obj      the object containing the long field
     * @param offset   the memory offset of the long field
     * @param newValue the new value to set the long field to
     *
     * @return the previous value of the long field before the update
     */
    public long getAndSetLong(Object obj, long offset, long newValue) {
        return unsafe.getAndSetLong(obj, offset, newValue);
    }

    /**
     * Atomically sets the reference field at the specified memory offset of the provided
     * object to the given new value.
     *
     * @param obj      the object containing the reference field
     * @param offset   the memory offset of the reference field
     * @param newValue the new value to set the reference field to
     *
     * @return the previous value of the reference field before the update
     */
    public Object getAndSetObject(Object obj, long offset, Object newValue) {
        return unsafe.getAndSetObject(obj, offset, newValue);
    }
}
