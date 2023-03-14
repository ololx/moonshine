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

import io.github.ololx.moonshine.bytes.coding.DoubleCoding;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 15.02.2023 20:38
 *
 * @author Alexander A. Kropotin
 */
public class DoubleBytesTest {

   @Test(dataProvider = "providesDoublesInBigEndian")
   void encodeInBigEndian_whenEncodeDoubleToBytes_thenBytesWillBeInBigEndianOrder(double value, byte[] expected) {
       byte[] actualDoubleInBytes = DoubleCoding.encodeBigEndian(value);
       assertEquals(actualDoubleInBytes, expected);
   }

   @Test(dataProvider = "providesDoublesInLittleEndian")
   void encodeInLittleEndian_whenEncodeDoubleToBytes_thenBytesWillBeInLittleEndianOrder(double value, byte[] expected) {
       byte[] actualDoubleInBytes = DoubleCoding.encodeLittleEndian(value);
       assertEquals(actualDoubleInBytes, DoubleCoding.encodeLittleEndian(value));
   }

    @Test(dataProvider = "providesDoublesInBigEndian")
    void decodeInBigEndian_whenEncodeDoubleToBytes_thenBytesWillBeInBigEndianOrder(double expected, byte[] bytes) {
        double actualDouble = DoubleCoding.decodeBigEndian(bytes);
        assertEquals(actualDouble, expected);
    }

    @Test(dataProvider = "providesDoublesInLittleEndian")
    void decodeInLittleEndian_whenEncodeDoubleToBytes_thenBytesWillBeInLittleEndianOrder(double expected, byte[] bytes) {
        double actualDouble = DoubleCoding.decodeLittleEndian(bytes);
        assertEquals(actualDouble, expected);
    }

    @DataProvider
    static Object[][] providesDoublesInBigEndian() {
       return new Object[][]{
                new Object[]{1, new byte[] {63, -16, 0, 0, 0, 0, 0, 0}},
                new Object[]{2, new byte[] {64, 0, 0, 0, 0, 0, 0, 0}},
                new Object[]{3, new byte[] {64, 8, 0, 0, 0, 0, 0, 0}},
                new Object[]{4, new byte[] {64, 16, 0, 0, 0, 0, 0, 0}},
                new Object[]{5, new byte[] {64, 20, 0, 0, 0, 0, 0, 0}},
        };
    }

   @DataProvider
   static Object[][] providesDoublesInLittleEndian() {
       return new Object[][]{
               new Object[]{1, new byte[] {0, 0, 0, 0, 0, 0, -16, 63}},
               new Object[]{2, new byte[] {0, 0, 0, 0, 0, 0, 0, 64}},
               new Object[]{3, new byte[] {0, 0, 0, 0, 0, 0, 8, 64}},
               new Object[]{4, new byte[] {0, 0, 0, 0, 0, 0, 16, 64}},
               new Object[]{5, new byte[] {0, 0, 0, 0, 0, 0, 20, 64}},
       };
   }
}
