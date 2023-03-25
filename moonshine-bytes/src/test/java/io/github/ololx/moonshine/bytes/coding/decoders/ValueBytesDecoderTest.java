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

import org.testng.annotations.Test;

import static io.github.ololx.moonshine.bytes.coding.decoders.ValueBytesDecoder.*;
import static org.testng.Assert.assertNotNull;

/**
 * project moonshine
 * created 13.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class ValueBytesDecoderTest {

    @Test
    public void value8BitDecoder_whenGetNewInstanceOfDecoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesDecoder<Byte> decoder = value8BitDecoder();

        //Then
        // instance is not null
        assertNotNull(decoder);
    }

    @Test
    public void value16BitDecoder_whenGetNewInstanceOfDecoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesDecoder<Short> decoder = value16BitDecoder();

        //Then
        // instance is not null
        assertNotNull(decoder);
    }

    @Test
    public void value32BitDecoder_whenGetNewInstanceOfDecoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesDecoder<Integer> decoder = value32BitDecoder();

        //Then
        // instance is not null
        assertNotNull(decoder);
    }

    @Test
    public void value64BitDecoder_whenGetNewInstanceOfDecoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesDecoder<Long> decoder = value64BitDecoder();

        //Then
        // instance is not null
        assertNotNull(decoder);
    }
}
