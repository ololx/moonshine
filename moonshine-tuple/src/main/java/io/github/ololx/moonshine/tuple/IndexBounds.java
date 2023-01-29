/**
 * Copyright 2023 the project moonshine authors
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

package io.github.ololx.moonshine.tuple;

/**
 * Utility methods to check if index are correct for the specific range.
 *
 * project moonshine
 * created 23.01.2023 16:19
 * <p>
 * @author Alexander A. Kropotin
 */
final class IndexBounds {

    /**
     * Override constructor by defaults (implicit public constructor).
     * Because utility class are not meant to be instantiated.
     */
    private IndexBounds() {}

    /**
     * Checks if the {@code index} is within the bounds of the range from
     * {@code 0} (inclusive) to {@code toExclusive} (exclusive).
     *
     * <p>The {@code index} is defined to be out of bounds if any of the
     * following inequalities is true:
     * <ol>
     *     <li>{@code index < 0}</li>
     *     <li>{@code index >= toExclusive}</li>
     * </ol>
     *
     * @param index the index
     * @param toExclusive the upper-bound (exclusive) of the range
     * @return {@code true} if index is within bounds of the range,
     * or {@code false} otherwise
     */
    static boolean checkIndex(int index, int toExclusive) {
        return checkIndex(index, 0, toExclusive);
    }

    /**
     * Checks if the {@code index} is within the bounds of the range from
     * {@code fromInclusive} (inclusive) to {@code toExclusive} (exclusive).
     *
     * <p>The {@code index} is defined to be out of bounds if any of the
     * following inequalities is true:
     * <ol>
     *     <li>{@code index < fromInclusive}</li>
     *     <li>{@code index >= toExclusive}</li>
     * </ol>
     *
     * @param index the index
     * @param fromInclusive the lower-bound (inclusive) of the range
     * @param toExclusive the upper-bound (exclusive) of the range
     * @return {@code true} if index is within bounds of the range,
     * or {@code false} otherwise
     */
    static boolean checkIndex(int index, int fromInclusive, int toExclusive) {
        return index >= fromInclusive && index < toExclusive;
    }

    /**
     * Checks that the {@code index} is out of the bounds
     * {@link #requireIndexWithinBounds(int, int, int)} and throws
     * {@link IndexOutOfBoundsException} if it is.
     *
     * <p>This method is designed primarily for doing parameter validation
     * in methods and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Object get(int index) {
     *      int len = elements.length;
     *      return elements[IndexBounds.requireIndexWithinBounds(index, len)];
     * }
     * </pre></blockquote>
     *
     * @param index the index
     * @param toExclusive the upper-bound (exclusive) of the range
     * @return {@code index} if index is within bounds
     * @throws IndexOutOfBoundsException if {@code index} is out of
     * a bounds
     */
    static int requireIndexWithinBounds(int index, int toExclusive) {
        return requireIndexWithinBounds(index, 0, toExclusive);
    }

    /**
     * Checks that the {@code index} is out of the bounds
     * {@link #checkIndex(int, int, int)} and throws
     * {@link IndexOutOfBoundsException} if it is.
     *
     * <p>This method is designed primarily for doing parameter validation
     * in methods and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Object get(int index) {
     *      int len = elements.length;
     *      return elements[IndexBounds.requireIndexWithinBounds(index, 0, len)];
     * }
     * </pre></blockquote>
     *
     * @param index the index
     * @param fromInclusive the lower-bound (inclusive) of the range
     * @param toExclusive the upper-bound (exclusive) of the range
     * @return {@code index} if index is within specified bounds
     * @throws IndexOutOfBoundsException if {@code index} is out of
     * the specified bounds
     */
    static int requireIndexWithinBounds(int index, int fromInclusive, int toExclusive) {
        if (!checkIndex(index, fromInclusive, toExclusive)) {
            throw new IndexOutOfBoundsException(String.format(
                    "The index %s out of a bounds [%s, %s)", index, fromInclusive, toExclusive
            ));
        }

        return index;
    }
}
