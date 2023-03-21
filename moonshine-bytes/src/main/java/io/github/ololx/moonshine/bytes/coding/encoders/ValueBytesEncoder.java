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

import java.util.function.IntUnaryOperator;

/**
 * The encoder that converts given value to a byte array using the specified
 * endianness.
 *
 * @param <T> The type of value to be encoded
 *
 * project moonshine
 * created 27.02.2023 10:50
 *
 * @author Alexander A. Kropotin
 */
public interface ValueBytesEncoder<T> {

    /**
     * Encodes a given value to a byte array using the specified endianness
     * starting at the specified offset.
     *
     * @param value the value to be encoded
     * @param offset the starting offset for encoding in the byte array
     * @param endianness the endianness to be used for encoding
     * @return the byte array that contains the encoded value
     */
    byte[] encode(T value, int offset, int[] endianness);

    /**
     * Encodes a given value to a byte array using the specified endianness
     * starting at offset 0.
     *
     * @implSpec
     * This method is a default implementation of the {@code encode}
     * method that starts encoding at offset 0.
     *
     * @param value the value to be encoded
     * @param endianness the endianness to be used for encoding
     * @return the byte array that contains the encoded value
     */
    default byte[] encode(T value, int[] endianness) {
        return this.encode(value, 0, endianness);
    }

    /**
     * Returns a {@code ValueBytesEncoder} instance that can encode 8-bit
     * values to a byte array using the specified endianness.
     *
     * @implSpec
     * This implementation encodes the value to a byte array of size
     * {@code offset * Byte.BYTES}, where the encoded value is stored at the
     * {@code offset + i} index in the array. The endianness is used to
     * determine the byte order of the encoded value
     *
     * @return the {@code ValueBytesEncoder} instance for 8-bit values
     */
    static ValueBytesEncoder<Byte> value8BitEncoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Byte.BYTES];
            encoded[offset] = (byte) ((value >> endianness[0] * Byte.SIZE) & 0xFF);

            return encoded;
        };
    }

    /**
     * Returns a {@code ValueBytesEncoder} instance that can encode 16-bit
     * values to a byte array using the specified endianness.
     *
     * @implSpec
     * This implementation encodes the value to a byte array of size
     * {@code offset * Byte.BYTES}, where the encoded value is stored at the
     * {@code offset + i} index in the array. The endianness is used to
     * determine the byte order of the encoded value
     *
     * @return the {@code ValueBytesEncoder} instance for 16-bit values
     */
    static ValueBytesEncoder<Short> value16BitEncoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Short.BYTES];
            encoded[offset] = (byte) ((value >> endianness[0] * Byte.SIZE) & 0xFF);
            encoded[offset + 1] = (byte) ((value >> endianness[1] * Byte.SIZE) & 0xFF);

            return encoded;
        };
    }

    /**
     * Returns a {@code ValueBytesEncoder} instance that can encode 32-bit
     * values to a byte array using the specified endianness.
     *
     * @implSpec
     * This implementation encodes the value to a byte array of size
     * {@code offset * Byte.BYTES}, where the encoded value is stored at the
     * {@code offset + i} index in the array. The endianness is used to
     * determine the byte order of the encoded value
     *
     * @return the {@code ValueBytesEncoder} instance for 32-bit values
     */
    static ValueBytesEncoder<Integer> value32BitEncoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Integer.BYTES];
            encoded[offset] = (byte) ((value >> endianness[0] * Byte.SIZE) & 0xFF);
            encoded[offset + 1] = (byte) ((value >> endianness[1] * Byte.SIZE) & 0xFF);
            encoded[offset + 2] = (byte) ((value >> endianness[2] * Byte.SIZE) & 0xFF);
            encoded[offset + 3] = (byte) ((value >> endianness[3] * Byte.SIZE) & 0xFF);

            return encoded;
        };
    }

    /**
     * Returns a {@code ValueBytesEncoder} instance that can encode 64-bit
     * values to a byte array using the specified endianness.
     *
     * @implSpec
     * This implementation encodes the value to a byte array of size
     * {@code offset * Byte.BYTES}, where the encoded value is stored at the
     * {@code offset + i} index in the array. The endianness is used to
     * determine the byte order of the encoded value
     *
     * @return the {@code ValueBytesEncoder} instance for 64-bit values
     */
    static ValueBytesEncoder<Long> value64BitEncoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Long.BYTES];
            encoded[offset] = (byte) ((value >> endianness[0] * Byte.SIZE) & 0xFF);
            encoded[offset + 1] = (byte) ((value >> endianness[1] * Byte.SIZE) & 0xFF);
            encoded[offset + 2] = (byte) ((value >> endianness[2] * Byte.SIZE) & 0xFF);
            encoded[offset + 3] = (byte) ((value >> endianness[3] * Byte.SIZE) & 0xFF);
            encoded[offset + 4] = (byte) ((value >> endianness[4] * Byte.SIZE) & 0xFF);
            encoded[offset + 5] = (byte) ((value >> endianness[5] * Byte.SIZE) & 0xFF);
            encoded[offset + 6] = (byte) ((value >> endianness[6] * Byte.SIZE) & 0xFF);
            encoded[offset + 7] = (byte) ((value >> endianness[7] * Byte.SIZE) & 0xFF);

            return encoded;
        };
    }
}
