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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import static org.testng.Assert.*;

/**
 * project moonshine
 * created 24.04.2023 17:17
 *
 * @author Alexander A. Kropotin
 */
public class CPUsageTest {

    static final int cores = Runtime.getRuntime().availableProcessors();

    @DataProvider
    static Object[][] providesCpuUsageTimesAndIntervalsAndCpuUsageFractions() {
        return new Object[][] {
                {100, 100, 1D / cores},
                {200, 200, 1D / cores},
                {1, 1, 1D / cores},
                {100, 50, 2D / cores},
                {100, 10, 10D / cores},
                {50, 100, 0.5D / cores},
        };
    }


    @Test(dataProvider = "providesCpuUsageTimesAndIntervalsAndCpuUsageFractions")
    public void toUsageFraction_whenCreated_thenReturnRightUsageFraction(long cpuTime,
                                                                         long interval,
                                                                         double expectedFraction) {
        //When
        // create CP usage instance
        CPUsage usage = CPUsage.ofUsageTime(Duration.ofNanos(cpuTime), Duration.ofNanos(interval));

        //Then
        // fraction equals expected
        assertEquals(usage.toUsageFraction(), expectedFraction);
    }

    @Test(dataProvider = "providesCpuUsageTimesAndIntervalsAndCpuUsageFractions")
    public void toUsagePercent_whenCreated_thenReturnRightUsagePercent(long cpuTime,
                                                                       long interval,
                                                                       double expectedFraction) {
        //When
        // create CP usage instance
        CPUsage usage = CPUsage.ofUsageTime(Duration.ofNanos(cpuTime), Duration.ofNanos(interval));

        //Then
        // percents equals expected
        assertEquals(usage.toUsagePercent(), expectedFraction * 100L);
    }

    @DataProvider
    static Object[][] providesCpuUsageTimesAndIntervals() {
        return new Object[][] {
                {100, 100},
                {200, 200},
                {1, 1},
                {100, 50},
                {100, 10},
                {50, 100},
        };
    }

    @Test(dataProvider = "providesCpuUsageTimesAndIntervals")
    public void toUsageTime_whenCreated_thenReturnRightUsageFraction(long cpuTime, long interval) {
        //When
        // create CP usage instance
        CPUsage usage = CPUsage.ofUsageTime(Duration.ofNanos(cpuTime), Duration.ofNanos(interval));

        //Then
        // time equals expected
        assertEquals(usage.toUsageTime(Duration.ofNanos(interval)), Duration.ofNanos(cpuTime));
    }

    @DataProvider
    static Object[][] providesWrongCpuUsageTimesOrIntervals() {
        return new Object[][] {
                {Duration.ofNanos(100), Duration.ofNanos(-100)},
                {Duration.ofNanos(-100), Duration.ofNanos(100)},
                {Duration.ofNanos(-100), Duration.ofNanos(-100)},
                {Duration.ofNanos(0), Duration.ofNanos(0)},
                {null, null}
        };
    }

    @Test(
            dataProvider = "providesWrongCpuUsageTimesOrIntervals",
            expectedExceptions = IllegalArgumentException.class
    )
    public void ofUsageTime_whenSomeOfDurationIsNegativeOrZeroOrNull_thenThrowException(Duration cpuTime,
                                                                                        Duration interval) {
        //When
        // create CP usage instance with bad  params
        //Then
        // throws exception
        CPUsage.ofUsageTime(cpuTime, interval);
    }
}
