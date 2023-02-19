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
 * created 15.02.2023 20:31
 *
 * @author Alexander A. Kropotin
 */
public final class DoubleCoding {

    private DoubleCoding() {}

    static byte[] encodeBigEndian(double value) {
        return LongCoding.encodeBigEndian(Double.doubleToLongBits(value));
    }

    static byte[] encodeLittleEndian(double value) {
        return LongCoding.encodeLittleEndian(Double.doubleToLongBits(value));
    }

    static double decodeBigEndian(byte[] bytes) {
        return Double.longBitsToDouble(LongCoding.decodeBigEndian(bytes));
    }

    static double decodeLittleEndian(byte[] bytes) {
        return Double.longBitsToDouble(LongCoding.decodeLittleEndian(bytes));
    }
}
