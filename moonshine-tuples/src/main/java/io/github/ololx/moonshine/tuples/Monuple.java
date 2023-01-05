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

package io.github.ololx.moonshine.tuples;

/**
 * The tuple with only one element.
 *
 * The {@code Monuple} class implements {@code Tuple1} and provides
 * all his behaviour.
 *
 * @param <A> the type of element in this tuple
 *
 * project moonshine
 * created 28.12.2022 19:51
 *
 * @author Alexander A. Kropotin
 */
public class Monuple<A> implements Tuple1<A> {

    /**
     * The power of this tuple.
     */
    private static final int SIZE = 1;

    /**
     * First element in this tuple
     */
    private final A t1;

    /**
     * Create new tuple with specified elements values
     *
     * @param t1 the first element of this tuple
     */
    public Monuple(A t1) {
        this.t1 = t1;
    }

    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer.
     *
     * <b>implSpec</b>
     * This implementation always return 1 as a size {@code SIZE} of the tuple.
     *
     * @return the number of elements in this tuple
     */
    @Override
    public int size() {
        return SIZE;
    }

    /**
     * Returns the element at the specified position in this tuple.
     *
     * <b>implSpec</b>
     * This implementation will return the first element if the index is 1;
     * otherwise throw an exception {@link IndexOutOfBoundsException}.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this tuple
     * @throws IndexOutOfBoundsException if the index is out of
     * range ({@code index < 0 || index >= size()})
     */
    @Override
    public Object get(int index) {
        if (index < 0 || index >= SIZE) {
            throw new IndexOutOfBoundsException("There is no elements by index " + index);
        }

        return this.t1;
    }

    /**
     * Returns the first element in this tuple.
     *
     * @return  the first element in this tuple.
     */
    @Override
    public A getT1() {
        return this.t1;
    }

    /**
     * Indicates whether some other {@code Object} is "equal to" this one.
     *
     * <b>implSpec</b>
     * This implementation will return {@code true}, if one of the following
     * conditions is true:
     * <ol>
     *     <li>
     *         This tuple and {@code obj} argument refer to the same
     *         {@code Object} object
     *     </li>
     *     <li>
     *         This tuple and {@code obj} argument has the same type, i.e.
     *         booth are the realisation of the {@code Tuple1} tuple with
     *         size = 1. And all values of this tuple has the same order and
     *         equals to values of the {@code obj} argument
     *         (T = B if (t1) = (b1) and |T| = |B| = 1)
     *     </li>
     * </ol>
     *
     * @param   obj   the reference object with which to compare.
     * @return  {@code true} if this tuple is the same as the obj
     *          argument; {@code false} otherwise.
     * @see     #hashCode()
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

        return (this.t1 == null && other.getT1() == null)
                || (this.t1 != null && this.t1.equals(other.getT1()));
    }

    /**
     * Returns a hash code value for the tuple.
     *
     * <b>implSpec</b>
     * This implementation generates a hash code given the order of
     * the elements and their hash code.
     *
     * @return  a hash code value for this tuple.
     */
    @Override
    public int hashCode() {
        final int prime = 31;

        int index = 0, hash = 0;
        hash = prime * ++index + hash + (this.t1 == null ? 0 : this.t1.hashCode());

        return hash;
    }
}
