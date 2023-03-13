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
 * created 16.02.2023 12:23
 *
 * @author Alexander A. Kropotin
 */
public class IntByteEncodedWrapperTest {

   @Test(dataProvider = "providesIntegersInBigEndian")
   void toBytes_whenEncodeIntToBytes_thenBytesWillBeInBigEndianOrder(int value, byte[] expected) {
       SingleValueBytes<Integer> encoded = IntBytes.fromValue(value);
       byte[] actualIntInBytes = encoded.getBytes();
       assertEquals(actualIntInBytes, expected);
   }

    @Test(dataProvider = "providesIntegersInBigEndian")
    void toBytesBigEndian_whenEncodeIntToBytes_thenBytesWillBeInBigEndianOrder(int value, byte[] expected) {
        SingleValueBytes<Integer> encoded = IntBytes.fromValue(value);
        byte[] actualIntInBytes = encoded.getBytes(Endianness.BIG_ENDIAN);
        assertEquals(actualIntInBytes, expected);
    }

    @Test(dataProvider = "providesIntegersInLittleEndian")
    void toBytesLittleEndian_whenEncodeIntToBytes_thenBytesWillBeInLittleEndianOrder(int value, byte[] expected) {
        SingleValueBytes<Integer> encoded = IntBytes.fromValue(value);
        byte[] actualIntInBytes = encoded.getBytes(Endianness.LITTLE_ENDIAN);
        assertEquals(actualIntInBytes, expected);
    }

    @DataProvider
    static Object[][] providesIntegersInBigEndian() {
        return new Object[][]{
                new Object[]{1, new byte[] {0, 0, 0, 1}},
                new Object[]{2, new byte[] {0, 0, 0, 2}},
                new Object[]{3, new byte[] {0, 0, 0, 3}},
                new Object[]{4, new byte[] {0, 0, 0, 4}},
                new Object[]{5, new byte[] {0, 0, 0, 5}},
        };
    }

    @DataProvider
    static Object[][] providesIntegersInLittleEndian() {
        return new Object[][]{
                new Object[]{1, new byte[] {1, 0, 0, 0}},
                new Object[]{2, new byte[] {2, 0, 0, 0}},
                new Object[]{3, new byte[] {3, 0, 0, 0}},
                new Object[]{4, new byte[] {4, 0, 0, 0}},
                new Object[]{5, new byte[] {5, 0, 0, 0}},
        };
    }
}
