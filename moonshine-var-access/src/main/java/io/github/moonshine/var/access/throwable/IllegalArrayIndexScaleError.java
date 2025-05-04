/**
 * Copyright 2025 the project moonshine authors
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

package io.github.moonshine.var.access.throwable;

/**
 * Thrown to indicate that the array index scale retrieved from the JVM
 * is not a power of two. This typically suggests an invalid or unexpected
 * memory layout for array elements, which may lead to incorrect behavior
 * in low-level memory operations.
 *
 * This error is usually triggered during static initialization when verifying
 * the platform-specific characteristics of array memory layout for direct memory access.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 04/05/2025 9:19pm
 */
public class IllegalArrayIndexScaleError extends Error {

    /**
     * Constructs a new error with the specified detail message.
     *
     * @param message the detail message describing the scale error
     */
    public IllegalArrayIndexScaleError(String message) {
        super(message);
    }
}
