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
import java.util.NoSuchElementException;

/**
 * project moonshine
 * created 23.12.2022 10:34
 *
 * @author Alexander A. Kropotin
 */
public class EmptyTuple implements Tuple0 {

    private static final int SIZE = 0;

    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public Object getOrDefault(int index, Object defaultValue) {
        return defaultValue;
    }
}
