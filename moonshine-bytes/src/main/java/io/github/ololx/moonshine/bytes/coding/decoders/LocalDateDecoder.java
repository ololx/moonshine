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
import io.github.ololx.moonshine.bytes.coding.Bytes;

import java.time.LocalDate;

import static io.github.ololx.moonshine.bytes.coding.ByteIndexOperator.identity;

/**
 * The decoder that converts byte arrays to a {@code Byte} value.
 *
 * project moonshine
 * created 20.06.2023 07:50
 *
 * @author Alexander A. Kropotin
 */
public class LocalDateDecoder implements ValueBytesDecoder<LocalDate> {

    /**
     * Decodes a byte array to a {@code LocalDate} value starting at a given offset
     * with specified endianness.
     *
     * @param bytes the byte array to decode
     * @param offset the offset at which decoding should start
     * @param endianness the endianness to be used for encoding
     * @return the decoded value of given type
     * @throws IndexOutOfBoundsException if {@code offset + endianness.appy(i)}
     * is out of the {@code bytes} bounds
     */
    @Override
    public LocalDate decode(byte[] bytes, int offset, ByteIndexOperator endianness) {
        byte[] reordered = Bytes.reorder(bytes, offset, endianness, identity());

        return LocalDate.of(
                ValueBytesDecoder.value32BitDecoder().decode(reordered, offset, identity()),
                ValueBytesDecoder.value8BitDecoder().decode(reordered, offset + 4, identity()),
                ValueBytesDecoder.value8BitDecoder().decode(reordered, offset + 5, identity())
        );
    }
}
