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

import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;

/**
 * The {@code Bytes} class provides utility methods for working with byte arrays.
 *
 * @apiNote The byte arrays passed to the methods should not be modified during
 * the execution of the methods.
 *
 * @implSpec All methods in this class are thread-safe.
 *
 * project moonshine
 * created 22.06.2023 16:28
 *
 * @author Alexander A. Kropotin
 */
public final class Bytes {

    /**
     * Override constructor by defaults (implicit public constructor).
     * Because utility class are not meant to be instantiated.
     */
    private Bytes() {}

    /**
     * Concatenates multiple byte arrays into a single byte array.
     *
     * @param encodedValues the byte arrays to be concatenated
     * @return the concatenated byte array
     * @throws NullPointerException if the {@code encodedValues} array is {@code null}
     */
    public static byte[] concat(final byte[]... encodedValues) {
        if (encodedValues == null || encodedValues.length == 0) {
            return new byte[0];
        }

        int totalLength = 0;
        for (byte[] encoded : encodedValues) {
            totalLength += encoded.length;
        }

        byte[] concatenated = new byte[totalLength];

        int destPos = 0;
        for (byte[] encoded : encodedValues) {
            int arrayLength = encoded.length;
            System.arraycopy(encoded, 0, concatenated, destPos, arrayLength);
            destPos += arrayLength;
        }

        return concatenated;
    }

    /**
     * Reorders the elements in the given byte array based on the provided
     * index operators.
     * The reordering is performed on the entire byte array, starting from
     * index 0.
     *
     * @param bytes the byte array to be reordered
     * @param from the index operator for source positions
     * @param to the index operator for destination positions
     * @return the reordered byte array
     * @throws NullPointerException if the {@code bytes}, {@code from},
     * or {@code to} is {@code null}
     */
    public static byte[] reorder(final byte[] bytes,
                                 final ByteIndexOperator from,
                                 final ByteIndexOperator to) {
        return reorder(bytes, 0, from, to);
    }

    /**
     * Reorders the elements in the given byte array based on the provided
     * index operators,
     * starting from the specified offset.
     *
     * @param bytes the byte array to be reordered
     * @param offset the starting offset for reordering
     * @param from the index operator for source positions
     * @param to the index operator for destination positions
     * @return the reordered byte array
     * @throws NullPointerException if the {@code bytes}, {@code from},
     * or {@code to} is {@code null}
     */
    public static byte[] reorder(final byte[] bytes,
                                 final int offset,
                                 final ByteIndexOperator from,
                                 final ByteIndexOperator to) {
        return reorder(bytes, offset, bytes.length, from, to);
    }

    /**
     * Reorders the elements in the given byte array based on the provided
     * index operators,
     * starting from the specified offset and length.
     *
     * @param bytes the byte array to be reordered
     * @param offset the starting offset for reordering
     * @param length the length of the range to be reordered
     * @param from the index operator for source positions
     * @param to the index operator for destination positions
     * @return the reordered byte array
     * @throws NullPointerException if the {@code bytes}, {@code from},
     * or {@code to} is {@code null}
     * @throws IndexOutOfBoundsException if the {@code offset}
     * or {@code length} is invalid
     */
    public static byte[] reorder(final byte[] bytes,
                                 final int offset,
                                 final int length,
                                 final ByteIndexOperator from,
                                 final ByteIndexOperator to) {
        if (bytes == null || bytes.length == 0) {
            return new byte[0];
        }

        final byte[] reordered = new byte[bytes.length];
        for (int byteIndex = offset; byteIndex < reordered.length; byteIndex++) {
            reordered[from.apply(byteIndex)] = bytes[to.apply(byteIndex)];
        }

        return reordered;
    }
}
