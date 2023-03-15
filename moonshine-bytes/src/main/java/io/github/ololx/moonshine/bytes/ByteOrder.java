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

/**
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public class ByteOrder {

    private static final AtomicInteger instancesCount = new AtomicInteger(1);

    private final int id;

    private final String name;

    private final ByteOrderProvider byteOrderProvider;

    public ByteOrder(String name, ByteOrderProvider byteOrderProvider) {
        this.name = Objects.requireNonNull(name);
        this.id = instancesCount.incrementAndGet();
        this.byteOrderProvider = Objects.requireNonNull(byteOrderProvider);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int[] byteOrder(int mostSignificantByte) {
        return this.byteOrderProvider.provide(mostSignificantByte);
    }

    public ByteOrderProvider getByteOrderProvider() {
        return this.byteOrderProvider;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", this.name, this.id);
    }
}
