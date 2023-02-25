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

import static io.github.ololx.moonshine.bytes.Endianness.BIG_ENDIAN;

/**
 * project moonshine
 * created 16.02.2023 11:57
 *
 * @author Alexander A. Kropotin
 */
public class IntBytes implements ValueBytesCell<Integer> {

    private final int value;

    public IntBytes(int value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public byte[] getBytes() {
        return IntCoding.encodeBigEndian(this.value);
    }

    @Override
    public byte[] getBytes(Endianness order) {
        switch (order.getName()) {
            case "little-endian":
                return IntCoding.encodeLittleEndian(this.value);
            case "big-endian":
                return IntCoding.encodeBigEndian(this.value);
            default:
                throw new RuntimeException("Unknown order type - " + order.getName());
        }
    }
}
