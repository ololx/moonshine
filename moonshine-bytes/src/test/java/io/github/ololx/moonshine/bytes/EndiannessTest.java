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

package io.github.ololx.moonshine.bytes;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * project moonshine
 * created 23.03.2023 20:18
 *
 * @author Alexander A. Kropotin
 */
public class EndiannessTest {

    @Test
    public void testBigEndian() {
        Assert.assertEquals(Endianness.BIG_ENDIAN.getName(), "Big-Endian");
        Assert.assertEquals(Endianness.BIG_ENDIAN.getId(), 0x1);

        Assert.assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(0), 3);
        Assert.assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(1), 2);
        Assert.assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(2), 1);
        Assert.assertEquals(Endianness.BIG_ENDIAN.byteOrder(4).apply(3), 0);
    }

    @Test
    public void testLittleEndian() {
        Assert.assertEquals(Endianness.LITTLE_ENDIAN.getName(), "Little-Endian");
        Assert.assertEquals(Endianness.LITTLE_ENDIAN.getId(), 0x2);

        Assert.assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(0), 0);
        Assert.assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(1), 1);
        Assert.assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(2), 2);
        Assert.assertEquals(Endianness.LITTLE_ENDIAN.byteOrder(4).apply(3), 3);
    }

    @Test
    public void testPdpEndian() {
        Assert.assertEquals(Endianness.PDP_ENDIAN.getName(), "PDP-Endian");
        Assert.assertEquals(Endianness.PDP_ENDIAN.getId(), 0x3);

        Assert.assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(0), 2);
        Assert.assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(1), 3);
        Assert.assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(2), 0);
        Assert.assertEquals(Endianness.PDP_ENDIAN.byteOrder(4).apply(3), 1);
    }
}
