package io.github.ololx.moonshine.util.function;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 08.09.2023 16:01
 */
public class ByteUnaryOperatorTest {

    @Test
    public void applyAsByte_whenAddition_thenReturnResult() {
        ByteUnaryOperator addition = value -> (byte) (value + 2);
        byte result = addition.applyAsByte((byte) 5);

        assertEquals(result, (byte) 7);
    }

    @Test
    public void applyAsByte_whenSubtraction_thenReturnResult() {
        ByteUnaryOperator subtraction = value -> (byte) (value - 2);
        byte result = subtraction.applyAsByte((byte) 5);

        assertEquals(result, (byte) 3);
    }

    @Test
    public void applyAsByte_whenMultiplication_thenReturnResult() {
        ByteUnaryOperator multiplication = value -> (byte) (value * 2);
        byte result = multiplication.applyAsByte((byte) 5);

        assertEquals(result, (byte) 10);
    }

    @Test
    public void applyAsByte_whenDivision_thenReturnResult() {
        ByteUnaryOperator division = value -> (byte) (value / 2);
        byte result = division.applyAsByte((byte) 6);

        assertEquals(result, (byte) 3);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void applyAsByte_whenDivisionByZero_thenThrowException() {
        ByteUnaryOperator division = value -> (byte) (10 / value);
        division.applyAsByte((byte) 0);
    }

    @Test
    public void compose_whenAdditionComposeSubtraction_thenReturnResultOfSubtractionBeforeAddition() {
        ByteUnaryOperator addition = value -> (byte) (value + 10);
        ByteUnaryOperator subtraction = value -> (byte) (value - 5);

        ByteUnaryOperator composed = addition.compose(subtraction);
        byte result = composed.applyAsByte((byte) 5);

        assertEquals(result, (byte) 10);
    }

    @Test
    public void andThen_whenMultiplicationAndThenIncrementByOne_thenReturnResultOfMultiplicationBeforeIncrementByOne() {
        ByteUnaryOperator multiplication = value -> (byte) (value * 2);
        ByteUnaryOperator incrementByOne = value -> (byte) (value + 1);

        ByteUnaryOperator composed = multiplication.andThen(incrementByOne);
        byte result = composed.applyAsByte((byte) 3);

        assertEquals(result, (byte) 7);
    }

    @Test
    public void identity_whenIdentity_thenReturnArgValue() {
        ByteUnaryOperator identityOperator = ByteUnaryOperator.identity();
        byte result = identityOperator.applyAsByte((byte) 42);

        assertEquals(result, (byte) 42);
    }
}