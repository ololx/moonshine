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
import java.util.Objects;

/**
 * project moonshine
 * created 01.04.2023 18:42
 *
 * @author Alexander A. Kropotin
 */
public class ThreadMemoryAllocationMeter implements Measurer<Memory> {

    private final ThreadMXBean threadMXBean;

    private long startUsedMemory;

    private long endUsedMemory;

    public ThreadMemoryAllocationMeter() {
        this.threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    }

    ThreadMemoryAllocationMeter(ThreadMXBean threadMXBean) {
        this.threadMXBean = Objects.requireNonNull(
                threadMXBean,
                "The thread MX bean must be not null"
        );
    }

    @Override
    public ThreadMemoryAllocationMeter start() {
        startUsedMemory = threadMXBean.getThreadAllocatedBytes(Thread.currentThread().getId());

        return this;
    }

    @Override
    public ThreadMemoryAllocationMeter stop() {
        endUsedMemory = threadMXBean.getThreadAllocatedBytes(Thread.currentThread().getId());

        return this;
    }

    @Override
    public Memory result() {
        return Memory.ofBytes(endUsedMemory - startUsedMemory);
    }
}
