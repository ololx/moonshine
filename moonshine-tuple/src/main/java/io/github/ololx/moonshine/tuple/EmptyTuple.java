/**
 * Copyright 2022 the project moonshine authors
 * and the original author or authors annotated by {@author}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.tuple;

/**
 * The tuple with only zero elements.
 *
 * The {@code EmptyTuple} class implements {@code Tuple0} and provides
 * all his behaviour.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 23.12.2022 10:34
 */
public class EmptyTuple
    extends AbstractTuple
    implements Tuple0 {

    /**
     * The power of this tuple.
     */
    private static final int SIZE = 0;

    /**
     * Creates a new {@code EmptyTuple} instance from an array of objects.
     * <p>
     * This method provides a convenient way to create a {@code EmptyTuple} from an array. It acts as a bridge
     * between array-based, collection-based, and tuple-based APIs, allowing easy conversion of an array to a tuple
     * </p>
     *
     * @param array the array from which the tuple is to be created
     *
     * @return a new {@code EmptyTuple} without any elements on an array
     *
     * @throws NullPointerException if the array is null
     * @implSpec This implementation always return EmptyTuple ignoring array elements
     */
    public static EmptyTuple from(Object[] array) {
        if (array == null) {
            throw new NullPointerException("The array must not be null");
        }

        return new EmptyTuple();
    }

    /**
     * Returns the element at the specified position in this tuple, or
     * {@code defaultValue} if the index is out of
     * range ({@code index < 0 || index >= size()})
     *
     * @param index        index of the element to return
     * @param defaultValue the default mapping of the key
     *
     * @return the {@code defaultValue}
     *
     * @throws ClassCastException if the tuple element is of an inappropriate
     *                            type for this {@code defaultValue}
     * @implSpec This implementation always returns the {@code defaultValue}
     */
    @Override
    public final <E> E getOrDefault(int index, E defaultValue) {
        return defaultValue;
    }

    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer.
     *
     * @return the number of elements in this tuple
     *
     * @implSpec This implementation always return 0 as a size {@code SIZE} of the tuple.
     */
    @Override
    public final int size() {
        return SIZE;
    }

    /**
     * Returns the element at the specified position in this tuple.
     *
     * @param index index of the element to return
     *
     * @throws IndexOutOfBoundsException for all method {@code get} invocations
     * @implSpec This implementation throws an instance of
     *     {@link IndexOutOfBoundsException} and performs no other action.
     */
    @Override
    public final <V> V get(int index) {
        IndexBounds.requireIndexWithinBounds(index, this.size());
        return null;
    }

    /**
     * Returns the string representation of the empty tuple (EmptyTuple) object.
     *
     * @return the string representation of the empty tuple
     *
     * @implSpec The implementation of this method for the EmptyTuple always returns the string "(∅)".
     */
    @Override
    public String toString() {
        return "(∅)";
    }

    /**
     * Returns a hash code value for the tuple.
     *
     * @return a hash code value for this tuple.
     *
     * @implSpec This implementation always return 31 (31 * 1)
     */
    @Override
    public int hashCode() {
        return 31;
    }

    /**
     * Indicates whether some other {@code Object} is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     *
     * @return {@code true} if this tuple is the same as the obj
     *     argument; {@code false} otherwise.
     *
     * @implSpec This implementation will return {@code true}, if one of the following
     *     conditions is true:
     *     <ol>
     *         <li>
     *             This tuple and {@code obj} argument refer to the same
     *             {@code Object} object
     *         </li>
     *         <li>
     *             This tuple and {@code obj} argument has the same type, i.e.
     *             booth are the realisation of the {@code Tuple0} tuple with
     *             size = 0 (T = B if |T| = |B| = 0)
     *         </li>
     *     </ol>
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        return obj instanceof Tuple0;
    }
}
