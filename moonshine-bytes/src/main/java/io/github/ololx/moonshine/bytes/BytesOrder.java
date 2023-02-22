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

import jdk.internal.misc.Unsafe;

import java.util.Objects;

/**
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public class BytesOrder {

    public static BytesOrder BIG_ENDIAN = new BytesOrder("Big-Endian");

    public static BytesOrder LITTLE_ENDIAN = new BytesOrder("Little-Endian");

    public static BytesOrder DEFAULT = BIG_ENDIAN;

    public static BytesOrder SYSTEM_DEFAULT = Unsafe.getUnsafe().isBigEndian()
            ? BIG_ENDIAN
            : LITTLE_ENDIAN;

    private final String name;

    public BytesOrder(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public String getName() {
        return this.name;
    }
}
