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
import java.util.Objects;

/**
 * A Measurer implementation that measures the amount of memory allocated by
 * the current thread during the execution of some code. This implementation
 * uses the ThreadMXBean to measure the amount of allocated bytes before and
 * after the code execution.
 *
 * @implSpec
 * This implementation uses the {@link ThreadMXBean
 * #getThreadAllocatedBytes(long)} method to measure the amount of allocated
 * bytes before and after the code execution.
 *
 * @implNote
 * This implementation measures only the amount of memory allocated by the
 * current thread. To measure the memory allocation of multiple threads, use
 * a different implementation of the {@link Measurer} interface.
 *
 * * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * ThreadMemoryAllocationMeter meter = new ThreadMemoryAllocationMeter();
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
public class ThreadMemoryAllocationMeter implements Measurer<Memory> {

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
     * Creates a new instance of the ThreadMemoryAllocationMeter class.
     *
     * <p>This constructor uses the default ThreadMXBean instance obtained from
     * the {@link ManagementFactory#getThreadMXBean()} method.</p>
     */
    public ThreadMemoryAllocationMeter() {
        this.threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    }

    /**
     * Creates a new instance of the ThreadMemoryAllocationMeter class using
     * the specified ThreadMXBean instance.
     *
     * @param threadMXBean the ThreadMXBean instance to use
     * @throws NullPointerException if threadMXBean is null
     */
    ThreadMemoryAllocationMeter(ThreadMXBean threadMXBean) {
        this.threadMXBean = Objects.requireNonNull(
                threadMXBean,
                "The thread MX bean must be not null"
        );
    }

    /**
     * Marks the beginning of the measurement period.
     *
     * <p>This method should be called before the code whose memory allocation
     * is to be measured.</p>
     *
     * @return this ThreadMemoryAllocationMeter instance
     */
    @Override
    public ThreadMemoryAllocationMeter start() {
        startUsedMemory = threadMXBean.getThreadAllocatedBytes(Thread.currentThread().getId());

        return this;
    }

    /**
     * Marks the end of the measurement period.
     *
     * <p>This method should be called after the code whose memory allocation
     * is to be measured.</p>
     *
     * @return this ThreadMemoryAllocationMeter instance
     */
    @Override
    public ThreadMemoryAllocationMeter stop() {
        endUsedMemory = threadMXBean.getThreadAllocatedBytes(Thread.currentThread().getId());

        return this;
    }

    /**
     * Returns the amount of memory allocated by the thread during the
     * measurement period.
     *
     * @return a Memory object representing the amount of memory allocated
     * by the thread during the measurement period
     */
    @Override
    public Memory result() {
        return Memory.ofBytes(endUsedMemory - startUsedMemory);
    }
}
