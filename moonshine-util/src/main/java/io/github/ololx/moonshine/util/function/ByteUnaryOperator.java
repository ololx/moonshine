/**
 * Copyright 2023 the project moonshine authors
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

package io.github.ololx.moonshine.util.function;

import java.util.Objects;

/**
 * A specialization of the {@link java.util.function.UnaryOperator} interface for byte values.
 * It represents an operation on a single byte-valued operand that produces a byte-valued result.
 *
 * @see java.util.function.UnaryOperator
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 01.09.2023 10:34
 */
@FunctionalInterface
public interface ByteUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand to be operated on
     *
     * @return the result of applying this operator
     */
    byte applyAsByte(byte operand);

    /**
     * Returns a composed operator that first applies the {@code before} operator to its input,
     * and then applies this operator to the result. If evaluation of either operator throws an exception,
     * it is relayed to the caller of the composed operator.
     *
     * @implSpec The default implementation returns a composed operator that performs the composition
     *     in the order of {@code before} followed by this operator.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Define a byte unary operator that doubles the value
     *     ByteUnaryOperator doubleValue = value -> (byte) (value * 2);
     *
     *     // Define a byte unary operator that increments the value by 1
     *     ByteUnaryOperator incrementByOne = value -> (byte) (value + 1);
     *
     *     // Compose the two operators: first double, then increment
     *     ByteUnaryOperator composed = doubleValue.andThen(incrementByOne);
     *
     *     // Apply the composed operator to a byte value
     *     byte result = composed.applyAsByte((byte) 3);
     *
     *     // 'result' contains the value 7 (3 doubled and then incremented by 1).
     *     }</pre>
     *
     * @param before the operator to apply before this operator is applied
     *
     * @return a composed operator that first applies the {@code before} operator and then applies this operator
     */
    default ByteUnaryOperator compose(ByteUnaryOperator before) {
        Objects.requireNonNull(before);
        return v -> applyAsByte(before.applyAsByte(v));
    }

    /**
     * Returns a composed operator that first applies this operator to its input,
     * and then applies the {@code after} operator to the result. If evaluation of either operator throws an exception,
     * it is relayed to the caller of the composed operator.
     *
     * @implSpec The default implementation returns a composed operator that performs the composition
     *     in the order of this operator followed by {@code after}.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Define a byte unary operator that doubles the value
     *     ByteUnaryOperator doubleValue = value -> (byte) (value * 2);
     *
     *     // Define a byte unary operator that squares the value
     *     ByteUnaryOperator squareValue = value -> (byte) (value * value);
     *
     *     // Compose the two operators: first square, then double
     *     ByteUnaryOperator composed = doubleValue.compose(squareValue);
     *
     *     // Apply the composed operator to a byte value
     *     byte result = composed.applyAsByte((byte) 3);
     *
     *     // 'result' contains the value 18 (3 squared, then doubled).
     *     }</pre>
     *
     * @param after the operator to apply after this operator is applied
     *
     * @return a composed operator that first applies this operator and then applies the {@code after} operator
     */
    default ByteUnaryOperator andThen(ByteUnaryOperator after) {
        Objects.requireNonNull(after);
        return v -> after.applyAsByte(applyAsByte(v));
    }

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @implSpec The default implementation returns a unary operator that returns the same byte value
     *     it receives as an argument.
     *
     *     Example usage:
     *     <pre>{@code
     *     // Get the identity byte unary operator
     *     ByteUnaryOperator identityOperator = ByteUnaryOperator.identity();
     *
     *     // Apply the identity operator to a byte value
     *     byte result = identityOperator.applyAsByte((byte) 42);
     *
     *     // 'result' contains the value 42, which is the same as the input value.
     *     }</pre>
     *
     * @return a unary operator that always returns its input argument
     */
    static ByteUnaryOperator identity() {
        return t -> t;
    }
}
