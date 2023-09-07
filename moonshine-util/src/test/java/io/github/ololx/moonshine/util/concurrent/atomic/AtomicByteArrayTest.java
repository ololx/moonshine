package io.github.ololx.moonshine.util.concurrent.atomic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 07.09.2023 22:09
 */
public class AtomicByteArrayTest {

    private AtomicByteArray atomicByteArray;

    @BeforeMethod
    public void setUp() {
        atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
    }

    @Test
    public void length_whenGetLength_thenReturnCorrectLength() {
        assertEquals(5, atomicByteArray.length());
    }

    @Test
    public void get_whenGet_thenCorrectValueReturned() {
        assertEquals(1, atomicByteArray.get(0));
    }

    @Test
    public void set_whenSet_thenValueSetCorrectly() {
        atomicByteArray.set(0, (byte) 9);

        assertEquals(9, atomicByteArray.get(0));
    }

    @Test
    public void getAndSet_whenSet_thenOldValueReturnedAndNewValueSet() {
        byte oldValue = atomicByteArray.getAndSet(0, (byte) 7);

        assertEquals(1, oldValue);
        assertEquals(7, atomicByteArray.get(0));
    }

    @Test
    public void compareAndSet_whenExpectMatches_thenValueSetAndReturnTrue() {
        assertTrue(atomicByteArray.compareAndSet(0, (byte) 1, (byte) 8));
        assertEquals(8, atomicByteArray.get(0));
    }

    @Test
    public void getAndIncrement_whenIncrement_thenIncrementValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndIncrement(0);

        assertEquals(1, oldValue);
        assertEquals(2, atomicByteArray.get(0));
    }

    @Test
    public void getAndDecrement_whenDecrement_thenDecrementValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndDecrement(1);

        assertEquals(2, oldValue);
        assertEquals(1, atomicByteArray.get(1));
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
        assertEquals(4, atomicByteArray.get(1));
    }

    @Test
    public void getAndUpdate_whenUpdate_thenUpdateValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndUpdate(0, x -> (byte) (x + 5));

        assertEquals(1, oldValue);
        assertEquals(6, atomicByteArray.get(0));
    }

    @Test
    public void updateAndGet_whenUpdate_thenUpdateValueAndReturnNewValue() {
        byte newValue = atomicByteArray.updateAndGet(0, x -> (byte) (x + 5));

        assertEquals(6, newValue);
    }

    @Test
    public void getAndAccumulate_whenAccumulate_thenAccumulateValueAndReturnOldValue() {
        byte oldValue = atomicByteArray.getAndAccumulate(0, (byte) 3, (x, y) -> (byte) (x * y));

        assertEquals(1, oldValue);
        assertEquals(3, atomicByteArray.get(0));
    }

    @Test
    public void accumulateAndGet_whenAccumulate_thenAccumulateValueAndReturnNewValue() {
        byte newValue = atomicByteArray.accumulateAndGet(0, (byte) 3, (x, y) -> (byte) (x * y));

        assertEquals(3, newValue);
    }

    @Test
    public void toString_whenGetString_thenReturnCorrectStringRepresentation() {
        assertEquals("[1, 2, 3, 4, 5]", atomicByteArray.toString());
    }
}
