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

package io.github.ololx.moonshine.measuring.cpu

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Duration

/**
 * project moonshine
 * created 24.04.2023 17:17
 *
 * @author Alexander A. Kropotin
 */
class CpuUsageTest extends Specification {

    @Shared
    int cores = Runtime.getRuntime().availableProcessors()

    @Unroll
    def "toUsageFraction() - when create with #cpuTime and #interval then return correct usage #fraction"() {
        given:
        CpuUsage usage = CpuUsage.ofUsageTime(Duration.ofNanos(cpuTime), Duration.ofNanos(interval))

        expect:
        usage.toUsageFraction() == fraction

        where:
        cpuTime | interval | fraction
        0       | 0        | Double.NaN
        0       | 1        | 0D
        1       | 0        | Double.POSITIVE_INFINITY
        100     | 100      | 1D / cores
        200     | 200      | 1D / cores
        1       | 1        | 1D / cores
        100     | 50       | 2D / cores
        100     | 10       | 10D / cores
        50      | 100      | 0.5D / cores
    }

    @Unroll
    def "toUsagePercent() - when create with #cpuTime and #interval then return correct usage #percents"() {
        given:
        CpuUsage usage = CpuUsage.ofUsageTime(Duration.ofNanos(cpuTime), Duration.ofNanos(interval))

        expect:
        usage.toUsagePercent() == percents

        where:
        cpuTime | interval | percents
        0       | 0        | Double.NaN
        0       | 1        | 0D
        1       | 0        | Double.POSITIVE_INFINITY
        100     | 100      | (1D / cores) * 100
        200     | 200      | (1D / cores) * 100
        1       | 1        | (1D / cores) * 100
        100     | 50       | (2D / cores) * 100
        100     | 10       | (10D / cores) * 100
        50      | 100      | (.5D / cores) * 100
    }

    @Unroll
    def "toUsageTime()- when create with #cpuTime and #interval then return correct usage #time"() {
        given:
        CpuUsage usage = CpuUsage.ofUsageTime(Duration.ofNanos(cpuTime), Duration.ofNanos(interval))

        expect:
        usage.toUsageTime(Duration.ofNanos(newInterval)) == Duration.ofNanos(time)

        where:
        cpuTime | interval | newInterval | time
        0       | 0        | 0           | 0
        1       | 1        | 1           | 1
        100     | 100      | 100         | 100
        100     | 200      | 200         | 100
        100     | 200      | 100         | 50
        100     | 400      | 300         | 75
        100     | 400      | 200         | 50
        100     | 400      | 100         | 25
    }

    @Unroll
    def "ofUsageTime() - when create with #cpuTime and #interval then throw exception"() {
        when:
        CpuUsage.ofUsageTime(cpuTime, interval)

        then:
        thrown(IllegalArgumentException)

        where:
        cpuTime                | interval
        Duration.ofNanos(100)  | Duration.ofNanos(-100)
        Duration.ofNanos(-100) | Duration.ofNanos(100)
        Duration.ofNanos(-100) | Duration.ofNanos(-100)
        null                   | null
    }
}
