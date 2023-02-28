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

package io.github.ololx.moonshine.bytes.utils;

import java.util.function.IntUnaryOperator;

/**
 * project moonshine
 * created 10.02.2023 15:45
 *
 * @author Alexander A. Kropotin
 */
public final class LongCoding {

    private LongCoding() {}

    public static byte[] encodeBigEndian(long value) {
        return encode(value, 0, it -> 56 - (it << 3));
    }

    public static byte[] encodeLittleEndian(long value) {
        return encode(value, 0, it -> it << 3);
    }

    public static byte[] encode(long value, int offset, IntUnaryOperator endianness) {
        byte[] encoded =  new byte[offset + 8];
        encoded[offset] = (byte) (value >> endianness.applyAsInt(offset));
        encoded[offset + 1] = (byte) (value >> endianness.applyAsInt(offset + 1));
        encoded[offset + 2] = (byte) (value >> endianness.applyAsInt(offset + 2));
        encoded[offset + 3] = (byte) (value >> endianness.applyAsInt(offset + 3));
        encoded[offset + 4] = (byte) (value >> endianness.applyAsInt(offset + 4));
        encoded[offset + 5] = (byte) (value >> endianness.applyAsInt(offset + 5));
        encoded[offset + 6] = (byte) (value >> endianness.applyAsInt(offset + 6));
        encoded[offset + 7] = (byte) (value >> endianness.applyAsInt(offset + 7));

        return encoded;
    }

    public static long decodeBigEndian(byte[] bytes) {
        return decode(bytes, 0, it -> 56 - (it << 3));
    }

    public static long decodeLittleEndian(byte[] bytes) {
        return decode(bytes, 0, it -> it << 3);
    }

    public static long decode(byte[] bytes, int offset, IntUnaryOperator endianness) {
        return (bytes[offset] & 0xFFL) << endianness.applyAsInt(offset)
                | (bytes[offset + 1] & 0xFFL) << endianness.applyAsInt(offset + 1)
                | (bytes[offset + 2] & 0xFFL) << endianness.applyAsInt(offset + 2)
                | (bytes[offset + 3] & 0xFFL) << endianness.applyAsInt(offset + 3)
                | (bytes[offset + 4] & 0xFFL) << endianness.applyAsInt(offset + 4)
                | (bytes[offset + 5] & 0xFFL) << endianness.applyAsInt(offset + 5)
                | (bytes[offset + 6] & 0xFFL) << endianness.applyAsInt(offset + 6)
                | (bytes[offset + 7] & 0xFFL) << endianness.applyAsInt(offset + 7);
    }
}
