/**
 * Copyright 2024 the project moonshine authors
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

package io.github.moonshine.unsafe.adapter.functional;

/**
 * A functional interface for binary operations on two byte values.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 01.09.2023 10:34
 * @see java.util.function.BinaryOperator
 */
@FunctionalInterface
public interface ByteBinaryFunction {

    /**
     * Applies this accumulator to the given byte operands.
     *
     * @param left  the first byte operand
     * @param right the second byte operand
     *
     * @return the result of applying this operator to the operands
     */
    byte apply(final byte left, final byte right);

    /**
     * Returns a ByteBinaryAccumulator that performs a bitwise OR operation on its operands.
     *
     * @return a ByteBinaryAccumulator that performs a bitwise OR operation
     *
     * @implNote This method returns a lambda expression that performs a bitwise OR operation
     *     between the left and right operands and casts the result to byte.
     * @implSpec The returned ByteBinaryAccumulator is stateless and thread-safe when used as intended.
     *     Implementations may choose to cache instances for performance.
     * @see ByteBinaryFunction
     */
    public static ByteBinaryFunction bitwiseOr() {
        return (left, right) -> (byte) (left | right);
    }

    /**
     * Returns a ByteBinaryAccumulator that performs a bitwise AND operation on its operands.
     *
     * @return a ByteBinaryAccumulator that performs a bitwise AND operation
     *
     * @implNote This method returns a lambda expression that performs a bitwise AND operation
     *     with the left and right operands and casts the result to byte.
     * @implSpec The returned ByteBinaryAccumulator is stateless and thread-safe when used as intended.
     *     Implementations may choose to cache instances for performance.
     * @see ByteBinaryFunction
     */
    public static ByteBinaryFunction bitwiseAnd() {
        return (left, right) -> (byte) (left & right);
    }

    /**
     * Returns a ByteBinaryAccumulator that performs a bitwise XOR operation on its operands.
     *
     * @return a ByteBinaryAccumulator that performs a bitwise XOR operation
     *
     * @implNote This method returns a lambda expression that performs a bitwise XOR operation
     *     with the left and right operands and casts the result to byte.
     * @implSpec The returned ByteBinaryAccumulator is stateless and thread-safe when used as intended.
     *     Implementations may choose to cache instances for performance.
     * @see ByteBinaryFunction
     */
    public static ByteBinaryFunction bitwiseXor() {
        return (left, right) -> (byte) (left ^ right);
    }
}
