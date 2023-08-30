/**
 * Copyright 2023 the project moonshine authors
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

package io.github.ololx.moonshine.util;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.FORBIDDEN;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 29.08.2023 12:16
 */
public class NonBlockingConcurrentBitSetTest {

    @State
    public static class NonBlockingConcurrentBitSetState {
        final NonBlockingConcurrentBitSet bitSet = new NonBlockingConcurrentBitSet(1);
    }

    @JCStressTest
    @Description("Test race map get and put")
    @Outcome(id = "0, 1", expect = ACCEPTABLE, desc = "return 0L and 1L")
    @Outcome(expect = FORBIDDEN, desc = "Case violating atomicity.")
    public static class MapPutGetTest {

        @Actor
        public void actor1(NonBlockingConcurrentBitSetState state, Z_Result result) {
            state.bitSet.set(0);
            boolean r = state.bitSet.get(0);
            result.r1 = state.bitSet.get(0);
        }

        @Actor
        public void actor2(NonBlockingConcurrentBitSetState state, Z_Result result) {
            state.bitSet.set(1);
            boolean r = state.bitSet.get(1);
            result.r1 = state.bitSet.get(1);
        }
    }
}
