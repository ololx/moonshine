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

import static io.github.ololx.moonshine.bytes.coding.decoders.ValueBytesDecoder.value32BitDecoder;
import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 13.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class Value32BitDecoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        Integer.MIN_VALUE,
                        Endianness.BIG_ENDIAN.byteOrder(3),
                        new byte[]{-128, 0, 0, 0}
                },
                {
                        Integer.MIN_VALUE,
                        Endianness.LITTLE_ENDIAN.byteOrder(3),
                        new byte[]{0, 0, 0, -128}
                },
                {
                        Integer.MIN_VALUE,
                        Endianness.PDP_ENDIAN.byteOrder(3),
                        new byte[]{0, -128, 0, 0}
                },
                {
                        0,
                        Endianness.BIG_ENDIAN.byteOrder(3),
                        new byte[]{0, 0, 0, 0}
                },
                {
                        0,
                        Endianness.LITTLE_ENDIAN.byteOrder(3),
                        new byte[]{0, 0, 0, 0}
                },
                {
                        0,
                        Endianness.PDP_ENDIAN.byteOrder(3),
                        new byte[]{0, 0, 0 , 0}
                },
                {
                        -65536,
                        Endianness.BIG_ENDIAN.byteOrder(3),
                        new byte[]{-1, -1, 0, 0}
                },
                {
                        -65536,
                        Endianness.LITTLE_ENDIAN.byteOrder(3),
                        new byte[]{0, 0, -1, -1}
                },
                {
                        -65536,
                        Endianness.PDP_ENDIAN.byteOrder(3),
                        new byte[]{-1, -1, 0, 0}
                },
                {
                        65536,
                        Endianness.BIG_ENDIAN.byteOrder(3),
                        new byte[]{0, 1, 0, 0}
                },
                {
                        65536,
                        Endianness.LITTLE_ENDIAN.byteOrder(3),
                        new byte[]{0, 0, 1, 0}
                },
                {
                        65536,
                        Endianness.PDP_ENDIAN.byteOrder(3),
                        new byte[]{1, 0, 0, 0}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void decode_whenDecodeBytes_thenDecodedValueEqualsExpectedValue(int expected,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] value) {
        //Given
        // value bytes decoder and origin value
        ValueBytesDecoder<Integer> decoder = value32BitDecoder();

        //When
        // decode value
        int decodedValue = decoder.decode(value, byteOrder);

        //Then
        // decoded value equals expected bytes
        assertEquals(decodedValue, expected);
    }
}
