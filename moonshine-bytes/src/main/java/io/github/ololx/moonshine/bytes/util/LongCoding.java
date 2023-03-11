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
import io.github.ololx.moonshine.bytes.ValueBytesDecoder;
import io.github.ololx.moonshine.bytes.ValueBytesEncoder;

/**
 * project moonshine
 * created 10.02.2023 15:45
 *
 * @author Alexander A. Kropotin
 */
public final class LongCoding {

    private LongCoding() {}

    public static byte[] encodeBigEndian(long value) {
        return encode(value, 0, Endianness.BIG_ENDIAN.getBytesOrderProvider().provide(7));
    }

    public static byte[] encodeLittleEndian(long value) {
        return encode(value, 0, Endianness.LITTLE_ENDIAN.getBytesOrderProvider().provide(7));
    }

    public static byte[] encode(long value, int offset, int[] endianness) {
        return ValueBytesEncoder.bit64Encoder().encode(value, offset, endianness);
    }

    public static long decodeBigEndian(byte[] bytes) {
        return decode(bytes, 0, Endianness.BIG_ENDIAN.getBytesOrderProvider().provide(7));
    }

    public static long decodeLittleEndian(byte[] bytes) {
        return decode(bytes, 0, Endianness.LITTLE_ENDIAN.getBytesOrderProvider().provide(7));
    }

    public static long decode(byte[] bytes, int offset, int[] endianness) {
        return ValueBytesDecoder.bit64Decoder().decode(bytes, offset, endianness);
    }
}
