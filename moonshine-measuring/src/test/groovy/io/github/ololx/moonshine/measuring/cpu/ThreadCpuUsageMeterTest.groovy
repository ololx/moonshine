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

import groovy.transform.CompileStatic
import spock.lang.Specification

import java.time.Duration

/**
 * project moonshine
 * created 27.04.2023 11:31
 *
 * @author Alexander A. Kropotin
 */
class ThreadCpuUsageMeterTest extends Specification {

    def "result() - when not stopped then return zero result"() {
        given:
        def meter = new ThreadCpuUsageMeter()

        when:
        meter.start()

        then:
        meter.result() == CpuUsage.ZERO
    }

    def "result() - when measured then return non zero result"() {
        given:
        def meter = new ThreadCpuUsageMeter()

        and:
        meter.start()

        def array = new int[1_000_000]
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) Math.sin(i)
        }

        def result = meter.stop().result()

        expect:
        result.toUsageTime(Duration.ofMillis(100)).toNanos() > 0
        result.toUsageFraction() > 0
        result.toUsagePercent() > 0
    }
}
