package io.github.ololx.moonshine.util;

import edu.umd.cs.mtc.MultithreadedTest;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

public class ConcurrentBitSetMultithreadingTest extends MultithreadedTest {

    private NonBlockingConcurrentBitSet nonBlockingConcurrentBitset;

    @Override
    public void initialize() {
        this.nonBlockingConcurrentBitset = new NonBlockingConcurrentBitSet(10);
    }

    public void thread1() throws InterruptedException {
        this.nonBlockingConcurrentBitset.set(1);
    }

    public void thread2() throws InterruptedException {
        this.nonBlockingConcurrentBitset.set(2);
    }

    @Override
    public void finish() {
        assertTrue(nonBlockingConcurrentBitset.get(1) && nonBlockingConcurrentBitset.get(2));
    }

    @Test
    public void set_get_test() throws Throwable {
        TestFramework.runManyTimes(new ConcurrentBitSetMultithreadingTest(), 1000);
    }
}
