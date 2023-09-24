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

import java.time.LocalDate

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class LocalDateEncoderSpec extends Specification {

    @Unroll
    def "encode LocalDate with #byteOrder endianness"() {
        given:
        def encoder = new LocalDateEncoder()

        when:
        byte[] encodedValue = encoder.encode(value, byteOrder)

        then:
        encodedValue == expected

        where:
        value         | byteOrder                             | expected
        LocalDate.MIN | Endianness.BIG_ENDIAN.byteOrder(5)    | [1, 1, -60, 101, 54, 1] as byte[]
        LocalDate.MIN | Endianness.LITTLE_ENDIAN.byteOrder(5) | [1, 54, 101, -60, 1, 1] as byte[]
        LocalDate.MIN | Endianness.PDP_ENDIAN.byteOrder(5)    | [1, 1, 101, -60, 1, 54] as byte[]
        LocalDate.MAX | Endianness.BIG_ENDIAN.byteOrder(5)    | [31, 12, 59, -102, -55, -1] as byte[]
        LocalDate.MAX | Endianness.LITTLE_ENDIAN.byteOrder(5) | [-1, -55, -102, 59, 12, 31] as byte[]
        LocalDate.MAX | Endianness.PDP_ENDIAN.byteOrder(5)    | [12, 31, -102, 59, -1, -55] as byte[]
    }
}
