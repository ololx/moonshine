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

package io.github.ololx.moonshine.util.concurrent.atomic.wrapping

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 13/09/2023 9:40 pm
 */
import spock.lang.Specification
import spock.lang.Unroll

class AtomicIntegerArrayWrapperTest extends Specification {

    @Unroll
    def "AtomicIntegerArrayWrapper() - when create from length = #length then create new instance with length = #length"() {
        when:
        def atomicArray = new AtomicIntegerArrayWrapper(length)

        then:
        atomicArray.length() == length

        where:
        length << [1, 2, 3, 4, 5]
    }

    @Unroll
    def "AtomicIntegerArrayWrapperTest() - when create from #array then create new instance with same length = #length"() {
        when:
        def atomicArray = new AtomicIntegerArrayWrapper(array)

        then:
        atomicArray.length() == length

        where:
        length | array
        1      | [1] as int[]
        2      | [1, 2] as int[]
        3      | [1, 2, 3] as int[]
    }
    
    def "length() - when get length then return correct length"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        expect:
        atomicArray.length() == 5
    }

    def "get() - when get then correct value returned"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        expect:
        atomicArray.get(0) == 1
    }

    def "set() - when set then value set correctly"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        atomicArray.set(0, 9)

        then:
        atomicArray.get(0) == 9
    }

    def "getAndSet() - when set then old value returned and new value set"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long oldValue = atomicArray.getAndSet(0, 7)

        then:
        oldValue == 1L
        atomicArray.get(0) == 7
    }

    def "compareAndSet() - when expect matches then value set and return true"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        boolean result = atomicArray.compareAndSet(0, 1, 8)

        then:
        result
        atomicArray.get(0) == 8
    }

    def "getAndIncrement() - when increment then increment value and return old value"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long oldValue = atomicArray.getAndIncrement(0)

        then:
        oldValue == 1L
        atomicArray.get(0) == 2
    }

    def "getAndDecrement() - when decrement then decrement value and return old value"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long oldValue = atomicArray.getAndDecrement(1)

        then:
        oldValue == 2
        atomicArray.get(1) == 1
    }

    def "incrementAndGet() - when increment then increment value and return new value"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long newValue = atomicArray.incrementAndGet(0)

        then:
        newValue == 2
    }

    def "decrementAndGet() - when decrement then decrement value and return new value"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long newValue = atomicArray.decrementAndGet(1)

        then:
        newValue == 1
    }

    def "addAndGet() - when add then add value and return new value"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long newValue = atomicArray.addAndGet(1, 3)

        then:
        newValue == 5
    }

    def "getAndAdd() - when add then add value and return old value"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        when:
        long oldValue = atomicArray.getAndAdd(1, 2)

        then:
        oldValue == 2
        atomicArray.get(1) == 4
    }

    def "toString() - when get string then return correct string representation"() {
        given:
        def atomicArray = new AtomicIntegerArrayWrapper([1, 2, 3, 4, 5] as int[])

        expect:
        atomicArray.toString() == "[1, 2, 3, 4, 5]"
    }
}
