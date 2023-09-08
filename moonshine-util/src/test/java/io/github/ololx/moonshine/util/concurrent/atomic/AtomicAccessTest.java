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

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 07.09.2023 13:18
 */
public class AtomicAccessTest {

    static AtomicAccess atomicAccess;

    @BeforeTest
    public static void setUp() {
        atomicAccess = new AtomicAccess();
    }

    @AfterClass
    public static void tearDown() {
        atomicAccess = null;
    }

    @Test
    public void endianness_whenEndiannessWasDetected_thenReturnEndiannessBigEndianOrLittleEndian() {
        AtomicAccess.Endianness endianness = atomicAccess.endianness();

        assertNotNull(endianness);
        assertTrue(endianness.isBigEndian() || endianness.isLittleEndian());
    }

    @DataProvider
    public Object[] providesArraysClasses() {
        return new Object[] {
            byte[].class,
            short[].class,
            char[].class,
            int[].class,
            long[].class,
            Object[].class,
        };
    }

    @Test(dataProvider = "providesArraysClasses")
    public void arrayBaseOffset_whenClassIsNotNull_thenReturnOffset(Class<?> arrayClass) {
        Integer arrayBaseOffset = atomicAccess.arrayBaseOffset(arrayClass);
        assertNotNull(arrayBaseOffset);
    }

    @Test(dataProvider = "providesArraysClasses")
    public void arrayIndexScale_whenClassIsNotNull_thenReturnScale(Class<?> arrayClass) {
        Integer arrayBaseOffset = atomicAccess.arrayIndexScale(arrayClass);
        assertNotNull(arrayBaseOffset);
    }

    @DataProvider
    public Object[][] providesObjectsClassesAndFieldNames() {
        return new Object[][] {
            {Byte.class, "value"},
            {Character.class, "value"},
            {Short.class, "value"},
            {Integer.class, "value"},
            {Long.class, "value"},
            {String.class, "hash"},
        };
    }

    @Test(dataProvider = "providesObjectsClassesAndFieldNames")
    public void objectFieldOffset_whenClassNotNullAndFieldExists_thenReturnScale(Class<?> objClass, String fieldName) {
        Long objectFieldOffset = atomicAccess.objectFieldOffset(objClass, fieldName);
        assertNotNull(objectFieldOffset);
    }

    @Test
    public void getByte_whenValueExists_thenReturnValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        Byte value = atomicAccess.getByte(objectWithFields, valueOffset);

