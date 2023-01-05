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
 * The tuple with only two elements.
 *
 * The {@code Couple} class implements {@code Tuple2} and provides
 * all his behaviour.
 *
 * @param <A> the type of first element in this tuple
 * @param <B> the type of second element in this tuple
 *
 * project moonshine
 * created 28.12.2022 20:19
 *
 * @author Alexander A. Kropotin
 */
public class Couple<A, B> implements Tuple2<A, B> {

    /**
     * The power of this tuple.
     */
    private static final int SIZE = 2;

    /**
     * First element in this tuple
     */
    private final A t1;

    /**
     * Second element in this tuple
     */
    private final B t2;

    /**
     * Create new tuple with specified elements values
     *
     * @param t1 the first element of this tuple
     * @param t2 the second element of this tuple
     */
    public Couple(A t1, B t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer.
     *
     * <b>implSpec:</b>
     * This implementation always return 2 as a size {@code SIZE} of the tuple.
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
     * <b>implSpec:</b>
     * This implementation will return the first and second element
     * if the index is in range [0, 1]; otherwise throw an exception
     * {@link IndexOutOfBoundsException}.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this tuple
     * @throws IndexOutOfBoundsException if the index is out of
     * range ({@code index < 0 || index >= size()})
     */
    @Override
    public Object get(int index) {
        switch (index) {
            case 0:
                return this.t1;
            case 1:
                return this.t2;
            default:
                throw new IndexOutOfBoundsException("There is no elements by index " + index);
        }
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
     * Returns the second element in this tuple.
     *
     * @return the second element in this tuple.
     */
    @Override
    public B getT2() {
        return this.t2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Couple)) {
            return false;
        }

        Couple<?, ?> other = (Couple<?, ?>) obj;

        final boolean isT1Equals = (this.t1 == null && other.t1 == null)
                || (this.t1 != null && this.t1.equals(other.t1));
        final boolean isT2Equals = (this.t2 == null && other.t2 == null)
                || (this.t2 != null && this.t2.equals(other.t2));

        return isT1Equals && isT2Equals;
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int hash = 1;
        hash = prime * hash + (this.t1 == null ? 0 : this.t1.hashCode());
        hash = prime * hash + (this.t2 == null ? 0 : this.t2.hashCode());

        return hash;
    }
}
