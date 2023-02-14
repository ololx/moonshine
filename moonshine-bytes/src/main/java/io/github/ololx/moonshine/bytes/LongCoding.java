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
public final class LongCoding {

    private LongCoding() {}

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
        return (long)bytes[0] << 56
                | (long)bytes[1] << 48
                | (long)bytes[2] << 40
                | (long)bytes[3] << 32
                | (int)bytes[4] << 24
                | (int)bytes[5] << 16
                | (int)bytes[6] << 8
                | (int)bytes[7];
    }

    static long decodeLittleEndian(byte[] bytes) {
        return (int)bytes[0]
                | (int)bytes[1] << 8
                | (int)bytes[2] << 16
                | (int)bytes[3] << 24
                | (long)bytes[4] << 32
                | (long)bytes[5] << 40
                | (long)bytes[6] << 48
                | (long)bytes[7] << 56;
    }
}
