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

/**
 * A functional interface for binary operations on two byte values.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 01.09.2023 10:34
 * @see java.util.function.BinaryOperator
 */
@FunctionalInterface
public interface ByteBinaryOperator {

    /**
     * Applies this operator to the given byte operands.
     *
     * @param left  the first byte operand
     * @param right the second byte operand
     *
     * @return the result of applying this operator to the operands
     *
     *     Example usage:
     *     <pre>{@code
     *         // Define a ByteBinaryOperator that adds two byte values
     *         ByteBinaryOperator addition = (left, right) -> (byte) (left + right);
     *
     *         // Apply the addition operation to two byte values
     *         byte result = addition.applyAsByte((byte) 5, (byte) 3);
     *
     *         // 'result' contains the sum of the two byte values (8).
     *         }</pre>
     */
    byte applyAsByte(byte left, byte right);
}
