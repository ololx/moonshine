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
class StringDecoderTest extends Specification {

    @Unroll
    def "when encoded value is #value, decoded String should be #expected"() {
        given:
        ValueBytesDecoder<String> decoder = new StringDecoder()

        when:
        String decodedValue = decoder.decode(value, byteOrder)

        then:
        decodedValue == expected

        where:
        expected | byteOrder                             | value
        "foo"    | Endianness.BIG_ENDIAN.byteOrder(5)    | [0, 111, 0, 111, 0, 102] as byte[]
        "foo"    | Endianness.LITTLE_ENDIAN.byteOrder(5) | [102, 0, 111, 0, 111, 0] as byte[]
        "foo"    | Endianness.PDP_ENDIAN.byteOrder(5)    | [111, 0, 111, 0, 102, 0] as byte[]
    }
}

