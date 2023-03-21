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

import io.github.ololx.moonshine.bytes.ByteIndexOperator;

/**
 * The encoder that converts given value to a byte array using the specified
 * endianness.
 *
 * project moonshine
 * created 15.03.2023 21:50
 *
 * @author Alexander A. Kropotin
 */
public class FloatEncoder implements ValueBytesEncoder<Float> {

    /**
     * Encodes a given value to a byte array using the specified endianness
     * starting at the specified offset.
     *
     * @param value the value type {@code Float} to be encoded
     * @param offset the starting offset for encoding in the byte array
     * @param endianness the endianness to be used for encoding
     * @return the byte array that contains the encoded value
     */
    @Override
    public byte[] encode(Float value, int offset, ByteIndexOperator endianness) {
        int intBitsValue = Float.floatToIntBits(value);
        return ValueBytesEncoder.value32BitEncoder().encode(intBitsValue, offset, endianness);
    }
}
