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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
public class EndiannessTest {

    @Test(invocationCount = 10)
    public void BIG_ENDIAN_whenGetByteOrderFromEndianessConst_thenThisByteOrderEqualsExpected() {
        //When
        // get order from const
        ByteOrder byteOrder = Endianness.BIG_ENDIAN;

        //Then
        // name of byte order equals expected
        assertEquals(Endianness.BIG_ENDIAN.getName(), "Big-Endian");
        // id of byte order equals expected
        assertEquals(Endianness.BIG_ENDIAN.getId(), 0x1);

        // byte order operator is same expected
        assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(0), 3);
        assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(1), 2);
        assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(2), 1);
        assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(3), 0);
    }

    @Test(invocationCount = 10)
    public void LITTLE_ENDIAN_whenGetByteOrderFromEndianessConst_thenThisByteOrderEqualsExpected() {
        //When
        // get order from const
        ByteOrder byteOrder = Endianness.LITTLE_ENDIAN;

        //Then
        // name of byte order equals expected
        assertEquals(Endianness.LITTLE_ENDIAN.getName(), "Little-Endian");
        // id of byte order equals expected
        assertEquals(Endianness.LITTLE_ENDIAN.getId(), 0x2);

        // byte order operator is same expected
        assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(0), 0);
        assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(1), 1);
        assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(2), 2);
        assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(3), 3);
    }

    @Test(invocationCount = 10)
    public void PDP_ENDIAN_whenGetByteOrderFromEndianessConst_thenThisByteOrderEqualsExpected() {
        //When
        // get order from const
        ByteOrder byteOrder = Endianness.LITTLE_ENDIAN;

        //Then
        // name of byte order equals expected
        assertEquals(Endianness.PDP_ENDIAN.getName(), "PDP-Endian");
        // id of byte order equals expected
        assertEquals(Endianness.PDP_ENDIAN.getId(), 0x3);

        // byte order operator is same expected
        assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(0), 2);
        assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(1), 3);
        assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(2), 0);
        assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(3), 1);
    }

    @Test(invocationCount = 10)
    public void DEFAULT_whenGetByteOrderFromEndianessConst_thenThisByteOrderEqualsExpected() {
        //When
        // get order from const
        ByteOrder byteOrder = Endianness.DEFAULT;

        //Then
        // byte order equals big endian
        assertEquals(byteOrder, Endianness.BIG_ENDIAN);
    }

    @Test(invocationCount = 10)
    public void NATIVE_whenGetByteOrderFromEndianessConst_thenThisByteOrderEqualsExpected() {
        //When
        // get order from const
        ByteOrder byteOrder = Endianness.NATIVE;

        //Then
        // byte order equals system native
        ByteOrder expected = UnsafeHelper.getInstance().isBigEndian()
                ?  Endianness.BIG_ENDIAN
                : Endianness.LITTLE_ENDIAN;
        assertEquals(byteOrder, expected);
    }
}
