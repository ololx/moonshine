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

package io.github.ololx.moonshine.util.function


import spock.lang.Specification

/**
 * @author Alexander A. Kropotin
 * project moonshine
 * created 15/09/2023 2:33 pm
 */
class ByteUnaryOperatorSpec extends Specification {

    def "addition returns the correct result"() {
        given:
        ByteUnaryOperator addition = {byte value -> (byte) (value + 2)}

        when:
        byte result = addition.applyAsByte((byte) 5)

        then:
        result == (byte) 7
    }

    def "subtraction returns the correct result"() {
        given:
        ByteUnaryOperator subtraction = {byte value -> (byte) (value - 2)}

        when:
        byte result = subtraction.applyAsByte((byte) 5)

        then:
        result == (byte) 3
    }

    def "multiplication returns the correct result"() {
        given:
        ByteUnaryOperator multiplication = {byte value -> (byte) (value * 2)}

        when:
        byte result = multiplication.applyAsByte((byte) 5)

        then:
        result == (byte) 10
    }

    def "division returns the correct result"() {
        given:
        ByteUnaryOperator division = {byte value -> (byte) (value / 2)}

        when:
        byte result = division.applyAsByte((byte) 6)

        then:
        result == (byte) 3
    }

    def "division by zero throws ArithmeticException"() {
        given:
        ByteUnaryOperator division = {byte value -> (byte) (10 / value)}

        when:
        division.applyAsByte((byte) 0)

        then:
        thrown ArithmeticException
    }

    def "composition of subtraction and addition returns the correct result"() {
        given:
        ByteUnaryOperator addition = {byte value -> (byte) (value + 10)}
        ByteUnaryOperator subtraction = {byte value -> (byte) (value - 5)}

        when:
        ByteUnaryOperator composed = addition.compose(subtraction)
        byte result = composed.applyAsByte((byte) 5)

        then:
        result == (byte) 10
    }

    def "composition of multiplication and increment by one returns the correct result"() {
        given:
        ByteUnaryOperator multiplication = {byte value -> (byte) (value * 2)}
        ByteUnaryOperator incrementByOne = {byte value -> (byte) (value + 1)}

        when:
        ByteUnaryOperator composed = multiplication.andThen(incrementByOne)
        byte result = composed.applyAsByte((byte) 3)

        then:
        result == (byte) 7
    }

    def "identity returns the argument value"() {
        given:
        ByteUnaryOperator identityOperator = ByteUnaryOperator.identity()

        when:
        byte result = identityOperator.applyAsByte((byte) 42)

        then:
        result == (byte) 42
    }
}
