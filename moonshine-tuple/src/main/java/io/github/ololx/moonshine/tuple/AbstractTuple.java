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

package io.github.ololx.moonshine.tuple;

/**
 * This class provides a skeletal implementation of {@code Tuple}
 * interface to minimize the effort required to implement that interface.
 * <br/>
 * This class contains basic realizations of {@code Tuple} interface methods.
 *
 * project moonshine
 * created 18.01.2023 18:59
 * <br/>
 * @author Alexander A. Kropotin
 */
abstract class AbstractTuple implements Tuple {

    /**
     * Returns a string representation of this tuple.
     *
     * @implSpec
     * The string consists of a list of the tuple's elements in their order,
     * enclosed in round brackets ({@code "()"}).  Adjacent elements are
     * separated by the characters {@code ", "} (comma and space). Elements
     * are converted to strings as by {@link String#valueOf(Object)}.
     *
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        return TupleString.format(this);
    }
}
