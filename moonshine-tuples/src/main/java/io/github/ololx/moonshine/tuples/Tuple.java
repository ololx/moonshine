/**
 * Copyright 2022 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ololx.moonshine.tuples;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * project moonshine
 * created 22.12.2022 11:41
 *
 * @author Alexander A. Kropotin
 */
public interface Tuple extends Iterable<Object> {

    /**
     * Returns the number of elements in this tuple. The size is a non-negative integer.
     * If this tuple contains more than {@code Integer.MAX_VALUE} elements,
     * returns {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this tuple
     */
    int size();

    /**
     * Returns the element at the specified position in this tuple.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this tuple
     * @throws IndexOutOfBoundsException if the index is out of
     * range ({@code index < 0 || index >= size})
     */
    Object get(int index);

    /**
     * Returns the element at the specified position in this tuple, or
     * {@code defaultValue} if the index is out of
     * range ({@code index < 0 || index >= size})
     *
     * @param index index of the element to return
     * @param defaultValue the default mapping of the key
     * @return the element at the specified position in this tuple
     * @throws ClassCastException if the tuple element is of an inappropriate type
     * for this {@code defaultValue}
     */
    default Object getOrDefault(int index, Object defaultValue) {
        if (index < 0 || index >= size()) {
            return defaultValue;
        }

        return get(index);
    }
}
