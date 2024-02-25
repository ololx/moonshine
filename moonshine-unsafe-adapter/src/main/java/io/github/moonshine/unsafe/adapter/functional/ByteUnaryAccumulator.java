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
 * It represents an operation on a single byte-valued operand that produces a byte-valued result.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 01.09.2023 10:34
 * @see java.util.function.UnaryOperator
 */
@FunctionalInterface
public interface ByteUnaryAccumulator {

    /**
     * Applies this accumulator to the given operand.
     *
     * @param operand the operand to be operated on
     *
     * @return the result of applying this accumulator
     */
    byte apply(byte operand);
}
