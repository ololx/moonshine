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

import io.github.ololx.moonshine.bytes.coding.ByteIndexOperator;

/**
 * An interface for providing {@code ByteIndexOperator} based on the most
 * significant byte.
 *
 * project moonshine
 * created 02.03.2023 10:22
 *
 * @author Alexander A. Kropotin
 */
public interface ByteOrderProvider {

    /**
     * Returns a byte index operator based on the given most significant byte.
     *
     * @param size (value size) the number of bytes occupied by the value.
     *
     * @return a {@code ByteIndexOperator} instance for the given byte order.
     *
     * @implSpec This method should return a non-null {@code ByteIndexOperator} instance
     *     for all valid byte orders.
     */
    ByteIndexOperator provide(int size);
}
