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

/**
 * project moonshine
 * created 27.02.2023 10:50
 *
 * @author Alexander A. Kropotin
 */
public interface ValueBytesEncoder<T> {

    byte[] encode(T value, int offset, int[] endianness);

    default byte[] encode(T value, int[] endianness) {
        return this.encode(value, 0, endianness);
    }

    static ValueBytesEncoder<Byte> bit8Encoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Byte.BYTES];
            encoded[offset] = (byte) (value >> endianness[0] * Byte.SIZE);

            return encoded;
        };
    }

    static ValueBytesEncoder<Short> bit16Encoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Short.BYTES];
            encoded[offset] = (byte) (value >> endianness[0] * Byte.SIZE);
            encoded[offset + 1] = (byte) (value >> endianness[1] * Byte.SIZE);

            return encoded;
        };
    }

    static ValueBytesEncoder<Integer> bit32Encoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Integer.BYTES];
            encoded[offset] = (byte) (value >> endianness[0] * Byte.SIZE);
            encoded[offset + 1] = (byte) (value >> endianness[1] * Byte.SIZE);
            encoded[offset + 2] = (byte) (value >> endianness[2] * Byte.SIZE);
            encoded[offset + 3] = (byte) (value >> endianness[3] * Byte.SIZE);

            return encoded;
        };
    }

    static ValueBytesEncoder<Long> bit64Encoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + Long.BYTES];
            encoded[offset] = (byte) (value >> endianness[0] * Byte.SIZE);
            encoded[offset + 1] = (byte) (value >> endianness[1] * Byte.SIZE);
            encoded[offset + 2] = (byte) (value >> endianness[2] * Byte.SIZE);
            encoded[offset + 3] = (byte) (value >> endianness[3] * Byte.SIZE);
            encoded[offset + 4] = (byte) (value >> endianness[4] * Byte.SIZE);
            encoded[offset + 5] = (byte) (value >> endianness[5] * Byte.SIZE);
            encoded[offset + 6] = (byte) (value >> endianness[6] * Byte.SIZE);
            encoded[offset + 7] = (byte) (value >> endianness[7] * Byte.SIZE);

            return encoded;
        };
    }
}
