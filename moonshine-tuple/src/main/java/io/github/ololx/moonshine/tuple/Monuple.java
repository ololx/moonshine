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
 * The tuple with only one element.
 *
 * The {@code Monuple} class implements {@code Tuple1} and provides
 * all his behaviour.
 *
 * @param <A> the type of element in this tuple
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 28.12.2022 19:51
 */
public class Monuple<A> extends AbstractTuple implements Tuple1<A> {

    /**
     * The power of this tuple.
     */
    private static final int SIZE = 1;

    /**
     * First element in this tuple
     */
    private final A t0;

    /**
     * Create new tuple with specified elements values
     *
     * @param t0 the first element of this tuple
     */
    public Monuple(A t0) {
        this.t0 = t0;
    }

    /**
     * Creates a new {@code Monuple} instance from an array of objects.
     * <p>
     * This method provides a convenient way to create a {@code Monuple} from an array. It acts as a bridge
     * between array-based, collection-based, and tuple-based APIs, allowing easy conversion of an array to a tuple
     * </p>
     *
     * @param <A>   the type of first element in this tuple
     * @param array the array from which the tuple is to be created
     *
     * @return a new {@code Monuple} containing the first element of the given array
     *
     * @throws NullPointerException     if the array is null
     * @throws IllegalArgumentException if the array size is not equal to or greater than the required number of
     *                                  elements for the tuple
     */
    @SuppressWarnings("unchecked")
    public static <A> Monuple<A> from(Object[] array) {
        if (array == null) {
            throw new NullPointerException("The array must not be null");
        } else if (array.length < SIZE) {
            throw new IllegalArgumentException("The array size must be equal to or greater than tuple size");
        }

        return (Monuple<A>) new Monuple<>(array[0]);
    }


    /**
     * Create new tuple with specified elements values
     *
     * @param <A> the type of element in this tuple
     * @param t0  the first element of this tuple
     *
     * @return new tuple with specified elements values
     */
    public static <A> Monuple<A> of(A t0) {
        return new Monuple<>(t0);
    }

    /**
     * Returns the first element in this tuple.
     *
     * @return the first element in this tuple.
     */
    @Override
    public A getT0() {
        return this.t0;
    }

    /**
     * Returns a hash code value for the tuple.
     *
     * @return a hash code value for this tuple.
     *
     * @implSpec This implementation generates a hash code given the order of
     *     the elements and their hash code.
     */
    @Override
    public int hashCode() {
        final int prime = 31;

        int hash = 0;
        int index = 0;

        hash = prime * ++index + hash + (this.t0 == null ? 0 : this.t0.hashCode());

        return hash;
    }    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer.
     *
     * @return the number of elements in this tuple
     *
     * @implSpec This implementation always return 1 as a size {@code SIZE} of the tuple.
     */
    @Override
    public final int size() {
        return SIZE;
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
     *             booth are the realisation of the {@code Tuple1} tuple with
     *             size = 1. And all values of this tuple has the same order and
     *             equals to values of the {@code obj} argument
     *             (T = B if (t0) = (b0) and |T| = |B| = 1)
     *         </li>
     *     </ol>
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Tuple1)) {
            return false;
        }

        Tuple1<?> other = (Tuple1<?>) obj;

        return (this.t0 == null && other.getT0() == null) || (this.t0 != null && this.t0.equals(other.getT0()));
    }

    /**
     * Returns the element at the specified position in this tuple.
     *
     * @param index index of the element to return
     *
     * @return the element at the specified position in this tuple
     *
     * @throws IndexOutOfBoundsException if the index is out of
     *                                   range ({@code index < 0 || index >= size()})
     * @implSpec This implementation will return the first element if the index is 1;
     *     otherwise throw an exception {@link IndexOutOfBoundsException}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final <V> V get(int index) {
        IndexBounds.requireIndexWithinBounds(index, this.size());

        return (V) this.t0;
    }


}
