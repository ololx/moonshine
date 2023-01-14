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
 * A tuple that always contains eight elements.
 *
 * More formally:
 * T3 = (t0, t1, t2, t3, t4, t5, t6, t7) or |T8| = 8.
 *
 * The {@code Tuple4} interface extends {@code Tuple} and provides
 * all his behaviour.
 *
 * The {@code Tuple3} interface additionally provides one method for the
 * accessing a tuple elements using getters {@code getT0}, {@code getT1},
 * {@code getT2}, {@code getT3}, {@code getT4}, {@code getT5}
 * {@code getT6} and {@code getT7}.
 *
 * @param <A> the type of first element in this tuple
 * @param <B> the type of second element in this tuple
 * @param <C> the type of third element in this tuple
 * @param <D> the type of fourth element in this tuple
 * @param <E> the type of fifth element in this tuple
 * @param <F> the type of sixth element in this tuple
 * @param <G> the type of seventh element in this tuple
 * @param <H> the type of eighth element in this tuple
 *
 * project moonshine
 * created 14.01.2023 10:48
 *
 * @author Alexander A. Kropotin
 */
public interface Tuple8<A, B, C, D, E, F, G, H> extends Tuple {

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

    /**
     * Returns the fifth element in this tuple.
     *
     * @return the fifth element in this tuple.
     */
    E getT4();

    /**
     * Returns the sixth element in this tuple.
     *
     * @return the sixth element in this tuple.
     */
    F getT5();

    /**
     * Returns the seventh element in this tuple.
     *
     * @return the seventh element in this tuple.
     */
    G getT6();

    /**
     * Returns the eighth element in this tuple.
     *
     * @return the eighth element in this tuple.
     */
    H getT7();
}
