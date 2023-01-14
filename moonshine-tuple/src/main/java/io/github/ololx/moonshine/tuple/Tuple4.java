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
 * A tuple that always contains four elements.
 *
 * More formally:
 * T3 = (t0, t1, t2, t3) or |T4| = 4.
 *
 * The {@code Tuple4} interface extends {@code Tuple} and provides
 * all his behaviour.
 *
 * The {@code Tuple4} interface additionally provides one method for the
 * accessing a tuple elements using getters {@code getT0}, {@code getT1},
 * {@code getT2} and {@code getT3}.
 *
 * @param <A> the type of first element in this tuple
 * @param <B> the type of second element in this tuple
 * @param <C> the type of third element in this tuple
 * @param <D> the type of fourth element in this tuple
 *
 * project moonshine
 * created 05.01.2023 20:41
 *
 * @author Alexander A. Kropotin
 */
public interface Tuple4<A, B, C, D> extends Tuple {

    /**
     * Returns the first element in this tuple.
     *
     * @return  the first element in this tuple.
     */
    A getT0();

    /**
     * Returns the second element in this tuple.
     *
     * @return the second element in this tuple.
     */
    B getT1();

    /**
     * Returns the third element in this tuple.
     *
     * @return the third element in this tuple.
     */
    C getT2();

    /**
     * Returns the fourth element in this tuple.
     *
     * @return the fourth element in this tuple.
     */
    D getT3();
}
