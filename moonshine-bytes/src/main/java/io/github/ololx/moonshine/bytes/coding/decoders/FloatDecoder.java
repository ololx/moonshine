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

/**
 * The decoder that converts byte arrays to a {@code Float} value.
 *
 * project moonshine
 * created 23.03.2023 07:50
 *
 * @author Alexander A. Kropotin
 */
public class FloatDecoder implements ValueBytesDecoder<Float> {

    /**
     * Decodes a byte array to a {@code Float} value starting at a given
     * offset with specified endianness.
     *
     * @param bytes the byte array to decode
     * @param offset the offset at which decoding should start
     * @param endianness the endianness to be used for encoding
     * @return the decoded value of given type
     */
    @Override
    public Float decode(byte[] bytes, int offset, ByteIndexOperator endianness) {
        int intValue = ValueBytesDecoder.value32BitDecoder().decode(bytes, offset, endianness);
        return Float.intBitsToFloat(intValue);
    }
}
