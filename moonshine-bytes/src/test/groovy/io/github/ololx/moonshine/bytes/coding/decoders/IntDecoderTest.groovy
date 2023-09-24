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

package io.github.ololx.moonshine.bytes.coding.decoders

import io.github.ololx.moonshine.bytes.Endianness
import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 25.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class IntDecoderTest extends Specification {

    @Unroll
    def "when encoded value is #value, decoded int should be #expected"() {
        given:
        IntDecoder decoder = new IntDecoder()

        when:
        int decodedValue = decoder.decode(value, byteOrder)

        then:
        decodedValue == expected

        where:
        expected          | byteOrder                             | value
        Integer.MIN_VALUE | Endianness.BIG_ENDIAN.byteOrder(3)    | [-128, 0, 0, 0] as byte[]
        Integer.MIN_VALUE | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, 0, -128] as byte[]
        Integer.MIN_VALUE | Endianness.PDP_ENDIAN.byteOrder(3)    | [0, -128, 0, 0] as byte[]
        0                 | Endianness.BIG_ENDIAN.byteOrder(3)    | [0, 0, 0, 0] as byte[]
        0                 | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, 0, 0] as byte[]
        0                 | Endianness.PDP_ENDIAN.byteOrder(3)    | [0, 0, 0, 0] as byte[]
        -65536            | Endianness.BIG_ENDIAN.byteOrder(3)    | [-1, -1, 0, 0] as byte[]
        -65536            | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, -1, -1] as byte[]
        -65536            | Endianness.PDP_ENDIAN.byteOrder(3)    | [-1, -1, 0, 0] as byte[]
        65536             | Endianness.BIG_ENDIAN.byteOrder(3)    | [0, 1, 0, 0] as byte[]
        65536             | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, 1, 0] as byte[]
        65536             | Endianness.PDP_ENDIAN.byteOrder(3)    | [1, 0, 0, 0] as byte[]
    }
}