        assertNotNull(value);
        assertEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
    }

    @Test
    public void putByte_whenPutNewValue_thenValueChanged() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        atomicAccess.putByte(objectWithFields, valueOffset, Byte.MAX_VALUE);

        assertNotEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
        assertEquals(objectWithFields.byteValue, Byte.MAX_VALUE);
    }

    @Test
    public void getInt_whenValueExists_thenReturnValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        Integer value = atomicAccess.getInt(objectWithFields, valueOffset);

        assertNotNull(value);
        assertEquals(objectWithFields.intValue, Integer.MIN_VALUE);
    }

    @Test
    public void putInt_whenPutNewValue_thenValueChanged() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        atomicAccess.putInt(objectWithFields, valueOffset, Integer.MAX_VALUE);

        assertNotEquals(objectWithFields.intValue, Integer.MIN_VALUE);
        assertEquals(objectWithFields.intValue, Integer.MAX_VALUE);
    }

    @Test
    public void compareAndSwapByte_whenCurrentValueEqualsExpected_thenSwapCurrentValueWithNewValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        atomicAccess.compareAndSwapByte(objectWithFields, valueOffset, Byte.MIN_VALUE, Byte.MAX_VALUE);

        assertNotEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
        assertEquals(objectWithFields.byteValue, Byte.MAX_VALUE);
    }

    @Test
    public void compareAndSwapInt_whenCurrentValueEqualsExpected_thenSwapCurrentValueWithNewValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        atomicAccess.compareAndSwapInt(objectWithFields, valueOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);

        assertNotEquals(objectWithFields.intValue, Integer.MIN_VALUE);
        assertEquals(objectWithFields.intValue, Integer.MAX_VALUE);
    }

    @Test
    public void getByteVolatile_whenValueExists_thenReturnValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        Byte value = atomicAccess.getByteVolatile(objectWithFields, valueOffset);

        assertNotNull(value);
        assertEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
    }

    @Test
    public void putByteVolatile_whenPutNewValue_thenValueChanged() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        atomicAccess.putByteVolatile(objectWithFields, valueOffset, Byte.MAX_VALUE);

        assertNotEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
        assertEquals(objectWithFields.byteValue, Byte.MAX_VALUE);
    }

    @Test
    public void getIntVolatile_whenValueExists_thenReturnValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        Integer value = atomicAccess.getIntVolatile(objectWithFields, valueOffset);

        assertNotNull(value);
        assertEquals(objectWithFields.intValue, Integer.MIN_VALUE);
    }

    @Test
    public void putIntVolatile_whenPutNewValue_thenValueChanged() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        atomicAccess.putIntVolatile(objectWithFields, valueOffset, Integer.MAX_VALUE);

        assertNotEquals(objectWithFields.intValue, Integer.MIN_VALUE);
        assertEquals(objectWithFields.intValue, Integer.MAX_VALUE);
    }

    @Test
    public void getAndAddByte_whenValueExists_thenAddDeltaAndReturnOldValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        Byte oldValue = atomicAccess.getAndAddByte(objectWithFields, valueOffset, Byte.MAX_VALUE);

        assertNotEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
        assertNotEquals(objectWithFields.byteValue, Byte.MAX_VALUE);
        assertEquals(objectWithFields.byteValue, Byte.MAX_VALUE + Byte.MIN_VALUE);
        assertEquals((byte) oldValue, Byte.MIN_VALUE);
    }

    @Test
    public void getAndAddInt_whenValueExists_thenAddDeltaAndReturnOldValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        Integer oldValue = atomicAccess.getAndAddInt(objectWithFields, valueOffset, Integer.MAX_VALUE);

        assertNotEquals(objectWithFields.intValue, Integer.MIN_VALUE);
        assertNotEquals(objectWithFields.intValue, Integer.MAX_VALUE);
        assertEquals(objectWithFields.intValue, Integer.MAX_VALUE + Integer.MIN_VALUE);
        assertEquals((int) oldValue, Integer.MIN_VALUE);
    }

    @Test
    public void getAndSetByte_whenValueExists_thenSetNewValueAndReturnOldValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = Byte.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");
        Byte oldValue = atomicAccess.getAndSetByte(objectWithFields, valueOffset, Byte.MAX_VALUE);

        assertNotEquals(objectWithFields.byteValue, Byte.MIN_VALUE);
        assertEquals(objectWithFields.byteValue, Byte.MAX_VALUE);
        assertEquals((byte) oldValue, Byte.MIN_VALUE);
    }

    @Test
    public void getAndSetInt_whenValueExists_thenSetNewValueAndReturnOldValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.intValue = Integer.MIN_VALUE;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "intValue");
        Integer oldValue = atomicAccess.getAndSetInt(objectWithFields, valueOffset, Integer.MAX_VALUE);

        assertNotEquals(objectWithFields.intValue, Integer.MIN_VALUE);
        assertEquals(objectWithFields.intValue, Integer.MAX_VALUE);
        assertEquals((int) oldValue, Integer.MIN_VALUE);
    }

    @Test
    public void getAndUpdateByte_whenValueExists_thenUpdateValueAndReturnOldValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = 1;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");

        Byte oldValue = atomicAccess.getAndUpdateByte(objectWithFields, valueOffset, newValue -> (byte) (newValue + 1));

        assertEquals((byte) oldValue, 1);
        assertEquals(objectWithFields.byteValue, 2);
    }

    @Test
    public void updateAndGetByte_whenValueExists_thenUpdateValueAndReturnNewValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = 1;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");

        Byte newValue = atomicAccess.updateAndGetByte(
            objectWithFields,
            valueOffset,
            prevValue -> (byte) (prevValue + 1)
        );

        assertEquals(objectWithFields.byteValue, 2);
        assertEquals((byte) newValue, 2);
    }

    @Test
    public void getAndAccumulateByte_whenValueExists_thenAccumulateValueAndReturnOldValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = 1;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");

        Byte oldValue = atomicAccess.getAndAccumulateByte(
            objectWithFields,
            valueOffset,
            (byte) 1,
            (currentValue, value) -> (byte) (currentValue + value)
        );

        assertEquals((byte) oldValue, 1);
        assertEquals(objectWithFields.byteValue, 2);
    }

    @Test
    public void accumulateAndGetByte_whenValueExists_thenAccumulateValueAndReturnNewValue() {
        ObjectWithFields objectWithFields = new ObjectWithFields();
        objectWithFields.byteValue = 1;
        long valueOffset = atomicAccess.objectFieldOffset(ObjectWithFields.class, "byteValue");

        Byte newValue = atomicAccess.accumulateAndGetByte(
            objectWithFields,
            valueOffset,
            (byte) 1,
            (currentValue, value) -> (byte) (currentValue + value)
        );

        assertEquals(objectWithFields.byteValue, 2);
        assertEquals((byte) newValue, 2);
    }

    static class ObjectWithFields {

        volatile byte byteValue;

        volatile int intValue;
    }
}