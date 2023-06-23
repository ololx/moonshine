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
package io.github.ololx.moonshine.bytes.coding;

/**
 * project moonshine
 * created 22.06.2023 16:28
 *
 * @author Alexander A. Kropotin
 */
public final class Bytes {

    private Bytes() {}

    public static byte[] concat(final byte[]... encodedValues) {
        if (encodedValues == null || encodedValues.length == 0) {
            return new byte[0];
        }

        int totalLength = 0;
        for (byte[] encoded : encodedValues) {
            totalLength += encoded.length;
        }

        byte[] mergedEncoded = new byte[totalLength];

        int destPos = 0;
        for (byte[] encoded : encodedValues) {
            int arrayLength = encoded.length;
            System.arraycopy(encoded, 0, mergedEncoded, destPos, arrayLength);
            destPos += arrayLength;
        }

        return mergedEncoded;
    }

    public static byte[] reorder(final byte[] bytes, ByteIndexOperator from, ByteIndexOperator to) {
        return reorder(bytes, 0, from, to);
    }

    public static byte[] reorder(final byte[] bytes, final int offset, final ByteIndexOperator from, final ByteIndexOperator to) {
        if (bytes == null || bytes.length == 0) {
            return new byte[0];
        }

        byte[] reordered = new byte[bytes.length];
        for (int byteIndex = offset; byteIndex < reordered.length; byteIndex++) {
            reordered[from.apply(byteIndex)] = bytes[to.apply(byteIndex)];
        }

        return reordered;
    }
}
