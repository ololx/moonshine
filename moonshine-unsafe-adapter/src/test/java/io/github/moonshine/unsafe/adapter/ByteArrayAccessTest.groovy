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
        byteArrayAccess.get(array, 0) == 1
        byteArrayAccess.get(array, 1) == 2
        byteArrayAccess.get(array, 2) == 3
        byteArrayAccess.get(array, 3) == 4
        byteArrayAccess.get(array, 4) == 5
    }

    def "set() - when valid index then update byte value in array"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [1, 2, 3, 4, 5] as byte[]

        when:
        byteArrayAccess.set(array, 0, (byte) 10)
        byteArrayAccess.set(array, 4, (byte) 50)

        then:
        array[0] == 10
        array[4] == 50
    }

    @Unroll
    def "get() and set() - when set #value at index #index then get returns #value"() {
        given:
        ByteArrayAccess byteArrayAccess = new ByteArrayAccess()
        byte[] array = [0, 0, 0, 0, 0] as byte[]

        when:
        byteArrayAccess.set(array, index, value as byte)

        then:
        byteArrayAccess.get(array, index) == value

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
}
