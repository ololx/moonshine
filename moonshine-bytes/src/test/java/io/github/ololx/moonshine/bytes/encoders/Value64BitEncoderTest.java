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

package io.github.ololx.moonshine.bytes.encoders;

import io.github.ololx.moonshine.bytes.Endianness;
import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;
import io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder.value64BitEncoder;
import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 13.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class Value64BitEncoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][] {
                {
                        Long.MIN_VALUE,
                        Endianness.BIG_ENDIAN.byteOrder(Long.BYTES),
                        new byte[]{-128, 0, 0, 0, 0, 0, 0, 0}
                },
                {
                        Long.MIN_VALUE,
                        Endianness.LITTLE_ENDIAN.byteOrder(Long.BYTES),
                        new byte[]{0, 0, 0, 0, 0, 0, 0, -128}
                },
                {
                        Long.MIN_VALUE,
                        Endianness.PDP_ENDIAN.byteOrder(Long.BYTES),
                        new byte[]{0, -128, 0, 0, 0, 0, 0, 0}
                },
                {
                        0L,
                        Endianness.BIG_ENDIAN.byteOrder(Long.BYTES),
                        new byte[]{0, 0, 0, 0, 0, 0, 0, 0}
                },
                {
                        0L,
                        Endianness.LITTLE_ENDIAN.byteOrder(Long.BYTES),
                        new byte[]{0, 0, 0, 0, 0, 0, 0, 0}
                },
                {
                        0L,
                        Endianness.PDP_ENDIAN.byteOrder(Long.BYTES),
                        new byte[]{0, 0, 0 , 0, 0, 0, 0, 0}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void encode_whenEncodeValue_thenEncodedBytesEqualsExpectedBytes(Long value,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] expected) {
        //Given
        //value bytes encoder and origin value
        ValueBytesEncoder<Long> encoder = value64BitEncoder();

        //When
        //encode value
        byte[] encodedValue = encoder.encode(value, byteOrder);

        //Then
        //encoded value equals expected bytes
        assertEquals(encodedValue, expected);
    }
}
