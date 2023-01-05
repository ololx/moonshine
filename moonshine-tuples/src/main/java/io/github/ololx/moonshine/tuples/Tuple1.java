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
package io.github.ololx.moonshine.tuples;

/**
 * A tuple that always contains one element.
 *
 * More formally:
 * T1 = (a1) or |T1| = 1.
 *
 * The {@code Tuple1} interface extends {@code Tuple} and provides
 * all his behaviour.
 *
 * The {@code Tuple1} interface additionally provides one method for the
 * accessing a tuple element using a {@code getT1} getter.
 *
 * @param <A> the type of element in this tuple
 *
 * project moonshine
 * created 28.12.2022 19:56
 *
 * @author Alexander A. Kropotin
 */
public interface Tuple1<A> extends Tuple {

    /**
     * Returns the first element in this tuple.
     *
     * @return  the first element in this tuple.
     */
    A getT1();
}
