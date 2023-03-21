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

package io.github.ololx.moonshine.bytes;

import io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder.*;
import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 13.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class ValueBytesEncoderTest {

    @DataProvider(name = "byteProvider")
    public static Object[][] byteProvider() {
        return new Object[][]{
                {Byte.MIN_VALUE, ByteIndexOperator.identity(), new byte[]{-128}},
                {(byte) 0, ByteIndexOperator.identity(), new byte[]{0}},
                {Byte.MAX_VALUE, ByteIndexOperator.identity(), new byte[]{127}},
        };
    }

    @Test(dataProvider = "byteProvider")
    public void testValue8BitEncoder(Byte value, ByteIndexOperator byteOrder, byte[] expected) {
        ValueBytesEncoder<Byte> encoder = value8BitEncoder();
        byte[] encodedValue = encoder.encode(value, byteOrder);
        assertEquals(encodedValue, expected);
    }

    @DataProvider(name = "shortProvider")
    public static Object[][] shortProvider() {
        return new Object[][]{
                {Short.MIN_VALUE, Endianness.BIG_ENDIAN.byteOrder(1), new byte[]{-128, 0}},
                {(short) -258, Endianness.BIG_ENDIAN.byteOrder(1), new byte[]{-2, -2}},
                {(short) 0, Endianness.BIG_ENDIAN.byteOrder(1), new byte[]{0, 0}},
                {(short) 258, Endianness.BIG_ENDIAN.byteOrder(1), new byte[]{1, 2}},
                {Short.MAX_VALUE, Endianness.BIG_ENDIAN.byteOrder(1), new byte[]{127, -1}},
        };
    }

    @Test(dataProvider = "shortProvider")
    public void testValue16BitEncoder(Short value, ByteIndexOperator byteOrder, byte[] expected) {
        ValueBytesEncoder<Short> encoder = value16BitEncoder();
        byte[] encodedValue = encoder.encode(value, byteOrder);
        assertEquals(encodedValue, expected);
    }

    @DataProvider(name = "intProvider")
    public static Object[][] intProvider() {
        return new Object[][]{
                {Integer.MIN_VALUE, Endianness.BIG_ENDIAN.byteOrder(3), new byte[]{-128, 0, 0, 0}},
                {-65536, Endianness.BIG_ENDIAN.byteOrder(3), new byte[]{-1, -1, 0, 0}},
                {-1, Endianness.BIG_ENDIAN.byteOrder(3), new byte[]{-1, -1, -1, -1}},
                {0, Endianness.BIG_ENDIAN.byteOrder(3), new byte[]{0, 0, 0, 0}},
                {65536, Endianness.BIG_ENDIAN.byteOrder(3), new byte[]{0, 1, 0, 0}},
                {Integer.MAX_VALUE, Endianness.BIG_ENDIAN.byteOrder(3), new byte[]{127, -1, -1, -1}},
        };
    }

    @Test(dataProvider = "intProvider")
    public void testValue32BitEncoder(Integer value, ByteIndexOperator byteOrder, byte[] expected) {
        ValueBytesEncoder<Integer> encoder = value32BitEncoder();
        byte[] encodedValue = encoder.encode(value, byteOrder);
        assertEquals(encodedValue, expected);
    }

    @DataProvider(name = "encodeData")
    public Object[][] encodeData() {
        return new Object[][] {
                {Long.MIN_VALUE, 0, ByteIndexOperator.identity(), new byte[]{0, 0, 0, 0, 0, 0, 0, -128}},
                {0L, 0, ByteIndexOperator.identity(), new byte[]{0, 0, 0, 0, 0, 0, 0, 0}},
                {255L, 0, ByteIndexOperator.identity(), new byte[]{-1, 0, 0, 0, 0, 0, 0, 0}},
                {-1L, 0, ByteIndexOperator.identity(), new byte[]{-1, -1, -1, -1, -1, -1, -1, -1}},
                {65536L, 0, ByteIndexOperator.identity(), new byte[]{0, 0, 1, 0, 0, 0, 0, 0}},
                {Long.MAX_VALUE, 0, ByteIndexOperator.identity(), new byte[]{-1, -1, -1, -1, -1, -1, -1, 127}},
        };
    }

    @Test(dataProvider = "encodeData")
    public void testValue64BitEncoder(long value, int offset, ByteIndexOperator endianness, byte[] expected) {
        ValueBytesEncoder<Long> encoder = value64BitEncoder();
        byte[] actual = encoder.encode(value, offset, endianness);
        assertEquals(actual, expected);
    }
}
