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

package io.github.ololx.moonshine.bytes.util;

import io.github.ololx.moonshine.bytes.Endianness;

/**
 * project moonshine
 * created 10.02.2023 13:45
 *
 * @author Alexander A. Kropotin
 */
public final class IntCoding {

    private IntCoding() {}

    public static byte[] encodeBigEndian(int value) {
        return encode(value, 0, Endianness.BIG_ENDIAN.getBytesOrderProvider().provide(3));
    }

    public static byte[] encodeLittleEndian(int value) {
        return encode(value, 0, Endianness.LITTLE_ENDIAN.getBytesOrderProvider().provide(3));
    }

    public static byte[] encodePDPEndian(int value) {
        return encode(value, 0, Endianness.PDP_ENDIAN.getBytesOrderProvider().provide(3));
    }

    public static byte[] encode(int value, int offset, int[] endianness) {
        byte[] encoded =  new byte[offset + 4];
        encoded[offset] = (byte) (value >> (endianness[0] << 3));
        encoded[offset + 1] = (byte) (value >> (endianness[1] << 3));
        encoded[offset + 2] = (byte) (value >> (endianness[2] << 3));
        encoded[offset + 3] = (byte) (value >> (endianness[3] << 3));

        return encoded;
    }

    public static int decodeBigEndian(byte[] bytes) {
        return decode(bytes, 0, Endianness.BIG_ENDIAN.getBytesOrderProvider().provide(3));
    }

    public static int decodeLittleEndian(byte[] bytes) {
        return decode(bytes, 0, Endianness.LITTLE_ENDIAN.getBytesOrderProvider().provide(3));
    }

    public static int decodePDPEndian(byte[] bytes) {
        return decode(bytes, 0, Endianness.PDP_ENDIAN.getBytesOrderProvider().provide(3));
    }

    public static int decode(byte[] bytes, int offset, int[] endianness) {
        int decoded = 0;
        decoded |= (bytes[offset] & 0xFF) << ( endianness[0] << 3);
        decoded |= (bytes[offset + 1] & 0xFF) << (endianness[1] << 3);
        decoded |= (bytes[offset + 2] & 0xFF) << (endianness[2] << 3);
        decoded |= (bytes[offset + 3] & 0xFF) << (endianness[3] << 3);

        return decoded;
    }
}
