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

package io.github.ololx.moonshine.bytes.coding.encoders;

import io.github.ololx.moonshine.bytes.Endianness;
import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class LocalDateTimeEncoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        LocalDateTime.MIN,
                        Endianness.BIG_ENDIAN.byteOrder(Byte.BYTES),
                        new byte[]{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}
                },
                {
                        LocalDateTime.MIN,
                        Endianness.LITTLE_ENDIAN.byteOrder(Byte.BYTES),
                        new byte[]{1, 54, 101, -60, 1, 1, 0, 0, 0, 0, 0, 0, 0}
                },
                {
                        LocalDateTime.MIN,
                        Endianness.PDP_ENDIAN.byteOrder(Byte.BYTES),
                        new byte[]{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}
                },
                {
                        LocalDateTime.MAX,
                        Endianness.BIG_ENDIAN.byteOrder(Byte.BYTES),
                        new byte[]{-1, -1, -1, -1, 12, 31, 23, 59, 59, -1, -1, -1, -1}
                },
                {
                        LocalDateTime.MAX,
                        Endianness.LITTLE_ENDIAN.byteOrder(Byte.BYTES),
                        new byte[]{-1, -55, -102, 59, 12, 31, 23, 59, 59, -1, -55, -102, 59}
                },
                {
                        LocalDateTime.MAX,
                        Endianness.PDP_ENDIAN.byteOrder(Byte.BYTES),
                        new byte[]{-1, -1, -1, -1, 12, 31, 23, 59, 59, -1, -1, -1, -1}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void encode_whenEncodeValue_thenEncodedBytesEqualsExpectedBytes(LocalDateTime value,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] expected) {
        //Given
        // value bytes encoder and origin value
        ValueBytesEncoder<LocalDateTime> encoder = new LocalDateTimeEncoder();

        //When
        // encode value
        byte[] encodedValue = encoder.encode(value, byteOrder);

        //Then
        // encoded value equals expected bytes
        assertEquals(encodedValue, expected);
    }
}
