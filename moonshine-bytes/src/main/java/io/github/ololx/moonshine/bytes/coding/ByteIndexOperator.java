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

package io.github.ololx.moonshine.bytes.coding;

/**
 * A functional interface for applying a mapping function to a byte index.
 *
 * @author Alexander A. Kropotin
 * @apiNote This interface is designed to be used with indexed byte arrays, where each
 *     element of the array is a single byte and the index represents the position
 *     of that byte in the array.
 * @implSpec The default implementation of {@link #identity()} returns a lambda that
 *     simply returns its input, effectively mapping each index to itself.
 *
 *     <p><strong>Example usage:</strong></p>
 *     <pre>{@code
 *     // Create a ByteIndexOperator that returns the negation of its input
 *     ByteIndexOperator negation = i -> -i;
 *
 *     // Apply the negation operator to an array of bytes
 *     byte[] byteArray = { 1, 2, 3 };
 *     int[] resultArray = new int[byteArray.length];
 *     for (int i = 0; i < byteArray.length; i++) {
 *         resultArray[i] = negation.apply(i);
 *     }
 *
 *     // Verify that the result is [0, -1, -2]
 *     System.out.println(Arrays.toString(resultArray));
 *     }</pre>
 *     <pre>{@code
 *     // Create a ByteIndexOperator that returns the sum of its input and a constant value
 *     int constant = 42;
 *     ByteIndexOperator addConstant = i -> i + constant;
 *
 *     // Apply the addConstant operator to an array of bytes
 *     byte[] byteArray = { 1, 2, 3 };
 *     int[] resultArray = IntStream.range(0, byteArray.length)
 *             .map(i -> addConstant.apply(i))
 *             .toArray();
 *
 *     // Verify that the result is [42, 43, 44]
 *     System.out.println(Arrays.toString(resultArray));
 *     }</pre>
 *     <pre>{@code
 *     // Create a ByteIndexOperator that returns the length of a desc of a byte array
 *     byte[] byteArray = { 1, 2, 3, 4, 5 };
 *     ByteIndexOperator desc = i -> byteArray.length - i;
 *
 *     // Apply the desc operator to an array of bytes
 *     int[] resultArray = IntStream.range(0, byteArray.length)
 *             .map(i -> desc.apply(i))
 *             .toArray();
 *
 *     // Verify that the result is [5, 4, 3, 2, 1]
 *     System.out.println(Arrays.toString(resultArray));
 *     }</pre>
 *
 *     project moonshine
 *     created 02.03.2023 10:22
 */
public interface ByteIndexOperator {

    /**
     * Returns a byte index operator that applies the identity function.
     *
     * @return a byte index operator that applies the identity function
     *
     * @implSpec The default implementation returns a lambda that simply returns its
     *     input, effectively mapping each index to itself.
     */
    static ByteIndexOperator identity() {
        return index -> index;
    }

    /**
     * Applies a mapping function to the given byte index.
     *
     * @param index the byte index to apply the mapping function to
     *
     * @return the result of applying the mapping function to the byte index
     */
    int apply(int index);
}

