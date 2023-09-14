package io.github.ololx.moonshine.util;

import edu.umd.cs.mtc.MultithreadedTest;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

public class NonBlockingConcurrentBitSetMultithreadingTest extends MultithreadedTest {

    ConcurrentBitArray bitSet = new ConcurrentBitArray(3);

    @Test
    public void set_whenInvokeInManyThreadsAndManyTimes_thenAllChangesWereApplied() throws Throwable {
        assertTrue(checkInMultiThreading());
    }

    private static boolean checkInMultiThreading() throws Throwable {
        TestFramework.runManyTimes(new NonBlockingConcurrentBitSetMultithreadingTest(), 1000);
        return true;
    }

    public void thread1() throws InterruptedException {
        this.bitSet.set(1);
    }

    public void thread2() throws InterruptedException {
        this.bitSet.set(2);
    }

    @Override
    public void finish() {
        assertTrue(bitSet.get(1) && bitSet.get(2));
    }
}
