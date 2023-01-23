/**
 * Copyright 2023 the project moonshine authors
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

package io.github.ololx.moonshine.tuple;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * project moonshine
 * created 18.01.2023 18:59
 * <p>
 * @author Alexander A. Kropotin
 */
abstract class AbstractTuple implements Tuple {

    protected final int checkIndex(int index) {
        if (!IndexBounds.checkIndex(index, this.size())) {
            throw new IndexOutOfBoundsException("There is no elements by index " + index);
        }

        return index;
    }

    @Override
    public String toString() {
        final int elementsCount = this.size();
        final int tupleStringLength = elementsCount == 0 ? 2 : elementsCount * 3;
        final StringBuilder tupleStringBuilder = new StringBuilder(tupleStringLength);

        tupleStringBuilder.append(String.valueOf(this.getOrDefault(0, "")));
        IntStream.range(1, elementsCount).forEach(index -> {
            tupleStringBuilder.append(", ");
            tupleStringBuilder.append(String.valueOf(this.getOrDefault(index, "")));
        });

        tupleStringBuilder.insert(0, "(");
        tupleStringBuilder.append(")");

        return tupleStringBuilder.toString();
    }
}
