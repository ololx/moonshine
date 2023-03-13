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
public interface ValueBytesDecoder<T> {

    T decode(byte[] bytes, int offset, int[] endianness);

    default T decode(byte[] bytes, int[] endianness) {
        return decode(bytes, 0, endianness);
    }

    static ValueBytesDecoder<Byte> value8BitDecoder() {
        return (bytes, offset, endianness) -> {
            byte decoded = 0;
            decoded |= (bytes[offset] & 0xFF);

            return decoded;
        };
    }

    static ValueBytesDecoder<Short> value16BitDecoder() {
        return (bytes, offset, endianness) -> {
            short decoded = 0;
            decoded |= (bytes[offset] & 0xFF) << endianness[0] * Byte.SIZE;
            decoded |= (bytes[offset + 1] & 0xFF) << endianness[1] * Byte.SIZE;

            return decoded;
        };
    }

    static ValueBytesDecoder<Integer> value32BitDecoder() {
        return (bytes, offset, endianness) -> {
            int decoded = 0;
            decoded |= (bytes[offset] & 0xFF) << endianness[0] * Byte.SIZE;
            decoded |= (bytes[offset + 1] & 0xFF) << endianness[1] * Byte.SIZE;
            decoded |= (bytes[offset + 2] & 0xFF) << endianness[2] * Byte.SIZE;
            decoded |= (bytes[offset + 3] & 0xFF) << endianness[3] * Byte.SIZE;

            return decoded;
        };
    }

    static ValueBytesDecoder<Long> value64BitDecoder() {
        return (bytes, offset, endianness) -> {
            long decoded = 0;
            decoded |= (bytes[offset] & 0xFFL) << endianness[0] * Byte.SIZE;
            decoded |= (bytes[offset + 1] & 0xFFL) << endianness[1] * Byte.SIZE;
            decoded |= (bytes[offset + 2] & 0xFFL) << endianness[2] * Byte.SIZE;
            decoded |= (bytes[offset + 3] & 0xFFL) << endianness[3] * Byte.SIZE;
            decoded |= (bytes[offset + 4] & 0xFFL) << endianness[4] * Byte.SIZE;
            decoded |= (bytes[offset + 5] & 0xFFL) << endianness[5] * Byte.SIZE;
            decoded |= (bytes[offset + 6] & 0xFFL) << endianness[6] * Byte.SIZE;
            decoded |= (bytes[offset + 7] & 0xFFL) << endianness[7] * Byte.SIZE;

            return decoded;
        };
    }
}
