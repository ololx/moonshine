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

package io.github.ololx.moonshine.bloom.filter;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 24/11/2023 11:25 am
 */
public interface BloomFilter<V> {

    boolean add(Entry<V> value);

    boolean contains(Entry<V> value);

    interface Entry<V> {

        V getValue();

        byte[] getBytes();
    }
}
