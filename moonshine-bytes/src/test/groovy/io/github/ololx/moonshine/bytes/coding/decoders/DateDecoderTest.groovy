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

import java.time.Instant

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class DateDecoderTest extends Specification {

    @Unroll
    def "when encoded value is #value, decoded date should be #expected"() {
        given:
        DateDecoder decoder = new DateDecoder()

        when:
        Date decodedValue = decoder.decode(value, byteOrder)

        then:
        decodedValue == expected

        where:
        expected                                            | byteOrder                             | value
        Date.from(Instant.parse("1990-03-20T00:00:00.00Z")) | Endianness.BIG_ENDIAN.byteOrder(7)    | [0, 0, 0, -108, -123, 71, 68, 0] as byte[]
        Date.from(Instant.parse("1990-03-20T00:00:00.00Z")) | Endianness.LITTLE_ENDIAN.byteOrder(7) | [0, 68, 71, -123, -108, 0, 0, 0] as byte[]
        Date.from(Instant.parse("1990-03-20T00:00:00.00Z")) | Endianness.PDP_ENDIAN.byteOrder(7)    | [0, 0, -108, 0, 71, -123, 0, 68] as byte[]
    }
}
