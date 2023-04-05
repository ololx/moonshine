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

import io.github.com.ololx.moonshine.measuring.Measurer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Objects;

/**
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

    public WholeMemoryAllocationMeter() {
        memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    WholeMemoryAllocationMeter(MemoryMXBean memoryMXBean) {
        this.memoryMXBean = Objects.requireNonNull(
                memoryMXBean,
                "The memory MX bean must be not null"
        );
    }

    @Override
    public WholeMemoryAllocationMeter start() {
        startHeapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        startMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        return this;
    }

    @Override
    public WholeMemoryAllocationMeter stop() {
        endHeapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        endMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        return this;
    }

    @Override
    public Memory result() {
        long usedHeapMemory = endHeapMemoryUsage.getUsed() - startHeapMemoryUsage.getUsed();
        long usedMemory = endMemoryUsage.getUsed() - startMemoryUsage.getUsed();

        return Memory.ofBytes(usedHeapMemory).plus(Memory.ofBytes(usedMemory));
    }
}
