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

package io.github.ololx.moonshine.bytes

import io.github.moonshine.unsafe.adapter.AtomicAccess
import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
class EndiannessTest extends Specification {

    @Unroll
    def "BIG_ENDIAN - when getting byte order from const, it equals expected"() {
        expect:
        with(Endianness.BIG_ENDIAN) {
            name == "Big-Endian"
            id == 0x1
            byteOrder(3).apply(0) == 3
            byteOrder(3).apply(1) == 2
            byteOrder(3).apply(2) == 1
            byteOrder(3).apply(3) == 0
        }
    }

    @Unroll
    def "LITTLE_ENDIAN - when getting byte order from const, it equals expected"() {
        expect:
        with(Endianness.LITTLE_ENDIAN) {
            name == "Little-Endian"
            id == 0x2
            byteOrder(3).apply(0) == 0
            byteOrder(3).apply(1) == 1
            byteOrder(3).apply(2) == 2
            byteOrder(3).apply(3) == 3
        }
    }

    @Unroll
    def "PDP_ENDIAN - when getting byte order from const, it equals expected"() {
        expect:
        with(Endianness.PDP_ENDIAN) {
            name == "PDP-Endian"
            id == 0x3
            byteOrder(3).apply(0) == 2
            byteOrder(3).apply(1) == 3
            byteOrder(3).apply(2) == 0
            byteOrder(3).apply(3) == 1
        }
    }

    @Unroll
    def "DEFAULT - when getting byte order from const, it equals BIG_ENDIAN"() {
        expect:
        Endianness.DEFAULT == Endianness.BIG_ENDIAN
    }

    @Unroll
    def "NATIVE - when getting byte order from const, it equals system native"() {
        when:
        ByteOrder byteOrder = Endianness.NATIVE

        then:
        byteOrder == (AtomicAccess.SYSTEM_ENDIANNESS.isBigEndian() ? Endianness.BIG_ENDIAN : Endianness.LITTLE_ENDIAN)
    }
}
