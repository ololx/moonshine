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

package io.github.ololx.moonshine.measuring.cpu;

import com.sun.management.OperatingSystemMXBean;
import io.github.ololx.moonshine.measuring.Measurer;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.Objects;

/**
 * Measures the CPU usage of a process during a certain period of time.
 *
 * <p>This class implements the Measurer interface to measure the CPU usage of
 * a process during a certain period of time.</p>
 *
 * @author Alexander A. Kropotin
 * @implSpec <ul>
 *     <li>The CPU usage is measured in nanoseconds.</li>
 *     <li>The measurement period starts and ends when the {@link #start()} and
 *     {@link #stop()} methods are called, respectively.</li>
 *     </ul>
 * @apiNote This implementation measures the CPU usage by the current process.
 *     To measure the CPU usage of any thread, use a different implementation
 *     of the {@link Measurer} interface.
 *
 *     <p><strong>Example usage:</strong></p>
 *     <pre>{@code
 *     ThreadCpuUsageMeter meter = new ThreadCpuUsageMeter();
 *
 *     meter.start();
 *     // Some code to be measured
 *     meter.stop();
 *
 *     CpuUsage result = meter.result();
 *     }</pre>
 *
 *     project moonshine
 *     created 13.04.2023 14:29
 */
public class ProcessCpuUsageMeter implements Measurer<CpuUsage> {

    /**
     * The const of state "is not running"
     */
    private static final long STOPPED = -1;

    /**
     * The OperatingSystemMXBean instance used to obtain CPU load information.
     */
    private final OperatingSystemMXBean systemMXBean;

    /**
     * The CPU time at the start of the measurement period.
     */
    private long startCpuTime;

    /**
     * The CPU time at the end of the measurement period.
     */
    private long endCpuTime;

    /**
     * The wall-clock time at the start of the measurement period.
     */
    private long startMeasuringTime;

    /**
     * The wall-clock time at the end of the measurement period.
     */
    private long endMeasuringTime;

    /**
     * Creates a new instance of the ThreadCpuUsageMeter class.
     *
     * <p>This constructor uses the default OperatingSystemMXBean instance obtained from
     * the {@link ManagementFactory#getOperatingSystemMXBean()} method.</p>
     */
    public ProcessCpuUsageMeter() {
        this.systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

    /**
     * Creates a new instance of the ThreadCpuUsageMeter class using
     * the specified OperatingSystemMXBean instance.
     *
     * @param systemMXBean the OperatingSystemMXBean instance to use
     *
     * @throws NullPointerException if operatingSystemMXBean is null
     */
    public ProcessCpuUsageMeter(OperatingSystemMXBean systemMXBean) {
        this.systemMXBean = Objects.requireNonNull(
            systemMXBean,
            "The operatingSystemMXBean must not be null"
        );
        this.startCpuTime = STOPPED;
    }

    /**
     * Marks the beginning of the measurement period.
     *
     * <p>This method should be called before the code whose CPU usage
     * is to be measured.</p>
     *
     * @return this ThreadCpuUsageMeter instance
     */
    @Override
    public ProcessCpuUsageMeter start() {
        this.startCpuTime = this.systemMXBean.getProcessCpuTime();
        this.startMeasuringTime = System.nanoTime();

        return this;
    }

    /**
     * Marks the end of the measurement period.
     *
     * <p>This method should be called after the code whose CPU usage
     * is to be measured.</p>
     *
     * @return this ThreadCpuUsageMeter instance
     */
    @Override
    public ProcessCpuUsageMeter stop() {
        this.endCpuTime = this.systemMXBean.getProcessCpuTime();
        this.endMeasuringTime = System.nanoTime();

        return this;
    }

    /**
     * Returns the CPU usage during the measurement period, as a
     * {@link CpuUsage} object.
     *
     * <p>This method should be called after {@link #stop()} method.</p>
     *
     * @return a {@link CpuUsage} object representing the CPU usage during the
     *     measurement period.
     */
    @Override
    public CpuUsage result() {
        if (endCpuTime - startCpuTime < 0 || endMeasuringTime - startMeasuringTime < 0) {
            return CpuUsage.ZERO;
        }

        return CpuUsage.ofUsageTime(
            Duration.ofNanos(endCpuTime - startCpuTime),
            Duration.ofNanos(endMeasuringTime - startMeasuringTime)
        );
    }
}
