package io.github.ololx.moonshine.util;

import io.github.ololx.moonshine.measuring.memory.WholeMemoryAllocationMeter;
import io.github.ololx.moonshine.util.concurrent.atomic.AtomicByteArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicByteArrayTest {

    @Test
    public void length_whenArrayIsCreated_thenReturnLength() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(12);
        Assert.assertEquals(atomicByteArray.length(), 12);
    }

    @Test
    public void length2_whenArrayIsCreated_thenReturnLength() {
        WholeMemoryAllocationMeter memoryAllocationMeter = new WholeMemoryAllocationMeter().start();
        AtomicByteArray atomicByteArray = new AtomicByteArray(1_000);
        memoryAllocationMeter.stop();
        System.out.print("B = " + memoryAllocationMeter.result());

        memoryAllocationMeter.start();
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(1_000);
        memoryAllocationMeter.stop();
        System.out.print("I = " + memoryAllocationMeter.result());

        memoryAllocationMeter.start();
        AtomicLongArray atomicLongArray = new AtomicLongArray(1_000);
        memoryAllocationMeter.stop();
        System.out.print("L = " + memoryAllocationMeter.result());
    }

    @Test
    public void set_whenSetNewValueInArray_thenArrayContainsThisValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(1);
        atomicByteArray.set(0, (byte) 12);
        Assert.assertEquals(atomicByteArray.get(0), 12);
    }

    @Test
    public void compareAndSet_whenCompareAndSetNewValue_thenArrayContainsThisValue() {
        AtomicByteArray atomicByteArray = new AtomicByteArray(1);
        System.out.print(atomicByteArray);
        //atomicByteArray.set(1, (byte) 12);
        //atomicByteArray.set(2, (byte) 13);
        atomicByteArray.compareAndSet(0, (byte) 0, Byte.MIN_VALUE);
        System.out.print(atomicByteArray);
        Assert.assertEquals(atomicByteArray.get(0), Byte.MIN_VALUE);
    }
}