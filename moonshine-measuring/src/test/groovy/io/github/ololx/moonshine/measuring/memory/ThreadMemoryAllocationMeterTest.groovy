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

package io.github.ololx.moonshine.measuring.memory

import groovy.transform.CompileStatic
import spock.lang.Specification

/**
 * project moonshine
 * created 07.04.2023 21:06
 *
 * @author Alexander A. Kropotin
 */
class ThreadMemoryAllocationMeterTest extends Specification {

    def "result() - when measurer was activated then return measuring result"() {
        given:
        def meter = new ThreadMemoryAllocationMeter()

        when:
        meter.start()
        def digits = new int[1_000_000]
        meter.stop()

        then:
        meter.result().toBytes() > 0
        meter.result() >= Memory.ofBytes(1_000_000 * 4)
    }

    def "result() - when measurer was activated and stopped after GC then return positive measuring result"() {
        given:
        def meter = new ThreadMemoryAllocationMeter()

        when:
        meter.start()

        def array = new int[1_000_000]
        for (int i = 0; i < array.length; i++) {
            array[i] = i
        }

        array = null
        System.gc()
        meter.stop()
        def result = meter.result()

        then:
        meter.result().toBytes() > 0
        meter.result() >= Memory.ofBytes(1_000_000 * 4)
    }
}