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
class CharDecoderSpec extends Specification {

    @Unroll
    def "when encoded value is #value, decoded char should be #expected"() {
        given:
        CharDecoder decoder = new CharDecoder()

        when:
        char decodedValue = decoder.decode(value, byteOrder)

        then:
        decodedValue == expected

        where:
        expected            | byteOrder                             | value
        Character.MIN_VALUE | Endianness.BIG_ENDIAN.byteOrder(1)    | [0, 0] as byte[]
        Character.MIN_VALUE | Endianness.LITTLE_ENDIAN.byteOrder(1) | [0, 0] as byte[]
        Character.MIN_VALUE | Endianness.PDP_ENDIAN.byteOrder(1)    | [0, 0] as byte[]
        (char) 0            | Endianness.BIG_ENDIAN.byteOrder(1)    | [0, 0] as byte[]
        (char) 0            | Endianness.LITTLE_ENDIAN.byteOrder(1) | [0, 0] as byte[]
        (char) 0            | Endianness.PDP_ENDIAN.byteOrder(1)    | [0, 0] as byte[]
        (char) -258         | Endianness.BIG_ENDIAN.byteOrder(1)    | [-2, -2] as byte[]
        (char) -258         | Endianness.LITTLE_ENDIAN.byteOrder(1) | [-2, -2] as byte[]
        (char) -258         | Endianness.PDP_ENDIAN.byteOrder(1)    | [-2, -2] as byte[]
        (char) 258          | Endianness.BIG_ENDIAN.byteOrder(1)    | [1, 2] as byte[]
        (char) 258          | Endianness.LITTLE_ENDIAN.byteOrder(1) | [2, 1] as byte[]
        (char) 258          | Endianness.PDP_ENDIAN.byteOrder(1)    | [2, 1] as byte[]
    }
}
