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

import java.util.Arrays;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 20.06.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class StringEncoderTest {

    @DataProvider
    static Object[][] providesValueAndEndianness() {
        return new Object[][]{
                {
                        "foo",
                        Endianness.BIG_ENDIAN.byteOrder(5),
                        new byte[]{0, 111, 0, 111, 0, 102}
                },
                {
                        "foo",
                        Endianness.LITTLE_ENDIAN.byteOrder(5),
                        new byte[]{102, 0, 111, 0, 111, 0}
                },
                {
                        "foo",
                        Endianness.PDP_ENDIAN.byteOrder(5),
                        new byte[]{111, 0, 111, 0, 102, 0}
                },
        };
    }

    @Test(dataProvider = "providesValueAndEndianness")
    public void encode_whenEncodeValue_thenEncodedBytesEqualsExpectedBytes(String value,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] expected) {
        //Given
        // value bytes encoder and origin value
        ValueBytesEncoder<String> encoder = new StringEncoder();

        //When
        // encode value
        byte[] encodedValue = encoder.encode(value, byteOrder);

        System.out.println(Arrays.toString(encodedValue) + "|" + Arrays.toString(expected));
        //Then
        // encoded value equals expected bytes
        assertEquals(encodedValue, expected);
    }
}
