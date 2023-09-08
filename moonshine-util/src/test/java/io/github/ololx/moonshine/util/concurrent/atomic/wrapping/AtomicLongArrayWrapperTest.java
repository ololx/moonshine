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
public class AtomicLongArrayWrapperTest {

    AtomicArrayWrapper<Long> atomicLongArray;

    @BeforeMethod
    public void setUp() {
        atomicLongArray = new AtomicLongArrayWrapper(new long[]{1, 2, 3, 4, 5});
    }

    @Test
    public void length_whenGetLength_thenReturnCorrectLength() {
        assertEquals(5, (long) atomicLongArray.length());
    }

    @Test
    public void get_whenGet_thenCorrectValueReturned() {
        assertEquals(1, (long) atomicLongArray.get(0));
    }

    @Test
    public void set_whenSet_thenValueSetCorrectly() {
        atomicLongArray.set(0, (long) 9);

        assertEquals(9, (long) atomicLongArray.get(0));
    }

    @Test
    public void getAndSet_whenSet_thenOldValueReturnedAndNewValueSet() {
        long oldValue = atomicLongArray.getAndSet(0, (long) 7);

        assertEquals(1, oldValue);
        assertEquals(7, (long) atomicLongArray.get(0));
    }

    @Test
    public void compareAndSet_whenExpectMatches_thenValueSetAndReturnTrue() {
        assertTrue(atomicLongArray.compareAndSet(0, (long) 1, (long) 8));
        assertEquals(8, (long) atomicLongArray.get(0));
    }

    @Test
    public void getAndIncrement_whenIncrement_thenIncrementValueAndReturnOldValue() {
        long oldValue = atomicLongArray.getAndIncrement(0);

        assertEquals(1, oldValue);
        assertEquals(2, (long) atomicLongArray.get(0));
    }

    @Test
    public void getAndDecrement_whenDecrement_thenDecrementValueAndReturnOldValue() {
        long oldValue = atomicLongArray.getAndDecrement(1);

        assertEquals(2, oldValue);
        assertEquals(1, (long) atomicLongArray.get(1));
    }

    @Test
    public void incrementAndGet_whenIncrement_thenIncrementValueAndReturnNewValue() {
        long newValue = atomicLongArray.incrementAndGet(0);

        assertEquals(2, newValue);
    }

    @Test
    public void decrementAndGet_whenDecrement_thenDecrementValueAndReturnNewValue() {
        long newValue = atomicLongArray.decrementAndGet(1);

        assertEquals(1, newValue);
    }

    @Test
    public void addAndGet_whenAdd_thenAddValueAndReturnNewValue() {
        long newValue = atomicLongArray.addAndGet(1, (long) 3);

        assertEquals(5, newValue);
    }

    @Test
    public void getAndAdd_whenAdd_thenAddValueAndReturnOldValue() {
        long oldValue = atomicLongArray.getAndAdd(1, (long) 2);

        assertEquals(2, oldValue);
        assertEquals(4, (long) atomicLongArray.get(1));
    }

    @Test
    public void toString_whenGetString_thenReturnCorrectStringRepresentation() {
        assertEquals("[1, 2, 3, 4, 5]", atomicLongArray.toString());
    }
}
