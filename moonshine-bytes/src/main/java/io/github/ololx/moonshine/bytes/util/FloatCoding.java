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

/**
 * project moonshine
 * created 15.02.2023 20:31
 *
 * @author Alexander A. Kropotin
 */
public final class FloatCoding {

    private FloatCoding() {}

    public static byte[] encodeBigEndian(float value) {
        return IntCoding.encodeBigEndian(Float.floatToIntBits(value));
    }

    public static byte[] encodeLittleEndian(float value) {
        return IntCoding.encodeLittleEndian(Float.floatToIntBits(value));
    }

    public static float decodeBigEndian(byte[] bytes) {
        return Float.intBitsToFloat(IntCoding.decodeBigEndian(bytes));
    }

    public static float decodeLittleEndian(byte[] bytes) {
        return Float.intBitsToFloat(IntCoding.decodeLittleEndian(bytes));
    }
}
