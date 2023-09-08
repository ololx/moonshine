package io.github.ololx.moonshine.util;

import edu.umd.cs.mtc.MultithreadedTest;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

public class NonBlockingConcurrentBitSetMultithreadingTest extends MultithreadedTest {

    NonBlockingConcurrentBitSet bitSet;

    @Override
    public void initialize() {
        this.bitSet = new NonBlockingConcurrentBitSet(2);
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

    @Test
    public void set_get_test() throws Throwable {
        TestFramework.runManyTimes(new NonBlockingConcurrentBitSetMultithreadingTest(), 1000);
    }
}
