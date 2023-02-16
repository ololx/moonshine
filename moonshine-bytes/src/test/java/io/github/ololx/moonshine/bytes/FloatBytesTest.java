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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 16.02.2023 10:07
 *
 * @author Alexander A. Kropotin
 */
public class FloatBytesTest {

   @Test(dataProvider = "providesFloatsInBigEndian")
   void encodeInBigEndian_whenEncodeFloatToBytes_thenBytesWillBeInBigEndianOrder(float value, byte[] expected) {
       byte[] actualFloatInBytes = FloatBytes.encodeBigEndian(value);
       assertEquals(actualFloatInBytes, expected);
   }

   @Test(dataProvider = "providesFloatsInLittleEndian")
   void encodeInLittleEndian_whenEncodeFloatToBytes_thenBytesWillBeInLittleEndianOrder(float value, byte[] expected) {
       byte[] actualFloatInBytes = FloatBytes.encodeLittleEndian(value);
       assertEquals(actualFloatInBytes, FloatBytes.encodeLittleEndian(value));
   }

    @Test(dataProvider = "providesFloatsInBigEndian")
    void decodeInBigEndian_whenEncodeFloatToBytes_thenBytesWillBeInBigEndianOrder(float expected, byte[] bytes) {
        float actualFloat = FloatBytes.decodeBigEndian(bytes);
        assertEquals(actualFloat, expected);
    }

    @Test(dataProvider = "providesFloatsInLittleEndian")
    void decodeInLittleEndian_whenEncodeFloatToBytes_thenBytesWillBeInLittleEndianOrder(float expected, byte[] bytes) {
        float actualFloat = FloatBytes.decodeLittleEndian(bytes);
        assertEquals(actualFloat, expected);
    }

    @DataProvider
    static Object[][] providesFloatsInBigEndian() {
       return new Object[][]{
                new Object[]{1, new byte[] {63, -128, 0, 0,}},
                new Object[]{2, new byte[] {64, 0, 0, 0,}},
                new Object[]{3, new byte[] {64, 64, 0, 0,}},
                new Object[]{4, new byte[] {64, -128, 0, 0,}},
                new Object[]{5, new byte[] {64, -96, 0, 0,}},
        };
    }

   @DataProvider
   static Object[][] providesFloatsInLittleEndian() {
       return new Object[][]{
               new Object[]{1, new byte[] {0, 0, -128, 63}},
               new Object[]{2, new byte[] {0, 0, 0, 64}},
               new Object[]{3, new byte[] {0, 0, 64, 64}},
               new Object[]{4, new byte[] {0, 0, -128, 64}},
               new Object[]{5, new byte[] {0, 0, -96, 64}},
       };
   }
}
