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

import io.github.ololx.moonshine.bytes.Bytes;
import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;
import org.testng.annotations.Test;

import java.util.stream.Stream;

import static io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * project moonshine
 * created 13.03.2023 18:28
 *
 * @author Alexander A. Kropotin
 */
public class ValueBytesEncoderTest {

    @Test
    public void value8BitEncoder_whenGetNewInstanceOfEncoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesEncoder<Byte> encoder = value8BitEncoder();

        //Then
        // instance is not null
        assertNotNull(encoder);
    }

    @Test
    public void value16BitEncoder_whenGetNewInstanceOfEncoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesEncoder<Short> encoder = value16BitEncoder();

        //Then
        // instance is not null
        assertNotNull(encoder);
    }

    @Test
    public void value32BitEncoder_whenGetNewInstanceOfEncoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesEncoder<Integer> encoder = value32BitEncoder();

        //Then
        // instance is not null
        assertNotNull(encoder);
    }

    @Test
    public void value64BitEncoder_whenGetNewInstanceOfEncoder_thenInstanceIsNotNull() {
        //When
        // get instance
        ValueBytesEncoder<Long> encoder = value64BitEncoder();

        //Then
        // instance is not null
        assertNotNull(encoder);
    }

    //FIXME:: Move in new test class for new class of utils
    @Test
    public void value64BitEncoder_whenGetNewInstanceOfEncoder_thenInstanceIsNotNull2() {
        //When
        // get instance
        byte[] result = Bytes.concat(
                Stream.of((byte) 1, (byte) 2, (byte) 3, (byte) 4)
                        .map(v -> ValueBytesEncoder.value8BitEncoder().encode(v, ByteIndexOperator.identity()))
                        .toArray(byte[][]::new)
        );

        //Then
        // instance is not null
        assertNotNull(result);
        assertEquals(result.length, 4);
        System.out.printf("%s, %s, %s, %s%n", result[0], result[1], result[2], result[3]);
    }
}
