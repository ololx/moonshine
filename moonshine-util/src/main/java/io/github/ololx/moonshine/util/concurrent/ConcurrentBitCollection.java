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

package io.github.ololx.moonshine.util.concurrent;

import io.github.ololx.moonshine.util.BitCollection;

/**
 * An interface for a concurrent bit collection, providing methods to manipulate
 * individual bits in a thread-safe manner.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 14.08.2023 13:51
 * @apiNote Implementations of this interface are expected to provide thread-safe
 *     behavior for all methods.
 */
public interface ConcurrentBitCollection extends BitCollection {}
