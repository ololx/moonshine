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

    static ValueBytesEncoder<Integer> intEncoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + 4];
            encoded[offset] = (byte) (value >> (endianness[0] << 3));
            encoded[offset + 1] = (byte) (value >> (endianness[1] << 3));
            encoded[offset + 2] = (byte) (value >> (endianness[2] << 3));
            encoded[offset + 3] = (byte) (value >> (endianness[3] << 3));

            return encoded;
        };
    }

    static ValueBytesEncoder<Long> longEncoder() {
        return (value, offset, endianness) -> {
            byte[] encoded =  new byte[offset + 8];
            encoded[offset] = (byte) (value >> (endianness[0] << 3));
            encoded[offset + 1] = (byte) (value >> (endianness[1] << 3));
            encoded[offset + 2] = (byte) (value >> (endianness[2] << 3));
            encoded[offset + 3] = (byte) (value >> (endianness[3] << 3));
            encoded[offset + 4] = (byte) (value >> (endianness[4] << 3));
            encoded[offset + 5] = (byte) (value >> (endianness[5] << 3));
            encoded[offset + 6] = (byte) (value >> (endianness[6] << 3));
            encoded[offset + 7] = (byte) (value >> (endianness[7] << 3));

            return encoded;
        };
    }
}
