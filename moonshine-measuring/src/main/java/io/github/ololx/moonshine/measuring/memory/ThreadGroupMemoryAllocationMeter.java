/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <br/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.measuring.memory;

import com.sun.management.ThreadMXBean;
import io.github.ololx.moonshine.measuring.Measurer;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Objects;

/**
 * A Measurer implementation that measures the amount of memory allocated by
 * the whole application during the execution of some code. This implementation
 * uses the MemoryMXBean to measure the amount of allocated bytes before and
 * after the code execution.
 *
 * @implNote
 * This implementation tracks the heap and non-heap memory usage of the JVM
 * before  and after the measurement period, then calculates the difference to
 * determine the total memory allocation.
 *
 * @apiNote
 * The returned {@link Memory} object represents the total memory allocation
 * during the measurement period.
 *
 * * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * WholeMemoryAllocationMeter meter = new WholeMemoryAllocationMeter();
 *
 * meter.start();
 * // execute some code here
 * meter.stop();
 *
 * Memory allocatedMemory = meter.result();
 * }</pre>
 *
 * project moonshine
 * created 01.04.2023 18:42
 *
 * @author Alexander A. Kropotin
 */
public class ThreadGroupMemoryAllocationMeter implements Measurer<Memory> {

    /**
     * The ThreadMXBean instance used to obtain memory allocation information.
     */
    private final ThreadMXBean threadMXBean;

    /**
     * The amount of memory used by the thread at the start of the measurement
     * period.
     */
    private long startUsedMemory;

    /**
     * The amount of memory used by the thread at the end of the measurement
     * period.
     */
    private long endUsedMemory;

    /**
     * Creates a new instance of {@link ThreadGroupMemoryAllocationMeter} that uses
     * the default {@link ThreadMXBean}.
     */
    public ThreadGroupMemoryAllocationMeter() {
        threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    }

    /**
     * Creates a new instance of {@link ThreadGroupMemoryAllocationMeter} that uses
     * the specified {@link ThreadMXBean}.
     *
     * @param threadMXBean the {@link ThreadMXBean} to use for memory usage
     * information
     * @throws NullPointerException if {@code threadMXBean} is {@code null}
     */
    ThreadGroupMemoryAllocationMeter(ThreadMXBean threadMXBean) {
        this.threadMXBean = Objects.requireNonNull(
                threadMXBean,
                "The thread MX bean must not be null"
        );
    }

    /**
     * Starts the memory allocation measurement period by capturing the
     * memory usage of all threads.
     *
     * <p>This method should be called before the code whose memory allocation
     * is to be measured.</p>
     *
     * @return this {@link ThreadGroupMemoryAllocationMeter} instance
     */
    @Override
    public ThreadGroupMemoryAllocationMeter start() {
        this.startUsedMemory = 0L;

        Thread[] threads = getAllThreads();
        for (Thread thread : threads) {
            if (thread == null) {
                continue;
            }

            this.endUsedMemory += threadMXBean.getThreadAllocatedBytes(thread.getId());
        }

        return this;
    }

    /**
     * Stops the memory allocation measurement period by capturing the
     * memory usage of all threads.
     *
     * <p>This method should be called after the code whose memory allocation
     * is to be measured.</p>
     *
     * @return this {@link ThreadGroupMemoryAllocationMeter} instance
     */
    @Override
    public ThreadGroupMemoryAllocationMeter stop() {
        this.endUsedMemory = 0L;

        Thread[] threads = getAllThreads();
        for (Thread thread : threads) {
            if (thread == null) {
                continue;
            }

            this.endUsedMemory += threadMXBean.getThreadAllocatedBytes(thread.getId());
        }

        return this;
    }

    private static Thread[] getAllThreads() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        /*while (group.getParent() != null) {
            group = group.getParent();
        }*/

        int estimatedSize = group.activeCount();
        Thread[] threads = new Thread[estimatedSize];
        int actualSize = group.enumerate(threads);

        return Arrays.copyOf(threads, actualSize);
    }


    /**
     * Calculates and returns the total memory allocation during the
     * measurement period.
     *
     * <p>This method should be called after {@link #stop()} method.</p>
     *
     * @return a {@link Memory} object representing the total memory allocation
     * during the measurement period
     */
    @Override
    public Memory result() {
        return Memory.ofBytes(this.endUsedMemory - this.startUsedMemory);
    }
}
