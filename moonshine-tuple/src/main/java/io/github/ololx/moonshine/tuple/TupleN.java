/**
 * Copyright 2022 the project moonshine authors
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

package io.github.ololx.moonshine.tuple;

/**
 * A tuple that could contain N elements, where N can be more than eight
 * {@code Tuple8.size()} and less than (2^31)-1 {@see Integer.MAX_VALUE}.
 *
 * More formally:
 * T3 = (t0, t1, t2, ... tn) or |TN| = N, where 8 < N <= 2147483647.
 *
 * The {@code TupleN} interface extends {@code Tuple} and provides
 * all his behaviour.
 *
 * project moonshine
 * created 14.01.2023 10:48
 *
 * @author Alexander A. Kropotin
 */
public interface TupleN extends Tuple {
}
