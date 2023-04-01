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

package io.github.com.ololx.moonshine.measuring;


import com.sun.management.ThreadMXBean;

import java.lang.management.ManagementFactory;

/**
 * project moonshine
 * created 01.04.2023 18:42
 *
 * @author Alexander A. Kropotin
 */
public class ThreadMemoryMeter {
    private final ThreadMXBean threadBean;
    private long startAllocatedBytes;
    private long threadId;
    private long result;

    public ThreadMemoryMeter() {
        threadBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    }

    public void from() {
        threadId = Thread.currentThread().getId();
        startAllocatedBytes = threadBean.getThreadAllocatedBytes(Thread.currentThread().getId());
    }

    public void to() {
        long endAllocatedBytes = threadBean.getThreadAllocatedBytes(threadId);
        result = (endAllocatedBytes - startAllocatedBytes) / 1024;
    }

    public long getResult() {
        return result;
    }
}
