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

import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class ZonedDateTimeDecoderSpec extends Specification {

    @Unroll
    def "when encoded value is #value, decoded ZonedDateTime should be #expected"() {
        given:
        ValueBytesDecoder<ZonedDateTime> decoder = new ZonedDateTimeDecoder()

        when:
        ZonedDateTime decodedValue = decoder.decode(value, byteOrder)

        then:
        decodedValue == expected

        where:
        expected                                                     | byteOrder                              | value
        ZonedDateTime.of(1990, 03, 20, 0, 0, 0, 0, ZoneId.of("UTC")) | Endianness.BIG_ENDIAN.byteOrder(18)    | [0, 67, 0, 84, 0, 85, 0, 0, 0, 0, 0, 0, 0, 20, 3, 0, 0, 7, -58] as byte[]
        ZonedDateTime.of(1990, 03, 20, 0, 0, 0, 0, ZoneId.of("UTC")) | Endianness.LITTLE_ENDIAN.byteOrder(18) | [-58, 7, 0, 0, 3, 20, 0, 0, 0, 0, 0, 0, 0, 85, 0, 84, 0, 67, 0] as byte[]
        ZonedDateTime.of(1990, 03, 20, 0, 0, 0, 0, ZoneId.of("UTC")) | Endianness.PDP_ENDIAN.byteOrder(18)    | [67, 0, 84, 0, 85, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 3, 7, 0, -58] as byte[]
    }
}
