/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ololx.moonshine.measuring.cpu;

import io.github.ololx.moonshine.measuring.Measurer;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Objects;

public class CPULoadMeasurer implements Measurer<Double> {

    /**
     * The ThreadMXBean instance used to obtain CPU load information.
     */
    private final ThreadMXBean threadMXBean;

    /**
     * The CPU time used by the thread at the start of the measurement period.
     */
    private long startCpuTime;

    /**
     * The CPU time used by the thread at the end of the measurement period.
     */
    private long endCpuTime;

    /**
     * The ID of the thread to measure.
     */
    private final long threadId;

    /**
     * Creates a new instance of the ThreadCPULoadMeter class.
     *
     * <p>This constructor uses the default ThreadMXBean instance obtained from
     * the {@link ManagementFactory#getThreadMXBean()} method.</p>
     *
     * @param threadId the ID of the thread to measure
     */
    public CPULoadMeasurer(long threadId) {
        this.threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        this.threadId = threadId;
    }

    /**
     * Creates a new instance of the ThreadCPULoadMeter class using
     * the specified ThreadMXBean instance.
     *
     * @param threadMXBean the ThreadMXBean instance to use
     * @param threadId the ID of the thread to measure
     * @throws NullPointerException if threadMXBean is null
     */
    CPULoadMeasurer(ThreadMXBean threadMXBean, long threadId) {
        this.threadMXBean = Objects.requireNonNull(
                threadMXBean,
                "The thread MX bean must be not null"
        );
        this.threadId = threadId;
    }

    /**
     * Marks the beginning of the measurement period.
     *
     * <p>This method should be called before the code whose CPU load
     * is to be measured.</p>
     *
     * @return this ThreadCPULoadMeter instance
     */
    @Override
    public CPULoadMeasurer start() {
        this.startCpuTime = this.threadMXBean.getThreadCpuTime(threadId);

        return this;
    }

    /**
     * Marks the end of the measurement period.
     *
     * <p>This method should be called after the code whose CPU load
     * is to be measured.</p>
     *
     * @return this ThreadCPULoadMeter instance
     */
    @Override
    public CPULoadMeasurer stop() {
        this.endCpuTime = this.threadMXBean.getThreadCpuTime(threadId);

        return this;
    }

    /**
     * Returns the CPU load during the measurement period as a percentage of
     * the total CPU time available to the thread.
     *
     * <p>This method should be called after {@link #stop()} method.</p>
     *
     * @return a Double object representing the CPU load during the measurement period
     */
    @Override
    public Double result() {
        long totalCpuTime = this.threadMXBean.getCurrentThreadCpuTime() - this.startCpuTime;
        long threadCpuTime = this.endCpuTime - this.startCpuTime;
        double cpuLoad = (double) threadCpuTime / (double) totalCpuTime;

        return cpuLoad * 100.0;
    }
}
