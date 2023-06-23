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

import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * project moonshine
 * created 27.04.2023 11:31
 *
 * @author Alexander A. Kropotin
 */
public class ThreadCpuUsageMeterTest {

    @Test
    public void startAndGetResult_whenNotStopped_thenReturnZeroResult() {
        //Given
        // the cpu meter
        ThreadCpuUsageMeter meter = new ThreadCpuUsageMeter();

        //When
        // start and not stopped
        meter.start();

        //Then
        // return ZERO
        assertEquals(meter.result(), CpuUsage.ZERO);
    }

    @Test
    public void startAndGetResult_whenMeasured_thenReturnNonZeroResult() {
        //Given
        // the cpu meter
        ThreadCpuUsageMeter meter = new ThreadCpuUsageMeter();

        //When
        //measure some operations
        meter.start();

        int[] array = new int[1_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) Math.sin(i);
        }

        CpuUsage result = meter.stop().result();

        //Then
        // result is not ZERO
        assertTrue(result.toUsageTime(Duration.ofMillis(100)).toNanos() > 0);
        assertTrue(result.toUsageFraction() > 0);
        assertTrue(result.toUsagePercent() > 0);
    }
}
