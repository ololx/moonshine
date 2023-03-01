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

import io.github.ololx.moonshine.bytes.utils.IntCoding;

import java.util.HashMap;
import java.util.Map;

import static io.github.ololx.moonshine.bytes.Endianness.BIG_ENDIAN;
import static io.github.ololx.moonshine.bytes.Endianness.LITTLE_ENDIAN;

/**
 * project moonshine
 * created 16.02.2023 11:57
 *
 * @author Alexander A. Kropotin
 */
public class IntBytes implements ValueBytesElement<Integer> {

    public static final ValueBytesEncoder<Integer> BEEncoder = (IntCoding::encodeBigEndian);

    public static final ValueBytesEncoder<Integer> LEEncoder = (IntCoding::encodeLittleEndian);

    private final Map<String, ValueBytesEncoder<Integer>> coding = new HashMap<>();

    {
        coding.put(BIG_ENDIAN.getName(), BEEncoder);
        coding.put(LITTLE_ENDIAN.getName(), LEEncoder);
    }

    private final int value;

    public IntBytes(int value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return this.value;
    }

    @Override
    public byte[] getBytes(Endianness endianness) {
        if (!this.coding.containsKey(endianness.getName())) {
            throw new RuntimeException("Unknown endianness type - " + endianness.getName());
        }

        return this.coding.get(endianness.getName()).encode(this.value);
    }
}
