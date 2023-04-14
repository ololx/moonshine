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
import com.sun.management.ThreadMXBean;
import io.github.ololx.moonshine.measuring.Measurer;

import java.lang.management.ManagementFactory;
import java.util.Objects;

/**
 * project moonshine
 * created 13.04.2023 14:29
 *
 * @author Alexander A. Kropotin
 */
public class ThreadCPUTimeMeter implements Measurer<Double> {

    /**
     * The ThreadMXBean instance used to obtain CPU load information.
     */
    private final ThreadMXBean mxTBean;

    /**
     * The CPU load at the start of the measurement period.
     */
    private double startCpuLoad;
    private long startCpuLoadT;

    /**
     * The CPU load at the end of the measurement period.
     */
    private double endCpuLoad;
    private long endCpuLoadT;

    /**
     * Creates a new instance of the ThreadCPULoadMeter class.
     *
     * <p>This constructor uses the default OperatingSystemMXBean instance obtained from
     * the {@link ManagementFactory#getOperatingSystemMXBean()} method.</p>
     */
    public ThreadCPUTimeMeter() {
        this.mxTBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    }

    /**
     * Creates a new instance of the ThreadCPULoadMeter class using
     * the specified OperatingSystemMXBean instance.
     *
     * @param osBean the OperatingSystemMXBean instance to use
     * @throws NullPointerException if osBean is null
     */
    ThreadCPUTimeMeter(ThreadMXBean osBean) {
        this.mxTBean = Objects.requireNonNull(
                osBean,
                "The OS MX bean must be not null"
        );
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
    public ThreadCPUTimeMeter start() {
        this.startCpuLoad = this.mxTBean.getThreadCpuTime(Thread.currentThread().getId());
        this.startCpuLoadT = System.nanoTime();

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
    public ThreadCPUTimeMeter stop() {
        this.endCpuLoad = this.mxTBean.getThreadCpuTime(Thread.currentThread().getId());
        this.endCpuLoadT = System.nanoTime();

        return this;
    }

    /**
     * Returns the CPU load during the measurement period, as a percentage.
     *
     * <p>This method should be called after {@link #stop()} method.</p>
     *
     * @return a Double object representing the CPU load during the measurement period, as a percentage
     */
    @Override
    public Double result() {
        return endCpuLoadT > startCpuLoadT ? ((endCpuLoad - startCpuLoad) * 100L) / (endCpuLoadT - startCpuLoadT) / Runtime.getRuntime().availableProcessors(): 0;
    }
}
