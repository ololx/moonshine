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

import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;

import java.util.Arrays;

/**
 * The encoder that converts given value to a byte array using the specified
 * endianness.
 *
 * project moonshine
 * created 19.06.2023 10:02
 *
 * @author Alexander A. Kropotin
 */
public class StringEncoder implements ValueBytesEncoder<String> {

    private static final CharEncoder charEncoder = new CharEncoder();

    /**
     * Encodes a given value to a byte array using the specified endianness
     * starting at the specified offset.
     *
     * @param value the value type {@code String} to be encoded
     * @param offset the starting offset for encoding in the byte array
     * @param endianness the endianness to be used for encoding
     * @return the byte array that contains the encoded value
     */
    @Override
    public byte[] encode(String value, int offset, ByteIndexOperator endianness) {
        if (value.isEmpty()) {
            return new byte[offset];
        }

        byte[][] encoded = new byte[value.length()][];
        encoded[0] = ValueBytesEncoder.value16BitEncoder().encode((short) value.charAt(0), offset, endianness);

        for (int charIndex = 1; charIndex < value.length(); charIndex++) {
            encoded[charIndex] = charEncoder.encode(value.charAt(charIndex), endianness);
        }

        return ValueBytesEncoder.concat(encoded);
    }
}
