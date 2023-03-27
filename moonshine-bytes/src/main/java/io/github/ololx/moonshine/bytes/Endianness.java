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
 * This class provides constants for different byte orders,
 * namely big-endian, little-endian, and PDP-endian. It also provides constants
 * for the system default byte order and the default byte order, which is big-endian.
 *
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public final class Endianness {

    /**
     * A constant representing the big-endian byte order, in which the most
     * significant byte of a multi-byte value is stored at the lowest memory
     * address.
     */
    public static final ByteOrder BIG_ENDIAN = new ByteOrder(
            "Big-Endian",
            0x1,
            size -> index -> {
                if (size == 1) {
                    return 0;
                }

                return size - 1 - index;
            }
    );

    /**
     * A constant representing the little-endian byte order, in which the least
     * significant byte of a multi-byte value is stored at the lowest memory
     * address.
     */
    public static final ByteOrder LITTLE_ENDIAN = new ByteOrder(
            "Little-Endian",
            0x2,
            size -> ByteIndexOperator.identity()
    );

    /**
     * A constant representing the PDP-endian byte order, which is a variation
     * of the big-endian order where adjacent bytes are swapped in pairs.
     */
    public static final ByteOrder PDP_ENDIAN = new ByteOrder(
            "PDP-Endian",
            0x3,
            size -> index -> {
                if (size == 1) {
                    return 0;
                }

                return index % 2 == 0
                        ? size - 1 - (index + 1)
                        : size - 1 - (index - 1);
            }
    );

    /**
     * A constant representing the default byte order for the Java platform,
     * which is big-endian.
     */
    public static final ByteOrder DEFAULT = BIG_ENDIAN;

    /**
     * A constant representing the native byte order of the system on which
     * the Java virtual machine is running. This is determined at runtime by
     * the {@code UnsafeHelper} class.
     */
    static final ByteOrder NATIVE = UnsafeHelper.getInstance().isBigEndian()
            ? BIG_ENDIAN
            : LITTLE_ENDIAN;

    /**
     * Override constructor by defaults (implicit public constructor).
     * Because utility class are not meant to be instantiated.
     */
    private Endianness() {}
}
