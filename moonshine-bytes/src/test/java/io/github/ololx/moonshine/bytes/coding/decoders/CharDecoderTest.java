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

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 25.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class CharDecoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        Character.MIN_VALUE,
                        Endianness.BIG_ENDIAN.byteOrder(1),
                        new byte[]{0, 0}
                },
                {
                        Character.MIN_VALUE,
                        Endianness.LITTLE_ENDIAN.byteOrder(1),
                        new byte[]{0, 0}
                },
                {
                        Character.MIN_VALUE,
                        Endianness.PDP_ENDIAN.byteOrder(1),
                        new byte[]{0, 0}
                },
                {
                        (char) 0,
                        Endianness.BIG_ENDIAN.byteOrder(1),
                        new byte[]{0, 0}
                },
                {
                        (char) 0,
                        Endianness.LITTLE_ENDIAN.byteOrder(1),
                        new byte[]{0, 0}
                },
                {
                        (char) 0,
                        Endianness.PDP_ENDIAN.byteOrder(1),
                        new byte[]{0, 0}
                },
                {
                        (char) -258,
                        Endianness.BIG_ENDIAN.byteOrder(1),
                        new byte[]{-2, -2}
                },
                {
                        (char) -258,
                        Endianness.LITTLE_ENDIAN.byteOrder(1),
                        new byte[]{-2, -2}
                },
                {
                        (char) -258,
                        Endianness.PDP_ENDIAN.byteOrder(1),
                        new byte[]{-2, -2}
                },
                {
                        (char) 258,
                        Endianness.BIG_ENDIAN.byteOrder(1),
                        new byte[]{1, 2}
                },
                {
                        (char) 258,
                        Endianness.LITTLE_ENDIAN.byteOrder(1),
                        new byte[]{2, 1}
                },
                {
                        (char) 258,
                        Endianness.PDP_ENDIAN.byteOrder(1),
                        new byte[]{2, 1}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void decode_whenDecodeValue_thenDecodedBytesEqualsExpectedBytes(char expected,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] value) {
        //Given
        // char decoder and origin value
        CharDecoder decoder = new CharDecoder();

        //When
        // decode value
        char decodedValue = decoder.decode(value, byteOrder);

        //Then
        // decoded value equals expected bytes
        assertEquals(decodedValue, expected);
    }
}
