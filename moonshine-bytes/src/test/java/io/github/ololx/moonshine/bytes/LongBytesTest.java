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

import io.github.ololx.moonshine.bytes.util.LongCoding;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 14.02.2023 15:49
 *
 * @author Alexander A. Kropotin
 */
public class LongBytesTest {

   @Test(dataProvider = "providesLongsInBigEndian")
   void encodeInBigEndian_whenEncodeLongToBytes_thenBytesWillBeInBigEndianOrder(long value, byte[] expected) {
       byte[] actualLongInBytes = LongCoding.encodeBigEndian(value);
       assertEquals(actualLongInBytes, expected);
   }

   @Test(dataProvider = "providesLongsInLittleEndian")
   void encodeInLittleEndian_whenEncodeLongToBytes_thenBytesWillBeInLittleEndianOrder(long value, byte[] expected) {
       byte[] actualLongInBytes = LongCoding.encodeLittleEndian(value);
       assertEquals(actualLongInBytes, expected);
   }

    @Test(dataProvider = "providesLongsInBigEndian")
    void decodeInBigEndian_whenEncodeLongToBytes_thenBytesWillBeInBigEndianOrder(long expected, byte[] bytes) {
        long actualLong = LongCoding.decodeBigEndian(bytes);
        assertEquals(actualLong, expected);
    }

    @Test(dataProvider = "providesLongsInLittleEndian")
    void decodeInLittleEndian_whenEncodeLongToBytes_thenBytesWillBeInLittleEndianOrder(long expected, byte[] bytes) {
        long actualLong = LongCoding.decodeLittleEndian(bytes);
        assertEquals(actualLong, expected);
    }

    @DataProvider
    static Object[][] providesLongsInBigEndian() {
        return new Object[][]{
                new Object[]{1, new byte[] {0, 0, 0, 0, 0, 0, 0, 1}},
                new Object[]{2, new byte[] {0, 0, 0, 0, 0, 0, 0, 2}},
                new Object[]{3, new byte[] {0, 0, 0, 0, 0, 0, 0, 3}},
                new Object[]{4, new byte[] {0, 0, 0, 0, 0, 0, 0, 4}},
                new Object[]{5, new byte[] {0, 0, 0, 0, 0, 0, 0, 5}},
                new Object[]{4617315517961601024L, new byte[] {64, 20, 0, 0, 0, 0, 0, 0}},
        };
    }

   @DataProvider
   static Object[][] providesLongsInLittleEndian() {
       return new Object[][]{
               new Object[]{1, new byte[] {1, 0, 0, 0, 0, 0, 0, 0}},
               new Object[]{2, new byte[] {2, 0, 0, 0, 0, 0, 0, 0}},
               new Object[]{3, new byte[] {3, 0, 0, 0, 0, 0, 0, 0}},
               new Object[]{4, new byte[] {4, 0, 0, 0, 0, 0, 0, 0}},
               new Object[]{5, new byte[] {5, 0, 0, 0, 0, 0, 0, 0}},
               new Object[]{4617315517961601024L, new byte[] {0, 0, 0, 0, 0, 0, 20, 64}},
       };
   }
}
