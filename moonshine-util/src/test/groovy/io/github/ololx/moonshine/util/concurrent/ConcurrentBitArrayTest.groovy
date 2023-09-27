/**
 * Copyright 2022 the project moonshine authors
 * and the original author or authors annotated by {@author}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.util.concurrent


import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 13/09/2023 9:40 pm
 */
class ConcurrentBitArrayTest extends Specification {

    @Shared
    Random random = new Random()

    @Unroll
    def "get() - when #index bit is unsetted then return false"() {
        given:
        def bits = new ConcurrentBitArray(length)

        expect:
        !bits.get(index)

        where:
        length | index
        1      | random.nextInt(1)
        8      | random.nextInt(8)
        64     | random.nextInt(64)
    }

    @Unroll
    def "get() - when #index bit is out of bounds [0, #length) then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.get(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        length | index
        1      | -1
        1      | 1
        8      | -1
        8      | 8
        64     | -1
        64     | 64
    }

    @Unroll
    def "set() - when set #index bit then get return true with length"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.set(index)

        then:
        bits.get(index)

        where:
        length | index
        1      | random.nextInt(1)
        8      | random.nextInt(8)
        64     | random.nextInt(64)
    }

    @Unroll
    def "set() - when #index bit is out of bounds [0, #length) then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.set(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        length | index
        1      | -1
        1      | 1
        8      | -1
        8      | 8
        64     | -1
        64     | 64
    }

    @Unroll
    def "clear() - when #index bit is cleared then return false with length"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.set(index)
        bits.clear(index)

        then:
        !bits.get(index)

        where:
        length | index
        1      | random.nextInt(1)
        8      | random.nextInt(8)
        64     | random.nextInt(64)
    }

    @Unroll
    def "clear() - when #index bit is out of bounds [0, #length) then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.clear(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        length | index
        1      | -1
        1      | 1
        8      | -1
        8      | 8
        64     | -1
        64     | 64
    }

    @Unroll
    def "flip() - when #index bit is unset then return true with length"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.flip(index)

        then:
        bits.get(index)

        where:
        length | index
        1      | random.nextInt(1)
        8      | random.nextInt(8)
        64     | random.nextInt(64)
    }

    @Unroll
    def "flip() - when #index bit is out of bounds [0, #length) then throw exception"() {
        given:
        def bits = new ConcurrentBitArray(length)

        when:
        bits.flip(index)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        length | index
        1      | -1
        1      | 1
        8      | -1
        8      | 8
        64     | -1
        64     | 64
    }

    @Unroll
    def "cardinality() - when bitset contains 1 bits in #words then return #cardinality"() {
        given:
        def bits = ConcurrentBitArray.valueOf(words as byte[])

        expect:
        bits.cardinality() == cardinality

        where:
        words        | cardinality
        []           | 0
        [1]          | 1
        [-128]       | 1
        [127]        | 7
        [-1]         | 8
        [1, 1, 1, 1] | 4
        [1, 2, 3, 4] | 5
    }
}
