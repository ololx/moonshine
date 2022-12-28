/**
 * Copyright 2022 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ololx.moonshine.tuples;

import java.util.Iterator;

/**
 * project moonshine
 * created 28.12.2022 20:19
 *
 * @author Alexander A. Kropotin
 */
public class Couple<A, B> implements Tuple2<A, B> {

    private static final int SIZE = 2;

    private final A a;

    private final B b;

    public Couple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public Object get(int index) {
        switch (index) {
            case 1:
                return this.a;
            case 2:
                return this.b;
            default:
                throw new IndexOutOfBoundsException("There is no elements by index " + index);
        }
    }

    @Override
    public A getA() {
        return this.a;
    }

    @Override
    public B getB() {
        return this.b;
    }

    @Override
    public Iterator<Object> iterator() {
        return null;
    }
}
