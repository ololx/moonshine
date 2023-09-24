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

import java.time.LocalDateTime

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class LocalDateTimeDecoderTest extends Specification {

    @Unroll
    def "when encoded value is #value, decoded date-time should be #expected"() {
        given:
        LocalDateTimeDecoder decoder = new LocalDateTimeDecoder()

        when:
        LocalDateTime decodedValue = decoder.decode(value, byteOrder)

        then:
        decodedValue == expected

        where:
        expected          | byteOrder                              | value
        LocalDateTime.MIN | Endianness.BIG_ENDIAN.byteOrder(12)    | [0, 0, 0, 0, 0, 0, 0, 1, 1, -60, 101, 54, 1] as byte[]
        LocalDateTime.MIN | Endianness.LITTLE_ENDIAN.byteOrder(12) | [1, 54, 101, -60, 1, 1, 0, 0, 0, 0, 0, 0, 0] as byte[]
        LocalDateTime.MIN | Endianness.PDP_ENDIAN.byteOrder(12)    | [0, 0, 0, 0, 0, 0, 1, 0, -60, 1, 54, 101, 1] as byte[]
        LocalDateTime.MAX | Endianness.BIG_ENDIAN.byteOrder(12)    | [59, -102, -55, -1, 59, 59, 23, 31, 12, 59, -102, -55, -1] as byte[]
        LocalDateTime.MAX | Endianness.LITTLE_ENDIAN.byteOrder(12) | [-1, -55, -102, 59, 12, 31, 23, 59, 59, -1, -55, -102, 59] as byte[]
        LocalDateTime.MAX | Endianness.PDP_ENDIAN.byteOrder(12)    | [-102, 59, -1, -55, 59, 59, 31, 23, 59, 12, -55, -102, -1] as byte[]
    }
}
