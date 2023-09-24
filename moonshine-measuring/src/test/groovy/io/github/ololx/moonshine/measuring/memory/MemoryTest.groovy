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

import groovy.transform.CompileDynamic
import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 07.04.2023 15:56
 *
 * @author Alexander A. Kropotin
 */
class MemoryTest extends Specification {

    @Unroll
    def "should correctly represent bytes for memory size #memorySize"() {
        when:
        Memory memory = Memory.ofBytes(memorySize)

        then:
        memory.toBytes() == memorySize

        where:
        memorySize << [0L, 1L, 100L, 1000L]
    }

    @Unroll
    def "should correctly represent kilobytes for memory size #memorySize"() {
        when:
        Memory memory = Memory.ofKilobytes(memorySize)

        then:
        memory.toBytes() == memorySize * 1024 // Assuming KILOBYTE is 1024 bytes

        where:
        memorySize << [0L, 1L, 100L, 1000L]
    }

    @CompileDynamic
    @Unroll
    def "should correctly represent megabytes for memory size #memorySize"() {
        when:
        Memory memory = Memory.ofMegabytes(memorySize)

        then:
        memory.toBytes() == memorySize * 1024 * 1024 // Assuming MEGABYTE is 1024 * 1024 bytes

        where:
        memorySize << [0L, 1L, 100L, 1000L]
    }

    @CompileDynamic
    @Unroll
    def "should correctly represent gigabytes for memory size #memorySize"() {
        when:
        Memory memory = Memory.ofGigabytes(memorySize)

        then:
        memory.toBytes() == memorySize * 1024 * 1024 * 1024 // Assuming GIGABYTE is 1024^3 bytes

        where:
        memorySize << [0L, 1L, 100L, 1000L]
    }

    @CompileDynamic
    @Unroll
    def "should correctly represent terabytes for memory size #memorySize"() {
        when:
        Memory memory = Memory.ofTerabytes(memorySize)

        then:
        memory.toBytes() == memorySize * 1024 * 1024 * 1024 * 1024 // Assuming TERABYTE is 1024^4 bytes

        where:
        memorySize << [0L, 1L, 100L, 1000L]
    }

    @CompileDynamic
    @Unroll
    def "adding #firstMemorySize bytes to #secondMemorySize bytes should result in #expectedMemorySize bytes"() {
        when:
        Memory actual = Memory.ofBytes(firstMemorySize).plus(Memory.ofBytes(secondMemorySize))

        then:
        actual.toBytes() == expectedMemorySize

        where:
        firstMemorySize | secondMemorySize | expectedMemorySize
        0L              | 0L               | 0L
        1L              | 0L               | 1L
        0L              | 1L               | 1L
        100L            | 100L             | 200L
        512L            | 512L             | 1024L
    }

    @CompileDynamic
    @Unroll
    def "subtracting #secondMemorySize bytes from #firstMemorySize bytes should result in #expectedMemorySize bytes"() {
        when:
        Memory actual = Memory.ofBytes(firstMemorySize).minus(Memory.ofBytes(secondMemorySize))

        then:
        actual.toBytes() == expectedMemorySize

        where:
        firstMemorySize | secondMemorySize | expectedMemorySize
        0L              | 0L               | 0L
        1L              | 0L               | 1L
        1L              | 1L               | 0L
        200L            | 100L             | 100L
        1024L           | 512L             | 512L
    }

    @CompileDynamic
    @Unroll
    def "comparing memory size of #firstMemorySize bytes with #secondMemorySize bytes should return #expectedComparingResult"() {
        when:
        int comparisonResult = Memory.ofBytes(firstMemorySize).compareTo(Memory.ofBytes(secondMemorySize))

        then:
        comparisonResult == expectedComparingResult

        where:
        firstMemorySize | secondMemorySize | expectedComparingResult
        0L              | 0L               | 0
        0L              | 1L               | -1
        1L              | 0L               | 1
        1L              | 1L               | 0
        200L            | 200L             | 0
        200L            | 100L             | 1
        100L            | 200L             | -1
        512L            | 1024L            | -1
        512L            | 512L             | 0
        1024L           | 512L             | 1
    }
}
