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

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A finite ordered list (otherwise <i>sequence</i>) of fixed length elements.
 * The user of this interface has precise control over where in the tuple
 * each element is placed. The user can access elements by their integer
 * index (position in the tuple), and search for elements in the tuple.
 *
 * Unlike collections, tuples typically are finite and has a fixed length.
 * Tuples typically allow pairs of elements {@code t0} and {@code b0}
 * such that {@code t0.equals(b0)}.
 *
 * More formally:
 * The general rule for the identity of two n-tuples is
 * (a0, a1, ..., an) = (b0, b1, ..., bn) <b>if and only if</b>
 * a0 = b0, a1 = b1, ..., an = bn
 *
 * <ul>
 *     <li>
 *         A tuple may contain multiple instances of the same element, so tuple
 *         (1, 2, 2, 3) != (1, 2, 3)
 *     </li>
 *     <li>
 *         Tuple elements are ordered: (1, 2, 3) != (3, 2, 1)
 *     </li>
 *     <li>
 *         A tuple has a finite number of elements, while a set or a multiset
 *         may have an infinite number of elements.
 *     </li>
 * </ul>
 *
 * The {@code Tuple} interface places additional stipulations, beyond those
 * specified in the {@code Iterable} interface, on the contracts of
 * the {@code iterator}, {@code get}, {@code getOrDefault}, {@code equals},
 * and {@code hashCode} methods.  Declarations for other inherited methods are
 * also included here for convenience.
 *
 * The {@code Tuple} interface provides two methods for positional (indexed)
 * access to tuple elements. Tuples (like arrays) are zero based.
 *
 * project moonshine
 * created 22.12.2022 11:41
 *
 * @author Alexander A. Kropotin
 */
public interface Tuple extends Iterable<Object> {

    /**
     * Returns the number of elements in this tuple.
     * The size is a non-negative integer. If this tuple contains more than
     * {@code Integer.MAX_VALUE} elements,
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
     * range ({@code index < 0 || index >= size()})
     */
    <V> V get(int index);

    /**
     * Returns the element at the specified position in this tuple, or
     * {@code defaultValue} if the index is out of
     * range ({@code index < 0 || index >= size()})
     *
     * @implSpec
     * This implementation returns {@code defaultValue} if the index is out of
     * range ({@code index < 0 || index >= size()}), otherwise returns
     * the element at the specified position
     *
     * @param index index of the element to return
     * @param defaultValue the default mapping of the key
     * @return the element at the specified position in this tuple
     * @throws ClassCastException if the tuple element is of an inappropriate
     * type for this {@code defaultValue}
     */
    @SuppressWarnings("unchecked")
    default <V> V getOrDefault(int index, V defaultValue) {
        if (!IndexBounds.checkIndex(index, this.size())) {
            return defaultValue;
        }

        return (V) get(index);
    }

    default Object[] toArray() {
        return this.toStream().toArray();
    }

    default List<Object> toList() {
        return this.toStream().collect(Collectors.toList());
    }

    default Set<Object> toSet() {
        return this.toStream().collect(Collectors.toSet());
    }

    default Stream<Object> toStream() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get);
    }

    /**
     * Returns an iterator over the elements in the tuple in proper sequence.
     *
     * @implSpec
     * This implementation returns a straightforward implementation of
     * the iterator interface, relying on the backing tuple's {@code size()},
     * {@code get(int)} methods.
     *
     * Note that the iterator returned by this method will throw an
     * {@link UnsupportedOperationException} in response to its
     * {@code remove} method.
     *
     * @return an iterator over the elements in the tuple in proper sequence
     */
    @Override
    default Iterator<Object> iterator() {
        return new BaseIterator(this);
    }

    /**
     * An iterator over a tuple elements.
     *
     * @implSpec
     * This is a straightforward implementation of the iterator interface,
     * relying on the backing tuple's {@code size()},{@code get(int)} methods.
     * This iterator will throw an {@link UnsupportedOperationException} in
     * response to its {@code remove} method.
     */
    class BaseIterator implements Iterator<Object> {

        /**
         * Index of element to be returned by subsequent call to next.
         */
        private int cursor;

        /**
         * Tuple witch will be iterated.
         */
        private final Tuple tuple;

        /**
         * Create new iterator for the tuple.
         */
        private BaseIterator(Tuple tuple) {
            this.tuple = tuple;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return this.cursor < this.tuple.size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Object next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("The iteration has no more elements");
            }

            return this.tuple.get((++this.cursor) - 1);
        }
    }
}
