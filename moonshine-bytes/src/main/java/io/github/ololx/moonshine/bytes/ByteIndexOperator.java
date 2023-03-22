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

package io.github.ololx.moonshine.bytes;

/**
 * A functional interface for applying a mapping function to a byte index.
 *
 * @apiNote
 * This interface is designed to be used with indexed byte arrays, where each
 * element of the array is a single byte and the index represents the position
 * of that byte in the array.
 *
 * @implSpec
 * The default implementation of {@link #identity()} returns a lambda that
 * simply returns its input, effectively mapping each index to itself.
 *
 * project moonshine
 * created 02.03.2023 10:22
 *
 * @author Alexander A. Kropotin
 */
public interface ByteIndexOperator {

    /**
     * Applies a mapping function to the given byte index.
     *
     * @param index the byte index to apply the mapping function to
     * @return the result of applying the mapping function to the byte index
     */
    int apply(int index);

    /**
     * Returns a byte index operator that applies the identity function.
     *
     * @implSpec
     * The default implementation returns a lambda that simply returns its
     * input, effectively mapping each index to itself.
     *
     * @return a byte index operator that applies the identity function
     */
    static ByteIndexOperator identity() {
        return i -> i;
    }
}

