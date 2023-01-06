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
 * A tuple that always contains two elements.
 *
 * More formally:
 * T2 = (t0, t1) or |T2| = 2.
 *
 * The {@code Tuple2} interface extends {@code Tuple1} and provides
 * all his behaviour.
 *
 * The {@code Tuple2} interface additionally provides one method for the
 * accessing a tuple elements using a {@code getT2} getter.
 *
 * @param <A> the type of first element in this tuple
 * @param <B> the type of second element in this tuple
 *
 * project moonshine
 * created 28.12.2022 19:56
 *
 * @author Alexander A. Kropotin
 */
public interface Tuple2<A, B> extends Tuple1<A> {

    /**
     * Returns the second element in this tuple.
     *
     * @return the second element in this tuple.
     */
    B getT1();
}
