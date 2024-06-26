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

package io.github.ololx.moonshine.util.concurrent.atomic


import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 13/09/2023 9:40 pm
 */
class AtomicByteArrayTest extends Specification {

    def "length() - when get length then return correct length"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        expect:
        atomicLongArray.length() == 5
    }

    def "get() - when get then correct value returned"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        expect:
        atomicLongArray.get(0) == 1 as byte
    }

    def "set() - when set then value set correctly"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        atomicLongArray.set(0, 9 as byte)

        then:
        atomicLongArray.get(0) == 9 as byte
    }

    def "getAndSet() - when set then old value returned and new value set"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicLongArray.getAndSet(0, 7 as byte)

        then:
        oldValue == 1 as byte
        atomicLongArray.get(0) == 7 as byte
    }

    def "compareAndSet() - when expect matches then value set and return true"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        boolean result = atomicLongArray.compareAndSet(0, 1 as byte, 8 as byte)

        then:
        result
        atomicLongArray.get(0) == 8 as byte
    }

    def "getAndIncrement() - when increment then increment value and return old value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicLongArray.getAndIncrement(0)

        then:
        oldValue == 1 as byte
        atomicLongArray.get(0) == 2 as byte
    }

    def "getAndDecrement() - when decrement then decrement value and return old value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicLongArray.getAndDecrement(1)

        then:
        oldValue == 2 as byte
        atomicLongArray.get(1) == 1 as byte
    }

    def "incrementAndGet() - when increment then increment value and return new value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte newValue = atomicLongArray.incrementAndGet(0)

        then:
        newValue == 2 as byte
    }

    def "decrementAndGet() - when decrement then decrement value and return new value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte newValue = atomicLongArray.decrementAndGet(1)

        then:
        newValue == 1 as byte
    }

    def "addAndGet() - when add delta then add value and return new value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte newValue = atomicLongArray.addAndGet(1, 3 as byte)

        then:
        newValue == 5 as byte
    }

    def "getAndAdd() - when add delta then add value and return old value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicLongArray.getAndAdd(1, 2 as byte)

        then:
        oldValue == 2 as byte
        atomicLongArray.get(1) == 4 as byte
    }

    def "getAndUpdate() - when update value then update value and return old value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicLongArray.getAndUpdate(1, value -> value + 12 as byte)

        then:
        oldValue == 2 as byte
        atomicLongArray.get(1) == 14 as byte
    }

    def "updateAndGet() - when update value then update value and return new value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte newValue = atomicLongArray.updateAndGet(1, value -> value + 12 as byte)

        then:
        newValue == 14 as byte
    }

    def "getAndAccumulate() - when update value then update value and return old value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicLongArray.getAndAccumulate(1, 12 as byte, (value, update) -> value as byte + update as byte)

        then:
        oldValue == 2 as byte
        atomicLongArray.get(1) == 14 as byte
    }

    def "accumulateAndGet() - when update value then update value and return new value"() {
        given:
        def atomicLongArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte newValue = atomicLongArray.accumulateAndGet(1, 12 as byte, (value, update) -> value as byte + update as byte)

        then:
        newValue == 14 as byte
    }

    def "getAndBitwiseOr() - when perform bitwise OR then return old value and update array"() {
        given:
        def atomicByteArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicByteArray.getAndBitwiseOr(2, 2 as byte)

        then:
        oldValue == 3 as byte
        atomicByteArray.get(2) == (3 | 2) as byte
    }

    def "getAndBitwiseAnd() - when perform bitwise AND then return old value and update array"() {
        given:
        def atomicByteArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicByteArray.getAndBitwiseAnd(0, 2 as byte)

        then:
        oldValue == 1 as byte
        atomicByteArray.get(0) == (1 & 2) as byte
    }

    def "getAndBitwiseXor() - when perform bitwise XOR then return old value and update array"() {
        given:
        def atomicByteArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicByteArray.getAndBitwiseXor(2, 2 as byte)

        then:
        oldValue == 3 as byte
        atomicByteArray.get(2) == (3 ^ 2) as byte
    }

    def "getAndBitwiseNot() - when perform bitwise NOT then return old value and update array"() {
        given:
        def atomicByteArray = new AtomicByteArray([1, 2, 3, 4, 5] as byte[])

        when:
        byte oldValue = atomicByteArray.getAndBitwiseNot(2)

        then:
        oldValue == 3 as byte
        atomicByteArray.get(2) == ~3 as byte
    }

    @Unroll
    def "toString() - when get string from #array then return correct string = #stringRepresentation"() {
        given:
        def atomicLongArray = new AtomicByteArray(array)

        expect:
        atomicLongArray.toString() == stringRepresentation

        where:
        array                     | stringRepresentation
        [] as byte[]              | "[]"
        [1, 2, 3] as byte[]       | "[1, 2, 3]"
        [1, 2, 3, 4, 5] as byte[] | "[1, 2, 3, 4, 5]"
    }
}
