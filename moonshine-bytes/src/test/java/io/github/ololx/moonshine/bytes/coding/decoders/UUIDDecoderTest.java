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

package io.github.ololx.moonshine.bytes.coding.decoders;

import io.github.ololx.moonshine.bytes.Endianness;
import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class UUIDDecoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        UUID.fromString("5cbb7015-7b74-452f-afb3-ce82e8637bc9"),
                        Endianness.BIG_ENDIAN.byteOrder(15),
                        new byte[]{-81, -77, -50, -126, -24, 99, 123, -55, 92, -69, 112, 21, 123, 116, 69, 47}
                },
                {
                        UUID.fromString("5cbb7015-7b74-452f-afb3-ce82e8637bc9"),
                        Endianness.LITTLE_ENDIAN.byteOrder(15),
                        new byte[]{47, 69, 116, 123, 21, 112, -69, 92, -55, 123, 99, -24, -126, -50, -77, -81}
                },
                {
                        UUID.fromString("5cbb7015-7b74-452f-afb3-ce82e8637bc9"),
                        Endianness.PDP_ENDIAN.byteOrder(15),
                        new byte[]{-77, -81, -126, -50, 99, -24, -55, 123, -69, 92, 21, 112, 116, 123, 47, 69}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void decode_whenEncodeValue_thenEncodedBytesEqualsExpectedBytes(UUID expected,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] value) {
        //Given
        // byte decoder and origin value
        ValueBytesDecoder<UUID> decoder = new UUIDDecoder();

        //When
        // decode value
        UUID decodedValue = decoder.decode(value, byteOrder);

        //Then
        // decoded value equals expected bytes
        assertEquals(decodedValue, expected);
    }
}
