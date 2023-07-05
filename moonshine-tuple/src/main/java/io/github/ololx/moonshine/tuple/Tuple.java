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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Spliterator.*;

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
     * @param <V> the type of this tuple element to return
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
     * @param <V> the type of this tuple element to return or
     * {@code defaultValue} to return
     * @param index index of the element to return
     * @param defaultValue the default value
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

    /**
     * Returns {@code true} if this list tuple the specified {@code value}
     * element.
     *
     * @implSpec
     * This implementation returns {@code true} if and only if this tuple
     * contains at least one element such that value ∈ (a0, a1, ..., an) or
     * in another words {@code Objects.equals(tupleElement, value)}.
     *
     * @param <V> the type of element whose presence in this tuple is to
     *           be tested
     * @param value element whose presence in this tuple is to be tested
     * @return {@code true} if this tuple contains the specified element
     */
    default <V> boolean contains(final V value) {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .anyMatch(tupleValue -> Objects.equals(tupleValue, value));
    }

    /**
     * Returns the index of the first occurrence of the specified {@code value}
     * element in this tuple, or -1 if this tuple does not contain the element.
     *
     * @implSpec
     * This implementation returns the lowest index {@code i} such that
     * value = am | am ∈ (a0, a1, ..., an) or in another words
     * {@code Objects.equals(o, get(i))}; or -1 if there is no such index.
     *
     * @param <V> the type of element whose index in this tuple is requested
     * @param value element whose index in this tuple is requested
     * @return the lowest index {@code i} or -1 if there is no such index
     */
    default <V> int indexOf(final V value) {
        return IntStream.range(0, this.size())
                .filter(index -> Objects.equals(this.get(index), value))
                .findFirst()
                .orElse(-1);
    }

    /**
     * Returns the index of the last occurrence of the specified {@code value}
     * element in this tuple, or -1 if this tuple does not contain the element.
     *
     * @implSpec
     * This implementation returns the highest index {@code i} such that
     * value = am | am ∈ (a0, a1, ..., an) or in another words
     * {@code Objects.equals(o, get(i))}; or -1 if there is no such index.
     *
     * @param <V> the type of element whose index in this tuple is requested
     * @param value element whose index in this tuple is requested
     * @return the highest index {@code i} or -1 if there is no such index
     */
    default <V> int lastIndexOf(final V value) {
        return IntStream.iterate(this.size() - 1, index -> index - 1)
                .limit(this.size())
                .filter(index -> Objects.equals(this.get(index), value))
                .findFirst()
                .orElse(-1);
    }

    /**
     * Returns an {@code Object[]} array containing all the elements in this
     * tuple in proper sequence (from first to last element).
     *
     * @implSpec
     * The returned {@code Object[]} array will be "safe" in that no references
     * to it are maintained by this tuple.  (In other words, this method must
     * allocate a new {@code Object[]} array). The caller is thus free to
     * modify the returned {@code Object[]} array.
     *
     * <br/>
     * This method acts as bridge between array-based, collection-based
     * and tuple-based APIs.
     *
     * @return an {@code Object[]} array containing all the elements in this
     * tuple in proper sequence
     */
    default Object[] toArray() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .toArray();
    }

    /**
     * Returns a {@code List<Object>} collection containing all the elements in
     * this tuple in proper sequence (from first to last element).
     *
     * @implSpec
     * The returned {@code List<Object>} collection will be "safe" in that no
     * references to it are maintained by this tuple.  (In other words, this
     * method must allocate a new {@code List<Object>} collection). The caller
     * is thus free to modify the returned {@code List<Object>} collection.
     *
     * <br/>
     * This method acts as bridge between array-based, collection-based
     * and tuple-based APIs.
     *
     * @return a {@code List<Object>} collection containing all the elements in
     * this tuple in proper sequence
     */
    default List<Object> toList() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    /**
     * Returns a {@code Set<Object>} collection containing all the elements in
     * this tuple in proper sequence (from first to last element).
     *
     * @implSpec
     * The returned {@code Set<Object>} collection will be "safe" in that no
     * references to it are maintained by this tuple.  (In other words, this
     * method must allocate a new {@code Set<Object>} collection). The caller
     * is thus free to modify the returned {@code Set<Object>} collection.
     *
     * <br/>
     * This method acts as bridge between array-based, collection-based
     * and tuple-based APIs.
     *
     * @return a {@code Set<Object>} collection containing all the elements in
     * this tuple in proper sequence
     */
    default Set<Object> toSet() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toSet());
    }

    /**
     * This method is deprecated and will be removed in a future releases;
     * use {@code stream()} instead.
     *
     * Returns a {@code Stream<Object>} stream containing all the elements in
     * this tuple in proper sequence (from first to last element).
     *
     * @implSpec
     * The returned {@code Stream<Object>} stream will be "safe" in that no
     * references to it are maintained by this tuple.  (In other words, this
     * method must allocate a new {@code Stream<Object>} stream). The caller
     * is thus free to modify the returned {@code Stream<Object>} stream.
     *
     * <br/>
     * This method acts as bridge between array-based, collection-based
     * and tuple-based APIs.
     *
     * @return a {@code Stream<Object>} stream containing all the elements in
     * this tuple in proper sequence
     */
    @Deprecated
    default Stream<Object> toStream() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get);
    }

    /**
     * Returns a {@code Stream<Object>} stream containing all the elements in
     * this tuple in proper sequence (from first to last element).
     *
     * @implSpec
     * The returned {@code Stream<Object>} stream will be "safe" in that no
     * references to it are maintained by this tuple.  (In other words, this
     * method must allocate a new {@code Stream<Object>} stream). The caller
     * is thus free to modify the returned {@code Stream<Object>} stream.
     *
     * <br/>
     * This method acts as bridge between array-based, collection-based
     * and tuple-based APIs.
     *
     * @return a {@code Stream<Object>} stream containing all the elements in
     * this tuple in proper sequence
     */
    default Stream<Object> stream() {
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
     * @implNote
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
     * Creates a {@link Spliterator} over the elements in this tuple.
     *
     * @implSpec
     * This method will return the {@code Spliterator} which reports
     * {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED},
     * {@link Spliterator#IMMUTABLE} and {@link Spliterator#ORDERED}.
     *
     * @implNote
     * Overriding implementations should document the reporting of additional
     * characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this tuple
     */
    @Override
    default Spliterator<Object> spliterator() {
        return Spliterators.spliterator(
                iterator(),
                this.size(),
                SIZED | IMMUTABLE | ORDERED
        );
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
