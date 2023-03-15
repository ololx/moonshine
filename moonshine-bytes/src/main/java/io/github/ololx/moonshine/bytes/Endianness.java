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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public final class Endianness {

    private static final UnsafeWrapper unsafe = UnsafeWrapper.getInstance();

    public static final ByteOrder BIG_ENDIAN = new ByteOrder(
            "Big-Endian",
            msb -> IntStream.iterate(msb, i -> i - 1)
                    .limit(msb + 1L)
                    .toArray()
    );

    public static final ByteOrder LITTLE_ENDIAN = new ByteOrder(
            "Little-Endian",
            msb -> IntStream.iterate(0, i -> i + 1)
                    .limit(msb + 1L)
                    .toArray()
    );

    public static final ByteOrder PDP_ENDIAN = new ByteOrder(
            "PDP-Endian",
            msb -> IntStream.iterate(0, i -> i + 1)
                    .limit(msb + 1L)
                    .map(i -> i % 2 == 0 ? msb - (i + 1) : msb - (i - 1))
                    .toArray()
    );

    public static final ByteOrder SYSTEM_DEFAULT = unsafe.isBigEndian()
            ? BIG_ENDIAN
            : LITTLE_ENDIAN;

    public static final ByteOrder DEFAULT = BIG_ENDIAN;
}
