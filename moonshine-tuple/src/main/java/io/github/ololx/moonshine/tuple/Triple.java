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
 * The tuple with only three elements.
 *
 * The {@code Triple} class implements {@code Tuple3} and provides
 * all his behaviour.
 *
 * @param <A> the type of first element in this tuple
 * @param <B> the type of second element in this tuple
 * @param <C> the type of third element in this tuple
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 05.01.2023 20:41
 */
public class Triple<A, B, C> extends AbstractTuple implements Tuple3<A, B, C> {

    /**
     * The power of this tuple.
     */
    private static final Integer SIZE = 3;

    /**
     * First element in this tuple
     */
    private final A t0;

    /**
     * Second element in this tuple
     */
    private final B t1;

    /**
     * Third element in this tuple
     */
    private final C t2;

    /**
     * Create new tuple with specified elements values
     *
     * @param t0 the first element of this tuple
     * @param t1 the second element of this tuple
     * @param t2 the third element of this tuple
     */
    public Triple(A t0, B t1, C t2) {
        this.t0 = t0;
        this.t1 = t1;
        this.t2 = t2;
    }

    /**
     * Create new tuple with specified elements values
     *
     * @param <A> the type of first element in this tuple
     * @param <B> the type of second element in this tuple
     * @param <C> the type of third element in this tuple
     * @param t0  the first element of this tuple
     * @param t1  the second element of this tuple
     * @param t2  the third element of this tuple
     *
     * @return new tuple with specified elements values
     */
    public static <A, B, C> Triple<A, B, C> of(A t0, B t1, C t2) {
        return new Triple<>(t0, t1, t2);
    }

    /**
     * Returns the first element in this tuple.
     *
     * @return the first element in this tuple.
     */
    @Override
    public A getT0() {
        return this.t0;
    }    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer.
     *
     * @return the number of elements in this tuple
     *
     * @implSpec This implementation always return 3 as a size {@code SIZE} of the tuple.
     */
    @Override
    public final int size() {
        return SIZE;
    }

    /**
     * Returns the second element in this tuple.
     *
     * @return the second element in this tuple.
     */
    @Override
    public B getT1() {
        return this.t1;
    }    /**
     * Returns the element at the specified position in this tuple.
     *
     * @param index index of the element to return
     *
     * @return the element at the specified position in this tuple
     *
     * @throws IndexOutOfBoundsException if the index is out of
     *                                   range ({@code index < 0 || index >= size()})
     * @implSpec This implementation will return the first, second, third element
     *     if the index is in range [0, 1, 2]; otherwise throw an exception
     *     {@link IndexOutOfBoundsException}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public final <V> V get(int index) {
        switch (IndexBounds.requireIndexWithinBounds(index, this.size())) {
            case 0:
                return (V) this.t0;
            case 1:
                return (V) this.t1;
            default:
                return (V) this.t2;
        }
    }

    /**
     * Returns the third element in this tuple.
     *
     * @return the third element in this tuple.
     */
    @Override
    public C getT2() {
        return this.t2;
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
        hash = prime * ++index + hash + (this.t1 == null ? 0 : this.t1.hashCode());
        hash = prime * ++index + hash + (this.t2 == null ? 0 : this.t2.hashCode());

        return hash;
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
     *             booth are the realisation of the {@code Tuple3} tuple with
     *             size = 3. And all values of this tuple has the same order and
     *             equals to values of the {@code obj} argument
     *             (T = B if (t0, t1, t2) = (b0, b1, b2) and |T| = |B| = 3)
     *         </li>
     *     </ol>
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Tuple3)) {
            return false;
        }

        Tuple3<?, ?, ?> other = (Tuple3<?, ?, ?>) obj;

        final boolean isT0Equals = (this.t0 == null && other.getT0() == null) ||
            (this.t0 != null && this.t0.equals(other.getT0()));
        final boolean isT1Equals = (this.t1 == null && other.getT1() == null) ||
            (this.t1 != null && this.t1.equals(other.getT1()));
        final boolean isT2Equals = (this.t2 == null && other.getT2() == null) ||
            (this.t2 != null && this.t2.equals(other.getT2()));

        return isT0Equals && isT1Equals && isT2Equals;
    }




}
