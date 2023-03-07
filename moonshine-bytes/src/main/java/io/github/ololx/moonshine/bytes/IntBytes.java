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

import io.github.ololx.moonshine.bytes.util.IntCoding;

/**
 * project moonshine
 * created 16.02.2023 11:57
 *
 * @author Alexander A. Kropotin
 */
public class IntBytes implements ValueBytesElement<Integer> {

    public static final BytesEncoder<Integer> ENCODER = IntCoding::encode;

    public static final BytesDecoder<Integer> DECODER = IntCoding::decode;

    private final BytesEncoder<Integer> encoder = ENCODER;

    private final int value;

    public IntBytes(int value) {
        this.value = value;
    }

    public static IntBytes fromBytes(byte[] bytes, int[] endianness) {
        return new IntBytes(DECODER.decode(bytes, 0, endianness));
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public byte[] getBytes(int[] endianness) {
        return this.encoder.encode(this.value, 0, endianness);
    }
}
