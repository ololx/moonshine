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

import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * project moonshine
 * created 25.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class LongDecoderTest extends Value64BitDecoderTest {

    @Test(dataProvider = "providesValueAndEndianness")
    public void decode_whenDecodeValue_thenDecodedBytesEqualsExpectedBytes(long expected,
                                                                           ByteIndexOperator byteOrder,
                                                                           byte[] value) {
        //Given
        // long decoder and origin value
        LongDecoder decoder = new LongDecoder();

        //When
        // decode value
        long decodedValue = decoder.decode(value, byteOrder);

        //Then
        // decoded value equals expected bytes
        assertEquals(decodedValue, expected);
    }
}