/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <br/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.tuple;

import java.util.stream.IntStream;

/**
 * An interpreter for the string representation of tuples. This class provides
 * utility methods for creating print-style string representations of a specific
 * tuple.
 *
 * <p>These methods are designed primarily for the building string from tuple
 * in methods and constructors, as demonstrated below:
 * <blockquote><pre>
 * public void printTuple(Tuple tuple) {
 *      System.out.println(TupleString.format(tuple));
 * }
 * </pre></blockquote>
 *
 * project moonshine
 * created 29.01.2023 12:34
 * <br/>
 * @author Alexander A. Kropotin
 */
final class TupleString {

    /**
     * Override constructor by defaults (implicit public constructor).
     * Because utility class are not meant to be instantiated.
     */
    private TupleString() {}

    /**
     * Returns a formatted string using the tuple argument.
     *
     * @implSpec
     * The string consists of a list of the tuple's elements in their order,
     * enclosed in round brackets ({@code "()"}).  Adjacent elements are
     * separated by the characters {@code ", "} (comma and space).
     *
     * @param tuple the specified tuple
     * @return the formatted string of the specified tuple. Typically,
     * a formatted string contains a sequence of the elements of a tuple
     * in parentheses.
     * @see  AbstractTuple#toString()
     */
    static String format(Tuple tuple) {
        final int elementsCount = tuple.size();
        final int tupleStringLength = elementsCount == 0 ? 2 : elementsCount * 3;
        final StringBuilder tupleStringBuilder = new StringBuilder(tupleStringLength);

        tupleStringBuilder.append(String.valueOf(tuple.getOrDefault(0, "")));
        IntStream.range(1, elementsCount).forEach(index -> {
            tupleStringBuilder.append(", ");
            tupleStringBuilder.append(String.valueOf(tuple.getOrDefault(index, "")));
        });

        tupleStringBuilder.insert(0, "(");
        tupleStringBuilder.append(")");

        return tupleStringBuilder.toString();
    }
}
