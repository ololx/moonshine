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
 * created 16.02.2023 11:57
 *
 * @author Alexander A. Kropotin
 */
public class IntByteArray implements ValueBytesArray<Integer> {

    private int[] value;

    public IntByteArray(int... value) {
        this.value = value;
    }

    @Override
    public int size() {
        return Integer.BYTES;
    }

    @Override
    public Integer get(int index) {
        return this.value[index];
    }

    @Override
    public void put(Integer value) {
        this.value[0] = value;
    }

    @Override
    public byte[] getBytes() {
        if (Endianness.SYSTEM_DEFAULT.equals(Endianness.LITTLE_ENDIAN)) {
            return IntCoding.encodeBigEndian(this.value[0]);
        }

        return new byte[0];
    }

    @Override
    public byte[] getBytes(Endianness order) {
        return new byte[0];
    }
}
