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
class ByteEncoderSpec extends Specification {

    @Unroll
    def "encode byte value with #byteOrder endianness"() {
        given:
        def encoder = new ByteEncoder()

        when:
        byte[] encodedValue = encoder.encode(value, byteOrder)

        then:
        encodedValue == expected

        where:
        value        | byteOrder                             | expected
        -128 as byte | Endianness.BIG_ENDIAN.byteOrder(0)    | [-128] as byte[]
        -128 as byte | Endianness.LITTLE_ENDIAN.byteOrder(0) | [-128] as byte[]
        -128 as byte | Endianness.PDP_ENDIAN.byteOrder(0)    | [-128] as byte[]
        0 as byte    | Endianness.BIG_ENDIAN.byteOrder(0)    | [0] as byte[]
        0 as byte    | Endianness.LITTLE_ENDIAN.byteOrder(0) | [0] as byte[]
        0 as byte    | Endianness.PDP_ENDIAN.byteOrder(0)    | [0] as byte[]
        127 as byte  | Endianness.BIG_ENDIAN.byteOrder(0)    | [127] as byte[]
        127 as byte  | Endianness.LITTLE_ENDIAN.byteOrder(0) | [127] as byte[]
        127 as byte  | Endianness.PDP_ENDIAN.byteOrder(0)    | [127] as byte[]
    }
}
