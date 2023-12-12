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
 * The decoder that converts byte arrays to value of given type.
 *
 * @param <T> the type of value to be decoded
 *
 *            project moonshine
 *            created 27.02.2023 10:50
 *
 * @author Alexander A. Kropotin
 */
public interface ValueBytesDecoder<T> {

    /**
     * Returns a {@code ValueBytesDecoder} instance that can encode 8-bit
     * values to a byte array using the specified endianness.
     *
     * @return the {@code ValueBytesEncoder} instance for 8-bit values
     *
     * @throws IndexOutOfBoundsException if {@code offset + endianness.appy(i)}
     *                                   is out of the {@code bytes} bounds
     * @implSpec This implementation decodes a value from the byte array of size
     *     {@code offset * Byte.BYTES}, where the encoded value is stored at the
     *     {@code offset + i} index in the array. The endianness is used to determine
     *     the byte order of the encoded value
     */
    static ValueBytesDecoder<Byte> value8BitDecoder() {
        return (bytes, offset, endianness) -> {
            byte decoded = 0;
            decoded |= (bytes[offset] & 0xFF);

            return decoded;
        };
    }

    /**
     * Returns a {@code ValueBytesDecoder} instance that can encode 16-bit
     * values to a byte array using the specified endianness.
     *
     * @return the {@code ValueBytesEncoder} instance for 16-bit values
     *
     * @throws IndexOutOfBoundsException if {@code offset + endianness.appy(i)}
     *                                   is out of the {@code bytes} bounds
     * @implSpec This implementation decodes a value from the byte array of size
     *     {@code offset * Byte.BYTES}, where the encoded value is stored at the
     *     {@code offset + i} index in the array. The endianness is used to determine
     *     the byte order of the encoded value
     */
    static ValueBytesDecoder<Short> value16BitDecoder() {
        return (bytes, offset, endianness) -> {
            short decoded = 0;
            decoded |= (bytes[offset] & 0xFF) << endianness.apply(0) * Byte.SIZE;
            decoded |= (bytes[offset + 1] & 0xFF) << endianness.apply(1) * Byte.SIZE;

            return decoded;
        };
    }

    /**
     * Returns a {@code ValueBytesDecoder} instance that can encode 32-bit
     * values to a byte array using the specified endianness.
     *
     * @return the {@code ValueBytesEncoder} instance for 32-bit values
     *
     * @throws IndexOutOfBoundsException if {@code offset + endianness.appy(i)}
     *                                   is out of the {@code bytes} bounds
     * @implSpec This implementation decodes a value from the byte array of size
     *     {@code offset * Byte.BYTES}, where the encoded value is stored at the
     *     {@code offset + i} index in the array. The endianness is used to determine
     *     the byte order of the encoded value
     */
    static ValueBytesDecoder<Integer> value32BitDecoder() {
        return (bytes, offset, endianness) -> {
            int decoded = 0;
            decoded |= (bytes[offset] & 0xFF) << endianness.apply(0) * Byte.SIZE;
            decoded |= (bytes[offset + 1] & 0xFF) << endianness.apply(1) * Byte.SIZE;
            decoded |= (bytes[offset + 2] & 0xFF) << endianness.apply(2) * Byte.SIZE;
            decoded |= (bytes[offset + 3] & 0xFF) << endianness.apply(3) * Byte.SIZE;

            return decoded;
        };
    }

    /**
     * Returns a {@code ValueBytesDecoder} instance that can encode 64-bit
     * values to a byte array using the specified endianness.
     *
     * @return the {@code ValueBytesEncoder} instance for 64-bit values
     *
     * @throws IndexOutOfBoundsException if {@code offset + endianness.appy(i)}
     *                                   is out of the {@code bytes} bounds
     * @implSpec This implementation decodes a value from the byte array of size
     *     {@code offset * Byte.BYTES}, where the encoded value is stored at the
     *     {@code offset + i} index in the array. The endianness is used to determine
     *     the byte order of the encoded value
     */
    static ValueBytesDecoder<Long> value64BitDecoder() {
        return (bytes, offset, endianness) -> {
            long decoded = 0;
            decoded |= (bytes[offset] & 0xFFL) << endianness.apply(0) * Byte.SIZE;
            decoded |= (bytes[offset + 1] & 0xFFL) << endianness.apply(1) * Byte.SIZE;
            decoded |= (bytes[offset + 2] & 0xFFL) << endianness.apply(2) * Byte.SIZE;
            decoded |= (bytes[offset + 3] & 0xFFL) << endianness.apply(3) * Byte.SIZE;
            decoded |= (bytes[offset + 4] & 0xFFL) << endianness.apply(4) * Byte.SIZE;
            decoded |= (bytes[offset + 5] & 0xFFL) << endianness.apply(5) * Byte.SIZE;
            decoded |= (bytes[offset + 6] & 0xFFL) << endianness.apply(6) * Byte.SIZE;
            decoded |= (bytes[offset + 7] & 0xFFL) << endianness.apply(7) * Byte.SIZE;

            return decoded;
        };
    }

    /**
     * Decodes a byte array to a value of given type with specified endianness.
     *
     * @param bytes      the byte array to decode
     * @param endianness the endianness to be used for encoding
     *
     * @return the decoded value of given type
     *
     * @implSpec This method is a default implementation of the {@code decode}
     *     method that starts decoding at offset 0.
     */
    default T decode(byte[] bytes, ByteIndexOperator endianness) {
        return decode(bytes, 0, endianness);
    }

    /**
     * Decodes a byte array to a value of given type starting at a given offset
     * with specified endianness.
     *
     * @param bytes      the byte array to decode
     * @param offset     the offset at which decoding should start
     * @param endianness the endianness to be used for encoding
     *
     * @return the decoded value of given type
     */
    T decode(byte[] bytes, int offset, ByteIndexOperator endianness);
}
