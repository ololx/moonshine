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
class DoubleEncoderTest extends Specification {

    @Unroll
    def "encode double with #byteOrder endianness"() {
        given:
        def encoder = new DoubleEncoder()

        when:
        byte[] encodedValue = encoder.encode(value, byteOrder)

        then:
        encodedValue == expected

        where:
        value            | byteOrder                             | expected
        Double.MIN_VALUE | Endianness.BIG_ENDIAN.byteOrder(7)    | [0, 0, 0, 0, 0, 0, 0, 1] as byte[]
        Double.MIN_VALUE | Endianness.LITTLE_ENDIAN.byteOrder(7) | [1, 0, 0, 0, 0, 0, 0, 0] as byte[]
        Double.MIN_VALUE | Endianness.PDP_ENDIAN.byteOrder(7)    | [0, 0, 0, 0, 0, 0, 1, 0] as byte[]
        0D               | Endianness.BIG_ENDIAN.byteOrder(7)    | [0, 0, 0, 0, 0, 0, 0, 0] as byte[]
        0D               | Endianness.LITTLE_ENDIAN.byteOrder(7) | [0, 0, 0, 0, 0, 0, 0, 0] as byte[]
        0D               | Endianness.PDP_ENDIAN.byteOrder(7)    | [0, 0, 0, 0, 0, 0, 0, 0] as byte[]
    }
}
