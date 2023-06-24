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

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class LocalDateEncoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        LocalDate.MIN,
                        Endianness.BIG_ENDIAN.byteOrder(5),
                        new byte[]{1, 1, -60, 101, 54, 1}
                },
                {
                        LocalDate.MIN,
                        Endianness.LITTLE_ENDIAN.byteOrder(5),
                        new byte[]{1, 54, 101, -60, 1, 1}
                },
                {
                        LocalDate.MIN,
                        Endianness.PDP_ENDIAN.byteOrder(5),
                        new byte[]{1, 1, 101, -60, 1, 54}
                },
                {
                        LocalDate.MAX,
                        Endianness.BIG_ENDIAN.byteOrder(5),
                        new byte[]{31, 12, 59, -102, -55, -1}
                },
                {
                        LocalDate.MAX,
                        Endianness.LITTLE_ENDIAN.byteOrder(5),
                        new byte[]{-1, -55, -102, 59, 12, 31}
                },
                {
                        LocalDate.MAX,
                        Endianness.PDP_ENDIAN.byteOrder(5),
                        new byte[]{12, 31, -102, 59, -1, -55}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void encode_whenEncodeValue_thenEncodedBytesEqualsExpectedBytes(LocalDate value,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] expected) {
        //Given
        // value bytes encoder and origin value
        ValueBytesEncoder<LocalDate> encoder = new LocalDateEncoder();

        //When
        // encode value
        byte[] encodedValue = encoder.encode(value, byteOrder);

        //Then
        // encoded value equals expected bytes
        assertEquals(encodedValue, expected);
    }
}
