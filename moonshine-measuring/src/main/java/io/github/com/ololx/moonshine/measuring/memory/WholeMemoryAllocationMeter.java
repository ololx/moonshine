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

package io.github.com.ololx.moonshine.measuring.memory;

import com.sun.management.ThreadMXBean;
import io.github.com.ololx.moonshine.measuring.Measurer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
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
public class WholeMemoryAllocationMeter implements Measurer<Memory> {

    private final MemoryMXBean memoryMXBean;

    private MemoryUsage startHeapMemoryUsage;

    private MemoryUsage startMemoryUsage;

    private MemoryUsage endHeapMemoryUsage;

    private MemoryUsage endMemoryUsage;

    /**
     * Creates a new instance of {@link WholeMemoryAllocationMeter} that uses
     * the default {@link MemoryMXBean}.
     */
    public WholeMemoryAllocationMeter() {
        memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    /**
     * Creates a new instance of {@link WholeMemoryAllocationMeter} that uses
     * the specified {@link MemoryMXBean}.
     *
     * @param memoryMXBean the {@link MemoryMXBean} to use for memory usage
     * information
     * @throws NullPointerException if {@code memoryMXBean} is {@code null}
     */
    WholeMemoryAllocationMeter(MemoryMXBean memoryMXBean) {
        this.memoryMXBean = Objects.requireNonNull(
                memoryMXBean,
                "The memory MX bean must be not null"
        );
    }

    /**
     * Starts the memory allocation measurement period by capturing the heap
     * and non-heap memory usage of the JVM.
     *
     * <p>This method should be called before the code whose memory allocation
     * is to be measured.</p>
     *
     * @return this {@link WholeMemoryAllocationMeter} instance
     */
    @Override
    public WholeMemoryAllocationMeter start() {
        startHeapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        startMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        return this;
    }

    /**
     * Stops the memory allocation measurement period by capturing the heap and
     * non-heap memory usage of the JVM.
     *
     * <p>This method should be called after the code whose memory allocation
     * is to be measured.</p>
     *
     * @return this {@link WholeMemoryAllocationMeter} instance
     */
    @Override
    public WholeMemoryAllocationMeter stop() {
        endHeapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        endMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        return this;
    }

    /**
     * Calculates and returns the total memory allocation during the
     * measurement period.
     *
     * @return a {@link Memory} object representing the total memory allocation
     * during the measurement period
     */
    @Override
    public Memory result() {
        long usedHeapMemory = endHeapMemoryUsage.getUsed() - startHeapMemoryUsage.getUsed();
        long usedMemory = endMemoryUsage.getUsed() - startMemoryUsage.getUsed();

        return Memory.ofBytes(usedHeapMemory).plus(Memory.ofBytes(usedMemory));
    }
}
