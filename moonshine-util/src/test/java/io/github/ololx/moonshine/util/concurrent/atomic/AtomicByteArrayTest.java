package io.github.ololx.moonshine.util.concurrent.atomic;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 07.09.2023 22:09
 */
public class AtomicByteArrayTest {

    @Test
    public void length_whenGetLength_thenReturnCorrectLength() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        assertEquals(5, atomicByteArray.length());
    }

    @Test
    public void get_whenGet_thenCorrectValueReturned() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        assertEquals(1, atomicByteArray.get(0));
    }

    @Test
    public void set_whenSet_thenValueSetCorrectly() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        atomicByteArray.set(0, (byte) 9);

        assertEquals(9, atomicByteArray.get(0));
    }

    @Test
    public void getAndSet_whenSet_thenOldValueReturnedAndNewValueSet() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte oldValue = atomicByteArray.getAndSet(0, (byte) 7);

        assertEquals(1, oldValue);
        assertEquals(7, atomicByteArray.get(0));
    }

    @Test
    public void compareAndSet_whenExpectMatches_thenValueSetAndReturnTrue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});

        assertTrue(atomicByteArray.compareAndSet(0, (byte) 1, (byte) 8));
        assertEquals(8, atomicByteArray.get(0));
    }

    @Test
    public void getAndIncrement_whenIncrement_thenIncrementValueAndReturnOldValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte oldValue = atomicByteArray.getAndIncrement(0);

        assertEquals(1, oldValue);
        assertEquals(2, atomicByteArray.get(0));
    }

    @Test
    public void getAndDecrement_whenDecrement_thenDecrementValueAndReturnOldValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte oldValue = atomicByteArray.getAndDecrement(1);

        assertEquals(2, oldValue);
        assertEquals(1, atomicByteArray.get(1));
    }

    @Test
    public void incrementAndGet_whenIncrement_thenIncrementValueAndReturnNewValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte newValue = atomicByteArray.incrementAndGet(0);

        assertEquals(2, newValue);
    }

    @Test
    public void decrementAndGet_whenDecrement_thenDecrementValueAndReturnNewValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte newValue = atomicByteArray.decrementAndGet(1);

        assertEquals(1, newValue);
    }

    @Test
    public void addAndGet_whenAdd_thenAddValueAndReturnNewValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte newValue = atomicByteArray.addAndGet(1, (byte) 3);

        assertEquals(5, newValue);
    }

    @Test
    public void getAndAdd_whenAdd_thenAddValueAndReturnOldValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte oldValue = atomicByteArray.getAndAdd(1, (byte) 2);

        assertEquals(2, oldValue);
        assertEquals(4, atomicByteArray.get(1));
    }

    @Test
    public void getAndUpdate_whenUpdate_thenUpdateValueAndReturnOldValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte oldValue = atomicByteArray.getAndUpdate(0, x -> (byte) (x + 5));

        assertEquals(1, oldValue);
        assertEquals(6, atomicByteArray.get(0));
    }

    @Test
    public void updateAndGet_whenUpdate_thenUpdateValueAndReturnNewValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte newValue = atomicByteArray.updateAndGet(0, x -> (byte) (x + 5));

        assertEquals(6, newValue);
    }

    @Test
    public void getAndAccumulate_whenAccumulate_thenAccumulateValueAndReturnOldValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte oldValue = atomicByteArray.getAndAccumulate(0, (byte) 3, (x, y) -> (byte) (x * y));

        assertEquals(1, oldValue);
        assertEquals(3, atomicByteArray.get(0));
    }

    @Test
    public void accumulateAndGet_whenAccumulate_thenAccumulateValueAndReturnNewValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        byte newValue = atomicByteArray.accumulateAndGet(0, (byte) 3, (x, y) -> (byte) (x * y));

        assertEquals(3, newValue);
    }

    @Test
    public void toString_whenGetString_thenReturnCorrectStringRepresentation() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
        assertEquals("[1, 2, 3, 4, 5]", atomicByteArray.toString());
    }
}
