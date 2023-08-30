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
 * A utility class that wraps the sun.misc.Unsafe class to provide access to memory operations.
 * This class implements the MemoryAccess interface.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 30.08.2023 13:26
 */
@SuppressWarnings("sunapi")
final class MemoryAccess {

    public enum Endianness {
        MIDDLE_ENDIAN, LITTLE_ENDIAN, BIG_ENDIAN;
    }

    public static final Endianness ENDIANNESS;

    private static final Unsafe unsafe;

    static {
        Class<?> unsafeClass = getUnsafeClass();
        unsafe = getUnsafeInstanceForClass(unsafeClass);
        ENDIANNESS = getEndianness();
    }

    /**
     * Retrieves the Unsafe class using reflection.
     *
     * @return The Unsafe class.
     *
     * @throws RuntimeException if the Unsafe class cannot be found.
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
     * Determines whether the underlying platform is <b>big-endian</b> or
     * <b>little-endian</b>.
     *
     * @implSpec This method uses a byte-order probe to determine whether the underlying
     *     platform is big-endian or little-endian. It allocates a 2-byte buffer
     *     using the {@code allocateMemory} method, writes the value
     *     {@code 0x10000001} to the buffer using the {@code putShort} method,
     *     reads the first byte of the buffer using the {@code getByte} method, and
     *     then deallocates the buffer using the {@code freeMemory} method.
     *     If the first byte of the buffer is {@code 0x01}, the platform is
     *     little-endian; if it is {@code 0x10}, the platform is big-endian.
     *
     * @return {@code true} if the platform is big-endian, {@code false} if it
     *     is little-endian.
     */
    private static Endianness getEndianness() {
        long intValueOffset = unsafe.allocateMemory(4);
        unsafe.putInt(intValueOffset, 0x04030201);
        byte lowByteValue = (byte) unsafe.getByte(intValueOffset);
        unsafe.freeMemory(intValueOffset);

        switch (lowByteValue) {
            case 0x01:
            default:
                return Endianness.LITTLE_ENDIAN;
            case 0x02:
                return Endianness.MIDDLE_ENDIAN;
            case 0x04:
                return Endianness.BIG_ENDIAN;
        }
    }

    public boolean isIsBigEndian() {
        return Endianness.BIG_ENDIAN.equals(ENDIANNESS);
    }

    /**
     * Returns the system's memory page size.
     *
     * @return The memory page size in bytes.
     */
    public int pageSize() {
        return unsafe.pageSize();
    }

    /**
     * Returns the size of native pointers in bytes.
     *
     * @return The size of native pointers in bytes.
     */
    public int addressSize() {
        return unsafe.addressSize();
    }

    /**
     * Retrieves the memory address at the specified offset.
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
     * @param address The starting address of the memory block to free.
     */
    public void freeMemory(long address) {
        unsafe.freeMemory(address);
    }

    /**
     * Fills a range of memory with a specified value.
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

    public boolean compareAndSwapInt(Object obj, long offset, int expected, int newValue) {
        return unsafe.compareAndSwapInt(obj, offset, expected, newValue);
    }

    public boolean compareAndSwapLong(Object obj, long offset, long expected, long newValue) {
        return unsafe.compareAndSwapLong(obj, offset, expected, newValue);
    }

    public boolean compareAndSwapObject(Object obj, long offset, Object expected, Object newValue) {
        return unsafe.compareAndSwapObject(obj, offset, expected, newValue);
    }

    public Object getObjectVolatile(Object obj, long offset) {
        return unsafe.getObjectVolatile(obj, offset);
    }

    public void putObjectVolatile(Object obj, long offset, Object value) {
        unsafe.putObjectVolatile(obj, offset, value);
    }

    public int getIntVolatile(Object obj, long offset) {
        return unsafe.getIntVolatile(obj, offset);
    }

    public void putIntVolatile(Object obj, long offset, int value) {
        unsafe.putIntVolatile(obj, offset, value);
    }

    public boolean getBooleanVolatile(Object obj, long offset) {
        return unsafe.getBooleanVolatile(obj, offset);
    }

    public void putBooleanVolatile(Object obj, long offset, boolean value) {
        unsafe.putBooleanVolatile(obj, offset, value);
    }

    public byte getByteVolatile(Object obj, long offset) {
        return unsafe.getByteVolatile(obj, offset);
    }

    public void putByteVolatile(Object obj, long offset, byte value) {
        unsafe.putByteVolatile(obj, offset, value);
    }

    public short getShortVolatile(Object obj, long offset) {
        return unsafe.getShortVolatile(obj, offset);
    }

    public void putShortVolatile(Object obj, long offset, short value) {
        unsafe.putShortVolatile(obj, offset, value);
    }

    public char getCharVolatile(Object obj, long offset) {
        return unsafe.getCharVolatile(obj, offset);
    }

    public void putCharVolatile(Object obj, long offset, char value) {
        unsafe.putCharVolatile(obj, offset, value);
    }

    public long getLongVolatile(Object obj, long offset) {
        return unsafe.getLongVolatile(obj, offset);
    }

    public void putLongVolatile(Object obj, long offset, long value) {
        unsafe.putLongVolatile(obj, offset, value);
    }

    public float getFloatVolatile(Object obj, long offset) {
        return unsafe.getFloatVolatile(obj, offset);
    }

    public void putFloatVolatile(Object obj, long offset, float value) {
        unsafe.putFloatVolatile(obj, offset, value);
    }

    public double getDoubleVolatile(Object obj, long offset) {
        return unsafe.getDoubleVolatile(obj, offset);
    }

    public void putDoubleVolatile(Object obj, long offset, double value) {
        unsafe.putDoubleVolatile(obj, offset, value);
    }

    public void putOrderedObject(Object obj, long offset, Object value) {
        unsafe.putOrderedObject(obj, offset, value);
    }

    public void putOrderedInt(Object obj, long offset, int value) {
        unsafe.putOrderedInt(obj, offset, value);
    }

    public void putOrderedLong(Object obj, long offset, long value) {
        unsafe.putOrderedLong(obj, offset, value);
    }

    public int getAndAddInt(Object obj, long offset, int delta) {
        return unsafe.getAndAddInt(obj, offset, delta);
    }

    public long getAndAddLong(Object obj, long offset, long delta) {
        return unsafe.getAndAddLong(obj, offset, delta);
    }

    public int getAndSetInt(Object obj, long offset, int newValue) {
        return unsafe.getAndSetInt(obj, offset, newValue);
    }

    public long getAndSetLong(Object obj, long offset, long newValue) {
        return unsafe.getAndSetLong(obj, offset, newValue);
    }

    public Object getAndSetObject(Object obj, long offset, Object newValue) {
        return unsafe.getAndSetObject(obj, offset, newValue);
    }
}
