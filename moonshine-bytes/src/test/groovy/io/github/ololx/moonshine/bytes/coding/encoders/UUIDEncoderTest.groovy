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
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
class UUIDEncoderTest extends Specification {

    @Unroll
    def "when UUID is #value, encoded bytes should be #expected"() {
        given:
        UUIDEncoder encoder = new UUIDEncoder()

        when:
        byte[] encodedValue = encoder.encode(value, byteOrder)

        then:
        encodedValue == expected

        where:
        value                                                   | byteOrder                              | expected
        UUID.fromString("5cbb7015-7b74-452f-afb3-ce82e8637bc9") | Endianness.BIG_ENDIAN.byteOrder(15)    | [-81, -77, -50, -126, -24, 99, 123, -55, 92, -69, 112, 21, 123, 116, 69, 47] as byte[]
        UUID.fromString("5cbb7015-7b74-452f-afb3-ce82e8637bc9") | Endianness.LITTLE_ENDIAN.byteOrder(15) | [47, 69, 116, 123, 21, 112, -69, 92, -55, 123, 99, -24, -126, -50, -77, -81] as byte[]
        UUID.fromString("5cbb7015-7b74-452f-afb3-ce82e8637bc9") | Endianness.PDP_ENDIAN.byteOrder(15)    | [-77, -81, -126, -50, 99, -24, -55, 123, -69, 92, 21, 112, 116, 123, 47, 69] as byte[]
    }
}
