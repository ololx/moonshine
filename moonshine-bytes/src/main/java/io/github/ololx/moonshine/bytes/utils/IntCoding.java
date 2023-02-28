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
 * created 10.02.2023 13:45
 *
 * @author Alexander A. Kropotin
 */
public final class IntCoding {

    private IntCoding() {}

    public static byte[] encodeBigEndian(int value) {
        return new byte[] {
                (byte)(value >> 24),
                (byte)(value >> 16),
                (byte)(value >> 8),
                (byte)(value)
        };
    }

    public static byte[] encodeLittleEndian(int value) {
        return new byte[] {
                (byte)(value),
                (byte)(value >> 8),
                (byte)(value >> 16),
                (byte)(value >> 24)
        };
    }

    public static int decodeBigEndian(byte[] bytes) {
        return decode(bytes, 0, num -> 24 - (num << 3));
    }

    public static int decodeLittleEndian(byte[] bytes) {
        return decode(bytes, 0, num -> num << 3);
    }

    public static int decode(byte[] bytes, int offset, IntUnaryOperator endianness) {
        return (bytes[offset] & 0xFF) << endianness.applyAsInt(offset)
                | (bytes[offset + 1] & 0xFF) << endianness.applyAsInt(offset + 1)
                | (bytes[offset + 2] & 0xFF) << endianness.applyAsInt(offset + 2)
                | (bytes[offset + 3] & 0xFF) << endianness.applyAsInt(offset + 3);
    }
}
