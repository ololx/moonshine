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
 * @param <T1> the type of element in this tuple
 *
 * project moonshine
 * created 28.12.2022 19:51
 *
 * @author Alexander A. Kropotin
 */
public class Monuple<T1> implements Tuple1<T1> {

    /**
     * The power of this tuple.
     */
    private static final int SIZE = 1;

    /**
     * First element in this tuple
     */
    private final T1 t1;

    /**
     * Create new tuple with specified elements values
     *
     * @param t1 the first element of this tuple
     */
    public Monuple(T1 t1) {
        this.t1 = t1;
    }

    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer.
     *
     * <b>implSpec:</b>
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
     * <b>implSpec:</b>
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
    public T1 getT1() {
        return this.t1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Monuple)) {
            return false;
        }

        Monuple<?> other = (Monuple<?>) obj;

        return (this.t1 == null && other.t1 == null)
                || (this.t1 != null && this.t1.equals(other.t1));
    }

    @Override
    public int hashCode() {
        final int prime = 31;

        int hash = 1;
        hash = prime * hash + (this.t1 == null ? 0 : this.t1.hashCode());

        return hash;
    }
}
