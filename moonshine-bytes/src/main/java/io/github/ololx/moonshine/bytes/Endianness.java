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
import java.util.function.IntUnaryOperator;

/**
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public final class Endianness {

    private static final UnsafeWrapper unsafe = UnsafeWrapper.getInstance();

    public static final Endianness BIG_ENDIAN = new Endianness("Big-endian", "BE", (seed, i) -> --seed - i);

    public static final Endianness LITTLE_ENDIAN = new Endianness("Little-endian", "LE", (seed, i) -> i);

    public static final Endianness PDP_ENDIAN = new Endianness("PDP-endian", "PDP-11", (seed, i) -> --seed - i);

    public static final Endianness SYSTEM_DEFAULT = unsafe.isBigEndian() ? BIG_ENDIAN : LITTLE_ENDIAN;

    public static final Endianness DEFAULT = BIG_ENDIAN;

    private final String name;

    private final String shortName;

    private final BytesOrderOperator bytesOrderOperator;

    public Endianness(String name, String shortName, BytesOrderOperator bytesOrderOperator) {
        this.name = Objects.requireNonNull(name);
        this.shortName = Objects.requireNonNull(shortName);
        this.bytesOrderOperator = Objects.requireNonNull(bytesOrderOperator);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public BytesOrderOperator getBytesOrderOperator() {
        return this.bytesOrderOperator;
    }
}
