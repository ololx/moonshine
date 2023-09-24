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

package io.github.ololx.moonshine.tuple

/**
 * project moonshine
 * created 26.01.2023 09:39
 *
 * @author Alexander A. Kropotin
 */
import spock.lang.Specification
import spock.lang.Unroll

class IndexBoundsSpec extends Specification {

    @Unroll
    def "checkIndex() - when #index within #upperBound then return true"() {
        expect:
        IndexBounds.checkIndex(index, upperBound)

        where:
        index | upperBound
        0     | 1
        1     | 2
        0     | 101
        1     | 101
        100   | 101
    }

    @Unroll
    def "checkIndex() - when #index out of #upperBound then return false"() {
        expect:
        !IndexBounds.checkIndex(index, upperBound)

        where:
        index | upperBound
        0     | 0
        1     | 0
        1     | 1
        100   | 1
        100   | 100
        101   | 100
    }

    @Unroll
    def "checkIndex() - when #index within #lowerBound and #upperBound then return true"() {
        expect:
        IndexBounds.checkIndex(index, lowerBound, upperBound)

        where:
        index | lowerBound | upperBound
        0     | 0          | 1
        1     | 0          | 2
        1     | 0          | 100
        100   | 0          | 101
        100   | 100        | 101
    }

    @Unroll
    def "checkIndex() - when #index out of #lowerBound and #upperBound then return false"() {
        expect:
        !IndexBounds.checkIndex(index, lowerBound, upperBound)

        where:
        index | lowerBound | upperBound
        1     | 0          | 0
        1     | 0          | 1
        1     | 1          | 1
        100   | 100        | 100
        100   | 101        | 101
        100   | 101        | 100
    }

    @Unroll
    def "requireIndexWithinBounds() - when #index within #upperBound then return true"() {
        expect:
        IndexBounds.requireIndexWithinBounds(index, upperBound) == index

        where:
        index | upperBound
        0     | 1
        1     | 2
        0     | 101
        1     | 101
        100   | 101
    }

    @Unroll
    def "requireIndexWithinBounds() - when #index out of #upperBound then throw exception"() {
        when:
        IndexBounds.requireIndexWithinBounds(index, upperBound)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        index | upperBound
        0     | 0
        1     | 0
        1     | 1
        100   | 1
        100   | 100
        101   | 100
    }

    @Unroll
    def "requireIndexWithinBounds() - when #index within #lowerBound and #upperBound then return true"() {
        expect:
        IndexBounds.requireIndexWithinBounds(index, lowerBound, upperBound) == index

        where:
        index | lowerBound | upperBound
        0     | 0          | 1
        1     | 0          | 2
        1     | 0          | 100
        100   | 0          | 101
        100   | 100        | 101
    }

    @Unroll
    def "requireIndexWithinBounds() - when #index out of #lowerBound and #upperBound then throw exception"() {
        when:
        IndexBounds.requireIndexWithinBounds(index, lowerBound, upperBound)

        then:
        thrown(IndexOutOfBoundsException)

        where:
        index | lowerBound | upperBound
        1     | 0          | 0
        1     | 0          | 1
        1     | 1          | 1
        100   | 100        | 100
        100   | 101        | 101
        100   | 101        | 100
    }
}
