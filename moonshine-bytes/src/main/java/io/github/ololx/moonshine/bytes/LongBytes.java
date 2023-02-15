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
 * created 10.02.2023 15:45
 *
 * @author Alexander A. Kropotin
 */
public final class LongBytes implements Bytes {

    private LongBytes() {}

    static byte[] encodeBigEndian(long value) {
        return new byte[] {
                (byte)(value >> 56),
                (byte)(value >> 48),
                (byte)(value >> 40),
                (byte)(value >> 32),
                (byte)(value >> 24),
                (byte)(value >> 16),
                (byte)(value >> 8),
                (byte)(value)
        };
    }

    static byte[] encodeLittleEndian(long value) {
        return new byte[] {
                (byte)(value),
                (byte)(value >> 8),
                (byte)(value >> 16),
                (byte)(value >> 24),
                (byte)(value >> 32),
                (byte)(value >> 40),
                (byte)(value >> 48),
                (byte)(value >> 56)
        };
    }

    static long decodeBigEndian(byte[] bytes) {
        return (bytes[0] & 0xFFL) << 56
                | (bytes[1] & 0xFFL) << 48
                | (bytes[2] & 0xFFL) << 40
                | (bytes[3] & 0xFFL) << 32
                | (bytes[4] & 0xFFL) << 24
                | (bytes[5] & 0xFFL) << 16
                | (bytes[6] & 0xFFL) << 8
                | (bytes[7] & 0xFFL);
    }

    static long decodeLittleEndian(byte[] bytes) {
        return (bytes[0] & 0xFFL)
                | (bytes[1] & 0xFFL) << 8
                | (bytes[2] & 0xFFL) << 16
                | (bytes[3] & 0xFFL) << 24
                | (bytes[4] & 0xFFL) << 32
                | (bytes[5] & 0xFFL) << 40
                | (bytes[6] & 0xFFL) << 48
                | (bytes[7] & 0xFFL) << 54;
    }
}
