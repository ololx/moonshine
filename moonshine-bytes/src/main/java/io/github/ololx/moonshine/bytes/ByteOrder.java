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
 * A class that represents a byte order, with a name, unique ID, and a provider
 * for byte order array.
 *
 * @implNote <p>This class is immutable and thread-safe.</p>
 * <p>This class provides a default implementation of byte order that can be
 * used as a reference.</p>
 * <p>The byte order provider can be implemented by the user and provided to
 * the constructor.</p>
 *
 * project moonshine
 * created 22.02.2023 14:13
 *
 * @author Alexander A. Kropotin
 */
public class ByteOrder {

    /**
     * Counter for generating unique IDs.
     */
    private static final AtomicInteger instancesCount = new AtomicInteger(1);

    /**
     * The ID of the byte order.
     */
    private final int id;

    /**
     * The name of the byte order.
     */
    private final String name;

    /**
     * The provider for the byte order array.
     */
    private final ByteOrderProvider byteOrderProvider;

    /**
     * Constructs a ByteOrder object with the given name and provider.
     *
     * @param name              The name of the byte order.
     * @param byteOrderProvider The provider for the byte order array.
     * @throws NullPointerException if the name or provider is null.
     */
    public ByteOrder(String name, ByteOrderProvider byteOrderProvider) {
        this.name = Objects.requireNonNull(name);
        this.id = instancesCount.incrementAndGet();
        this.byteOrderProvider = Objects.requireNonNull(byteOrderProvider);
    }

    /**
     * Returns the name of this byte order.
     *
     * @return The name of this byte order.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Returns the ID of this byte order.
     *
     * @return The ID of this byte order.
     */
    public final int getId() {
        return this.id;
    }

    /**
     * Returns the byte order array for the given most significant byte.
     *
     * @param mostSignificantByte The most significant byte.
     * @return The byte order array.
     */
    public final int[] byteOrder(int mostSignificantByte) {
        return this.byteOrderProvider.provide(mostSignificantByte);
    }

    /**
     * Returns the byte order provider for this byte order.
     *
     * @return The byte order provider for this byte order.
     */
    public final ByteOrderProvider getByteOrderProvider() {
        return this.byteOrderProvider;
    }

    /**
     * Returns a string representation of this byte order.
     *
     * @return A string representation of this byte order.
     */
    @Override
    public String toString() {
        return String.format("%s[%s]", this.name, this.id);
    }
}
