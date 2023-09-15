package io.github.ololx.moonshine.util.concurrent.atomic.wrapping;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 07.09.2023 22:09
 */
public class AtomicByteArrayWrapperTest {

    AtomicArrayWrapper<Byte> atomicByteArray;

    @BeforeMethod
    public void setUp() {
        atomicByteArray = new AtomicByteArrayWrapper(new byte[]{1, 2, 3, 4, 5});
    }

    @Test
    public void length_whenGetLength_thenReturnCorrectLength() {
        assertEquals(5, (byte) atomicByteArray.length());
    }

    @Test
    public void get_whenGet_thenCorrectValueReturned() {
        assertEquals(1, (byte) atomicByteArray.get(0));
    }

    @Test
    public void set_whenSet_thenValueSetCorrectly() {
        atomicByteArray.set(0, (byte) 9);

        assertEquals(9, (byte) atomicByteArray.get(0));
    }

    @Test
    public void getAndSet_whenSet_thenOldValueReturnedAndNewValueSet() {
        byte oldValue = atomicByteArray.getAndSet(0, (byte) 7);

        assertEquals(1, oldValue);
        assertEquals(7, (byte) atomicByteArray.get(0));
    }

    @Test
    public void compareAndSet_whenExpectMatches_thenValueSetAndReturnTrue() {
        assertTrue(atomicByteArray.compareAndSet(0, (byte) 1, (byte) 8));
        assertEquals(8, (byte) atomicByteArray.get(0));
    }

    @Test
    public void getAndIncrement_whenIncrement_thenIncrementValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndIncrement(0);

        assertEquals(1, oldValue);
        assertEquals(2, (byte) atomicByteArray.get(0));
    }

    @Test
    public void getAndDecrement_whenDecrement_thenDecrementValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndDecrement(1);

        assertEquals(2, oldValue);
        assertEquals(1, (byte) atomicByteArray.get(1));
    }

    @Test
    public void incrementAndGet_whenIncrement_thenIncrementValueAndReturnNewValue() {
        byte newValue = atomicByteArray.incrementAndGet(0);

        assertEquals(2, newValue);
    }

    @Test
    public void decrementAndGet_whenDecrement_thenDecrementValueAndReturnNewValue() {
        byte newValue = atomicByteArray.decrementAndGet(1);

        assertEquals(1, newValue);
    }

    @Test
    public void addAndGet_whenAdd_thenAddValueAndReturnNewValue() {
        byte newValue = atomicByteArray.addAndGet(1, (byte) 3);

        assertEquals(5, newValue);
    }

    @Test
    public void getAndAdd_whenAdd_thenAddValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndAdd(1, (byte) 2);

        assertEquals(2, oldValue);
        assertEquals(4, (byte) atomicByteArray.get(1));
    }

    @Test
    public void toString_whenGetString_thenReturnCorrectStringRepresentation() {
        assertEquals("[1, 2, 3, 4, 5]", atomicByteArray.toString());
    }
}