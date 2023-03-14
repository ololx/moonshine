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

import io.github.ololx.moonshine.bytes.coding.decoders.ValueBytesDecoder;
import io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder;
import io.github.ololx.moonshine.bytes.coding.IntCoding;

/**
 * project moonshine
 * created 16.02.2023 11:57
 *
 * @author Alexander A. Kropotin
 */
public class IntBytes extends AbstractSingleValueBytes<Integer> {

    static final ValueBytesEncoder<Integer> ENCODER = IntCoding::encode;

    static final ValueBytesDecoder<Integer> DECODER = IntCoding::decode;

    static final int MSB = Integer.BYTES - 1;

    private IntBytes(int value) {
        super(ENCODER, DECODER, MSB, value);
    }

    public static IntBytes wrap(byte[] bytes, int[] endianness) {
        return new IntBytes(DECODER.decode(bytes, 0, endianness));
    }

    public static IntBytes wrap(int value) {
        return new IntBytes(value);
    }
}
