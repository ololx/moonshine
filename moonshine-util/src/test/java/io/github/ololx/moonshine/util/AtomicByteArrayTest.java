package io.github.ololx.moonshine.util;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.Assert.*;

public class AtomicByteArrayTest {

    @Test
    public void length_whenArrayIsCreated_thenReturnLength() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(12);
        Assert.assertEquals(atomicByteArray.length(), 12);
    }

    @Test
    public void set_whenSetNewValueInArray_thenArrayContainsThisValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(1);
        atomicByteArray.set(0, (byte) 12);
        Assert.assertEquals(atomicByteArray.get(0), 12);
    }

    @Test
    public void compareAndSet_whenCompareAndSetNewValue_thenArrayContainsThisValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(3);
        atomicByteArray.compareAndSet(0, (byte) 0, Byte.MAX_VALUE);
        Assert.assertEquals(atomicByteArray.get(0), Byte.MAX_VALUE);
        Assert.assertEquals(atomicByteArray.get(1), 0);
        Assert.assertEquals(atomicByteArray.get(2), 0);
    }
}