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
 * the whole application during the execution of some code. This implementation
 * uses the MemoryMXBean to measure the amount of allocated bytes before and
 * after the code execution.
 *
 * @author Alexander A. Kropotin
 * @implNote This implementation tracks the total memory usage of the JVM before and
 * after the measurement period, then calculates the difference to determine
 * the total memory allocation.
 * @apiNote The returned {@link Memory} object represents the total memory allocation
 * during the measurement period.
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * WholeMemoryAllocationMeter meter = new WholeMemoryAllocationMeter();
 *
 * meter.start();
 * // execute some code here
 * meter.stop();
 *
 * Memory allocatedMemory = meter.result();
 * }</pre>
 * <p>
 * project moonshine
 * created 01.04.2023 18:42
 */
public class WholeMemoryAllocationMeter implements Measurer<Memory> {

    /**
     * The ThreadMXBean instance used to obtain memory allocation information.
     */
    private final ThreadMXBean threadMXBean;

    /**
     * The amount of memory used by the all threads at the start of the
     * measurement period.
     */
    private long startUsedMemory;

    /**
     * The amount of memory used by the all threads at the end of the
     * measurement period.
     */
    private long endUsedMemory;

    /**
     * Creates a new instance of {@link WholeMemoryAllocationMeter} that uses
     * the default {@link ThreadMXBean}.
     */
    public WholeMemoryAllocationMeter() {
        threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    }

    /**
     * Creates a new instance of {@link WholeMemoryAllocationMeter} that uses
     * the specified {@link ThreadMXBean}.
     *
     * @param threadMXBean the {@link ThreadMXBean} to use for memory usage
     *                     information
     * @throws NullPointerException if {@code threadMXBean} is {@code null}
     */
    WholeMemoryAllocationMeter(ThreadMXBean threadMXBean) {
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
     * @return this {@link WholeMemoryAllocationMeter} instance
     */
    @Override
    public WholeMemoryAllocationMeter start() {
        this.startUsedMemory = 0L;

        long[] threadIds = threadMXBean.getAllThreadIds();
        for (long threadId : threadIds) {
            this.startUsedMemory += this.threadMXBean.getThreadAllocatedBytes(threadId);
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
     * @return this {@link WholeMemoryAllocationMeter} instance
     */
    @Override
    public WholeMemoryAllocationMeter stop() {
        this.endUsedMemory = 0L;

        long[] threadIds = threadMXBean.getAllThreadIds();
        for (long threadId : threadIds) {
            this.endUsedMemory += threadMXBean.getThreadAllocatedBytes(threadId);
        }

        return this;
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
