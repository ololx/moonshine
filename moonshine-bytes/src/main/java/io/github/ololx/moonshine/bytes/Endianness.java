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

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public class Endianness {

    private static final UnsafeWrapper unsafe = UnsafeWrapper.getInstance();

    public static final Endianness BIG_ENDIAN = new Endianness(
            "Big-Endian",
            0x1,
            msb -> IntStream.iterate(msb, i -> i - 1)
                    .limit(msb + 1L)
                    .toArray()
    );

    public static final Endianness LITTLE_ENDIAN = new Endianness(
            "Little-Endian",
            0x2,
            msb -> IntStream.iterate(0, i -> i + 1)
                    .limit(msb + 1L)
                    .toArray()
    );

    public static final Endianness PDP_ENDIAN = new Endianness(
            "PDP-Endian",
            0x3,
            msb -> IntStream.iterate(0, i -> i + 1)
                    .limit(msb + 1L)
                    .map(i -> i % 2 == 0 ? msb - (i + 1) : msb - (i - 1))
                    .toArray()
    );

    public static final Endianness SYSTEM_DEFAULT = unsafe.isBigEndian()
            ? BIG_ENDIAN
            : LITTLE_ENDIAN;

    public static final Endianness DEFAULT = BIG_ENDIAN;

    private final int id;

    private final String name;

    private final EndiannessProvider bytesOrderProvider;

    public Endianness(String name, int id, EndiannessProvider bytesOrderProvider) {
        this.name = Objects.requireNonNull(name);
        this.id = id;
        this.bytesOrderProvider = Objects.requireNonNull(bytesOrderProvider);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public EndiannessProvider getBytesOrderProvider() {
        return this.bytesOrderProvider;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
