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

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public final class Endianness {

    public static Endianness BIG_ENDIAN;

    public static Endianness LITTLE_ENDIAN;

    public static Endianness SYSTEM_DEFAULT;

    static {
        LITTLE_ENDIAN = new Endianness("little-endian");
        BIG_ENDIAN = new Endianness("big-endian");

        Unsafe unsafe = UnsafeSupplier.getInstance().get();
        long a = unsafe.allocateMemory(2);
        try {
            unsafe.putShort(a, (short) 0x01000010);
            byte b = unsafe.getByte(a);

            switch (b) {
                case 0x01:
                    SYSTEM_DEFAULT = BIG_ENDIAN;
                    break;
                case 0x10:
                    SYSTEM_DEFAULT = LITTLE_ENDIAN;
                    break;
                default:
                    assert false;
            }
        } finally {
            unsafe.freeMemory(a);
        }
    }

    private final String name;

    public Endianness(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }
}
