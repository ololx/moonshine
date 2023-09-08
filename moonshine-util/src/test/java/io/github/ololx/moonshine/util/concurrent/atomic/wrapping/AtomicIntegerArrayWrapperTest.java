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
public class AtomicIntegerArrayWrapperTest {

    AtomicIntegerArrayWrapper atomicIntegerArray;

    @BeforeMethod
    public void setUp() {
        atomicIntegerArray = new AtomicIntegerArrayWrapper(new int[]{1, 2, 3, 4, 5});
    }

    @Test
    public void length_whenGetLength_thenReturnCorrectLength() {
        assertEquals(5, atomicIntegerArray.length());
    }

    @Test
    public void get_whenGet_thenCorrectValueReturned() {
        assertEquals(1, (int) atomicIntegerArray.get(0));
    }

    @Test
    public void set_whenSet_thenValueSetCorrectly() {
        atomicIntegerArray.set(0, 9);

        assertEquals(9, (int) atomicIntegerArray.get(0));
    }

    @Test
    public void getAndSet_whenSet_thenOldValueReturnedAndNewValueSet() {
        int oldValue = atomicIntegerArray.getAndSet(0, 7);

        assertEquals(1, oldValue);
        assertEquals(7, (int) atomicIntegerArray.get(0));
    }

    @Test
    public void compareAndSet_whenExpectMatches_thenValueSetAndReturnTrue() {
        assertTrue(atomicIntegerArray.compareAndSet(0, 1, 8));
        assertEquals(8, (int) atomicIntegerArray.get(0));
    }

    @Test
    public void getAndIncrement_whenIncrement_thenIncrementValueAndReturnOldValue() {
        int oldValue = atomicIntegerArray.getAndIncrement(0);

        assertEquals(1, oldValue);
        assertEquals(2, (int) atomicIntegerArray.get(0));
    }

    @Test
    public void getAndDecrement_whenDecrement_thenDecrementValueAndReturnOldValue() {
        int oldValue = atomicIntegerArray.getAndDecrement(1);

        assertEquals(2, oldValue);
        assertEquals(1, (int) atomicIntegerArray.get(1));
    }

    @Test
    public void incrementAndGet_whenIncrement_thenIncrementValueAndReturnNewValue() {
        int newValue = atomicIntegerArray.incrementAndGet(0);

        assertEquals(2, newValue);
    }

    @Test
    public void decrementAndGet_whenDecrement_thenDecrementValueAndReturnNewValue() {
        int newValue = atomicIntegerArray.decrementAndGet(1);

        assertEquals(1, newValue);
    }

    @Test
    public void addAndGet_whenAdd_thenAddValueAndReturnNewValue() {
        int newValue = atomicIntegerArray.addAndGet(1, 3);

        assertEquals(5, newValue);
    }

    @Test
    public void getAndAdd_whenAdd_thenAddValueAndReturnOldValue() {
        int oldValue = atomicIntegerArray.getAndAdd(1, 2);

        assertEquals(2, oldValue);
        assertEquals(4, (int) atomicIntegerArray.get(1));
    }

    @Test
    public void toString_whenGetString_thenReturnCorrectStringRepresentation() {
        assertEquals("[1, 2, 3, 4, 5]", atomicIntegerArray.toString());
    }
}
