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

package io.github.ololx.moonshine.bytes.coding.encoders

import io.github.ololx.moonshine.bytes.Endianness
import spock.lang.Specification
import spock.lang.Unroll

/**
 * project moonshine
 * created 25.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class FloatEncoderTest extends Specification {

    @Unroll
    def "encode float with #byteOrder endianness"() {
        given:
        def encoder = new FloatEncoder()

        when:
        byte[] encodedValue = encoder.encode(value, byteOrder)

        then:
        encodedValue == expected

        where:
        value           | byteOrder                             | expected
        Float.MIN_VALUE | Endianness.BIG_ENDIAN.byteOrder(3)    | [0, 0, 0, 1] as byte[]
        Float.MIN_VALUE | Endianness.LITTLE_ENDIAN.byteOrder(3) | [1, 0, 0, 0] as byte[]
        Float.MIN_VALUE | Endianness.PDP_ENDIAN.byteOrder(3)    | [0, 0, 1, 0] as byte[]
        0f              | Endianness.BIG_ENDIAN.byteOrder(3)    | [0, 0, 0, 0] as byte[]
        0f              | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, 0, 0] as byte[]
        0f              | Endianness.PDP_ENDIAN.byteOrder(3)    | [0, 0, 0, 0] as byte[]
        -258f           | Endianness.BIG_ENDIAN.byteOrder(3)    | [-61, -127, 0, 0] as byte[]
        -258f           | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, -127, -61] as byte[]
        -258f           | Endianness.PDP_ENDIAN.byteOrder(3)    | [-127, -61, 0, 0] as byte[]
        258f            | Endianness.BIG_ENDIAN.byteOrder(3)    | [67, -127, 0, 0] as byte[]
        258f            | Endianness.LITTLE_ENDIAN.byteOrder(3) | [0, 0, -127, 67] as byte[]
        258f            | Endianness.PDP_ENDIAN.byteOrder(3)    | [-127, 67, 0, 0] as byte[]
    }
}
