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

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class ZonedDateTimeDecoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        ZonedDateTime.of(1990, 03, 20, 0, 0, 0, 0, ZoneId.of("UTC")),
                        Endianness.BIG_ENDIAN.byteOrder(18),
                        new byte[]{0, 67, 0, 84, 0, 85, 0, 0, 0, 0, 0, 0, 0, 20, 3, 0, 0, 7, -58}
                },
                {
                        ZonedDateTime.of(1990, 03, 20, 0, 0, 0, 0, ZoneId.of("UTC")),
                        Endianness.LITTLE_ENDIAN.byteOrder(18),
                        new byte[]{-58, 7, 0, 0, 3, 20, 0, 0, 0, 0, 0, 0, 0, 85, 0, 84, 0, 67, 0}
                },
                {
                        ZonedDateTime.of(1990, 03, 20, 0, 0, 0, 0, ZoneId.of("UTC")),
                        Endianness.PDP_ENDIAN.byteOrder(18),
                        new byte[]{67, 0, 84, 0, 85, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0, 3, 7, 0, -58}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void decode_whenEncodeValue_thenEncodedBytesEqualsExpectedBytes(ZonedDateTime expected,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] value) {
        //Given
        // byte decoder and origin value
        ValueBytesDecoder<ZonedDateTime> decoder = new ZonedDateTimeDecoder();

        //When
        // decode value
        ZonedDateTime decodedValue = decoder.decode(value, byteOrder);

        //Then
        // decoded value equals expected bytes
        assertEquals(decodedValue, expected);
    }
}
