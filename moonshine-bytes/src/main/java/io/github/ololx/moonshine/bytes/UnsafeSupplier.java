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

package io.github.ololx.moonshine.bytes;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * project moonshine
 * created 22.02.2023 21:10
 *
 * @author Alexander A. Kropotin
 */
final class UnsafeSupplier implements Supplier<Unsafe> {

    private static volatile UnsafeSupplier unsafeProvider;

    private static final String THE_UNSAFE = "theUnsafe";

    private final Unsafe unsafe;

    public static UnsafeSupplier getInstance() {
        if (unsafeProvider == null) {
            synchronized (UnsafeSupplier.class) {
                if (unsafeProvider == null) {
                    unsafeProvider = new UnsafeSupplier();
                }
            }
        }

        return unsafeProvider;
    }

    private UnsafeSupplier() {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField(THE_UNSAFE);
            theUnsafeField.setAccessible(true);
            this.unsafe = (Unsafe) theUnsafeField.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Unsafe get() {
        return this.unsafe;
    }
}
