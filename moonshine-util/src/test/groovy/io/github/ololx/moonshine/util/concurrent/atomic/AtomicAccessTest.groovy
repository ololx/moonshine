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

package io.github.ololx.moonshine.util.concurrent.atomic


import groovy.transform.TupleConstructor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 16/09/2023 11:09 am
 */
class AtomicAccessTest extends Specification {

    def "endianness() - when Endianness was detected then return Endianness BigEndian or LittleEndian"() {
        given:
        AtomicAccess atomicAccess = new AtomicAccess()

        when:
        AtomicAccess.Endianness endianness = atomicAccess.endianness()

        then:
        endianness != null
        endianness.isBigEndian() || endianness.isLittleEndian()
    }

    @Unroll
    def "arrayBaseOffset() - when put #arrayClass then return offset"() {
        given:
        AtomicAccess atomicAccess = new AtomicAccess()

        when:
        Integer arrayBaseOffset = atomicAccess.arrayBaseOffset(arrayClass)

        then:
        arrayBaseOffset != null

        where:
        arrayClass << [byte[], short[], char[], int[], long[], Object[]]
    }

    @Unroll
    def "arrayIndexScale() - when put #arrayClass then return scale"() {
        given:
        AtomicAccess atomicAccess = new AtomicAccess()

        when:
        Integer arrayIndexScale = atomicAccess.arrayIndexScale(byte[].class)

        then:
        arrayIndexScale != null

        where:
        arrayClass << [byte[], short[], char[], int[], long[], Object[]]
    }

    @Unroll
    def "objectFieldOffset() - when put #objClass and #fieldName then return scale"(Class<?> objClass, String fieldName) {
        given:
        AtomicAccess atomicAccess = new AtomicAccess()

        when:
        Long objectFieldOffset = atomicAccess.objectFieldOffset(objClass, fieldName)

        then:
        objectFieldOffset != null

        where:
        objClass        | fieldName
        Byte.class      | "value"
        Character.class | "value"
        Short.class     | "value"
        Integer.class   | "value"
        Long.class      | "value"
    }

    def "getByte() - when #object exists then return value of #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getByte(object, valueOffset)

        then:
        value == object.value

        where:
        object << [new MutableByte(Byte.MIN_VALUE), new MutableByte(Byte.MAX_VALUE)]
    }

    def "getByteVolatile() - when #object exists then return value of #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getByteVolatile(object, valueOffset)

        then:
        value == object.value

        where:
        object << [new MutableByte(Byte.MIN_VALUE), new MutableByte(Byte.MAX_VALUE)]
    }

    def "putByte() - when put #value in #object then value changed in #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        atomicAccess.putByte(object, valueOffset, value)

        then:
        object.value == value

        where:
        object                          | value
        new MutableByte(Byte.MIN_VALUE) | Byte.MAX_VALUE
        new MutableByte(Byte.MAX_VALUE) | Byte.MIN_VALUE
    }

    def "putByteVolatile() - when put #value in #object then value changed in #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        atomicAccess.putByteVolatile(object, valueOffset, value)

        then:
        object.value == value

        where:
        object                          | value
        new MutableByte(Byte.MIN_VALUE) | Byte.MAX_VALUE
        new MutableByte(Byte.MAX_VALUE) | Byte.MIN_VALUE
    }

    def "getInt() - when #object exists then return value of #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getInt(object, valueOffset)

        then:
        value == object.value

        where:
        object << [new MutableInteger(Integer.MIN_VALUE), new MutableInteger(Integer.MAX_VALUE)]
    }

    def "getIntVolatile() - when #object exists then return value of #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getIntVolatile(object, valueOffset)

        then:
        value == object.value

        where:
        object << [new MutableInteger(Integer.MIN_VALUE), new MutableInteger(Integer.MAX_VALUE)]
    }

    def "putInt() - when put #value in #object then value changed in #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        atomicAccess.putInt(object, valueOffset, value)

        then:
        object.value == value

        where:
        object                                | value
        new MutableInteger(Integer.MIN_VALUE) | Integer.MAX_VALUE
        new MutableInteger(Integer.MAX_VALUE) | Integer.MIN_VALUE
    }

    def "putIntVolatile() - when put #value in #object then value changed in #object field"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        atomicAccess.putInt(object, valueOffset, value)

        then:
        object.value == value

        where:
        object                                | value
        new MutableInteger(Integer.MIN_VALUE) | Integer.MAX_VALUE
        new MutableInteger(Integer.MAX_VALUE) | Integer.MIN_VALUE
    }

    def "compareAndSwapByte() - when #object contains #oldValue then swap on #newValue"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def result = atomicAccess.compareAndSwapByte(object, valueOffset, oldValue, newValue)

        then:
        result
        object.value == newValue

        where:
        object                          | oldValue       | newValue
        new MutableByte(Byte.MIN_VALUE) | Byte.MIN_VALUE | Byte.MAX_VALUE
        new MutableByte(Byte.MAX_VALUE) | Byte.MAX_VALUE | Byte.MIN_VALUE
    }

    def "getAndAddByte() - when in #object put #delta then #object contains #newValue"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getAndAddByte(object, valueOffset, delta)

        then:
        value == (newValue - delta) as byte
        object.value == newValue

        where:
        object                       | delta      | newValue
        new MutableByte(0 as byte)   | 12 as byte | 12 as byte
        new MutableByte(100 as byte) | 22 as byte | 122 as byte
    }

    def "getAndAddByte() - when in #object put #newValue then #object contains #newValue"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getAndSetByte(object, valueOffset, newValue)

        then:
        value != newValue
        object.value == newValue

        where:
        object                       | newValue
        new MutableByte(0 as byte)   | 12 as byte
        new MutableByte(100 as byte) | 122 as byte
    }

    def "compareAndSwapInt() - when #object contains #oldValue then swap on #newValue"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def result = atomicAccess.compareAndSwapInt(object, valueOffset, oldValue, newValue)

        then:
        result
        object.value == newValue

        where:
        object                                | oldValue          | newValue
        new MutableInteger(Integer.MIN_VALUE) | Integer.MIN_VALUE | Integer.MAX_VALUE
        new MutableInteger(Integer.MAX_VALUE) | Integer.MAX_VALUE | Integer.MIN_VALUE
    }

    def "getAndAddInt() - when in #object put #delta then #object contains #newValue"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getAndAddInt(object, valueOffset, delta)

        then:
        value == newValue - delta
        object.value == newValue

        where:
        object                  | delta | newValue
        new MutableInteger(0)   | 12    | 12
        new MutableInteger(100) | 22    | 122
    }

    def "getAndSetInt() - when in #object put #newValue then #object contains #newValue"() {
        given:
        def atomicAccess = new AtomicAccess()

        when:
        def valueOffset = atomicAccess.objectFieldOffset(object.class, "value")
        def value = atomicAccess.getAndSetInt(object, valueOffset, newValue)

        then:
        value != newValue
        object.value == newValue

        where:
        object                  | newValue
        new MutableInteger(0)   | 12
        new MutableInteger(100) | 122
    }

    @TupleConstructor
    class MutableByte {
        byte value
    }

    @TupleConstructor
    class MutableInteger {
        int value
    }
}
