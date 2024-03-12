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

package io.github.moonshine.unsafe.adapter


import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 16/09/2023 11:09 am
 */
class ByteArrayAccessTest extends Specification {

    def "get() - when valid index then return byte value from array"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        expect:
        byteArrayAccess.get(array, 0) == 1 as byte
        byteArrayAccess.get(array, 1) == 2 as byte
        byteArrayAccess.get(array, 2) == 3 as byte
        byteArrayAccess.get(array, 3) == 4 as byte
        byteArrayAccess.get(array, 4) == 5 as byte
    }

    def "set() - when valid index then update byte value in array"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.set(array, 0, (byte) 10)
        byteArrayAccess.set(array, 4, (byte) 50)

        then:
        array[0] == 10 as byte
        array[4] == 50 as byte
    }

    @Unroll
    def "get() and set() - when set #value at index #index then get returns #value"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [0, 0, 0, 0, 0] as byte[]

        when:
        byteArrayAccess.set(array, index, value as byte)

        then:
        byteArrayAccess.get(array, index) == value as byte

        where:
        index | value
        0     | -128
        1     | 127
        2     | 0
        3     | -1
        4     | 1
    }

    def "set() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.set(array, -1, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.set(array, array.length, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "get() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.get(array, -1)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.get(array, array.length)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "getVolatile() - when valid index then return byte value with volatile read semantics"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        expect:
        byteArrayAccess.getVolatile(array, 0) == 1 as byte
        byteArrayAccess.getVolatile(array, 1) == 2 as byte
        byteArrayAccess.getVolatile(array, 2) == 3 as byte
        byteArrayAccess.getVolatile(array, 3) == 4 as byte
        byteArrayAccess.getVolatile(array, 4) == 5 as byte
    }

    def "setVolatile() - when valid index then update byte value with volatile write semantics"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.setVolatile(array, 0, (byte) 10)
        byteArrayAccess.setVolatile(array, 4, (byte) 50)

        then:
        array[0] == 10 as byte
        array[4] == 50 as byte
    }

    @Unroll
    def "getVolatile() and setVolatile() - when set #value at index #index then getVolatile returns #value"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [0, 0, 0, 0, 0] as byte[]

        when:
        byteArrayAccess.setVolatile(array, index, value as byte)

        then:
        byteArrayAccess.getVolatile(array, index) == value as byte

        where:
        index | value
        0     | -128
        1     | 127
        2     | 0
        3     | -1
        4     | 1
    }

    def "setVolatile() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.setVolatile(array, -1, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.setVolatile(array, array.length, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "getVolatile() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.getVolatile(array, -1)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.getVolatile(array, array.length)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "getOpaque() - when valid index then return byte value with opaque read semantics"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        expect:
        byteArrayAccess.getOpaque(array, 0) == 1 as byte
        byteArrayAccess.getOpaque(array, 1) == 2 as byte
        byteArrayAccess.getOpaque(array, 2) == 3 as byte
        byteArrayAccess.getOpaque(array, 3) == 4 as byte
        byteArrayAccess.getOpaque(array, 4) == 5 as byte
    }

    def "setOpaque() - when valid index then update byte value with opaque write semantics"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.setOpaque(array, 0, (byte) 10)
        byteArrayAccess.setOpaque(array, 4, (byte) 50)

        then:
        array[0] == 10 as byte
        array[4] == 50 as byte
    }

    @Unroll
    def "getOpaque() and setOpaque() - when set #value at index #index then getOpaque returns #value"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [0, 0, 0, 0, 0] as byte[]

        when:
        byteArrayAccess.setOpaque(array, index, value as byte)

        then:
        byteArrayAccess.getOpaque(array, index) == value as byte

        where:
        index | value
        0     | -128
        1     | 127
        2     | 0
        3     | -1
        4     | 1
    }

    def "setOpaque() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.setOpaque(array, -1, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.setOpaque(array, array.length, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "getOpaque() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.getOpaque(array, -1)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.getOpaque(array, array.length)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "getAcquire() - when valid index then return byte value with acquire semantics"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        expect:
        byteArrayAccess.getAcquire(array, 0) == 1 as byte
        byteArrayAccess.getAcquire(array, 1) == 2 as byte
        byteArrayAccess.getAcquire(array, 2) == 3 as byte
        byteArrayAccess.getAcquire(array, 3) == 4 as byte
        byteArrayAccess.getAcquire(array, 4) == 5 as byte
    }

    def "setRelease() - when valid index then update byte value with release semantics"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.setRelease(array, 0, (byte) 10)
        byteArrayAccess.setRelease(array, 4, (byte) 50)

        then:
        array[0] == 10 as byte
        array[4] == 50 as byte
    }

    @Unroll
    def "getAcquire() and setRelease() - when set #value at index #index then getAcquire returns #value"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [0, 0, 0, 0, 0] as byte[]

        when:
        byteArrayAccess.setRelease(array, index, value as byte)

        then:
        byteArrayAccess.getAcquire(array, index) == value as byte

        where:
        index | value
        0     | -128
        1     | 127
        2     | 0
        3     | -1
        4     | 1
    }

    def "setRelease() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.setRelease(array, -1, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.setRelease(array, array.length, (byte) 10)

        then:
        thrown(IndexOutOfBoundsException)
    }

    def "getAcquire() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.getAcquire(array, -1)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.getAcquire(array, array.length)

        then:
        thrown(IndexOutOfBoundsException)
    }

    @Unroll
    def "compareAndSet() - when expected value at index #index is #expect then update to #update and return true"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        expect:
        byteArrayAccess.compareAndSet(array, index, expect as byte, update as byte)
        array[index] == update as byte

        where:
        index | expect | update
        0     | 1      | 10
        1     | 2      | 20
        2     | 3      | 30
        3     | 4      | 40
        4     | 5      | 50
    }

    def "compareAndSet() - when actual value does not match expected then do not update and return false"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        expect:
        !byteArrayAccess.compareAndSet(array, 2, (byte) 0, (byte) 8) // expected is 0, but actual is 3
        array[2] == 3 as byte
    }

    def "compareAndSet() - when index out of bounds then throw IndexOutOfBoundsException"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.compareAndSet(array, -1, (byte) 10, (byte) 11)

        then:
        thrown(IndexOutOfBoundsException)

        when:
        byteArrayAccess.compareAndSet(array, array.length, (byte) 10, (byte) 11)

        then:
        thrown(IndexOutOfBoundsException)
    }
}
