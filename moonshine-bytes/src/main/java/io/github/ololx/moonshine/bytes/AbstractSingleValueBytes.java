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

import io.github.ololx.moonshine.bytes.coding.IntCoding;
import io.github.ololx.moonshine.bytes.coding.decoders.ValueBytesDecoder;
import io.github.ololx.moonshine.bytes.coding.encoders.ValueBytesEncoder;

/**
 * project moonshine
 * created 14.03.2023 21:57
 *
 * @author Alexander A. Kropotin
 */
public class AbstractSingleValueBytes<T> implements SingleValueBytes<T> {

    protected final ValueBytesEncoder<T> encoder;

    protected final ValueBytesDecoder<T> decoder;

    private final int msb;

    private final T value;

    AbstractSingleValueBytes(ValueBytesEncoder<T> encoder, ValueBytesDecoder<T> decoder, int msb, T value) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.value = value;
        this.msb = msb;
    }

    @Override
    public final T getValue() {
        return this.value;
    }

    @Override
    public final byte[] getBytes(Endianness bytesOrder) {
        return this.encoder.encode(this.value, bytesOrder.getBytesOrderProvider().provide(this.msb));
    }
}
