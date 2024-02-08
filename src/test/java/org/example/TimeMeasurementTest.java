package org.example;

import org.example.Exceptions.InvalidInputException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TimeMeasurementTest {

    private final String name = "test_name";
    private final int minTimeInterval = 1;
    private final int maxTimeInterval = 100;
    List<Integer> correctListOfMinutes = new ArrayList<>();

    @Test
    public void testValidSingleTime() throws InvalidInputException {
        correctListOfMinutes.add(10);
        validTest("10");
    }

    @Test
    public void validTestValidMultipleSingleTimes() throws InvalidInputException {
        correctListOfMinutes.add(9);
        correctListOfMinutes.add(10);
        correctListOfMinutes.add(15);
        correctListOfMinutes.add(58);
        validTest( "10,15,58,9");
    }

    @Test
    public void validTestNoRepeats() throws InvalidInputException {
        correctListOfMinutes.add(9);
        correctListOfMinutes.add(10);
        correctListOfMinutes.add(15);
        correctListOfMinutes.add(58);
        validTest( "10,15,58,9,10,58");
    }

    @Test
    public void validTestAtLimits() throws InvalidInputException {
        correctListOfMinutes.add(1);
        correctListOfMinutes.add(100);
        validTest( "1,100");
    }

    @Test
    public void invalidTestAboveLimit() {
        exceptionTest("101", "input outside time constraints");
    }

    @Test
    public void invalidTestBelowLimit() {
        exceptionTest("0", "input outside time constraints");
    }

    @Test
    public void validTestRangeOfValues() throws InvalidInputException {
        for (int x = 7; x <= 19; x++) {
            correctListOfMinutes.add(x);
        }
        validTest( "7-19");
    }

    @Test
    public void validTestAstrix() throws InvalidInputException {
        for (int x = minTimeInterval; x <= maxTimeInterval; x++) {
            correctListOfMinutes.add(x);
        }
        validTest("*");
    }

    @Test
    public void testMultipleRangesOfValues() throws InvalidInputException {
        for (int x = 8; x <= 19; x++) {
            correctListOfMinutes.add(x);
        }
        for (int x = 1; x <= 3; x++) {
            correctListOfMinutes.add(x);
        }
        for (int x = 55; x <= 56; x++) {
            correctListOfMinutes.add(x);
        }
        validTest("8-19,1-3,55-56");
    }

    @Test
    public void testRangeOfValuesAboveUpperConstraint() {
        exceptionTest("8-101", "input outside time constraints");
    }

    @Test
    public void testRangeOfValuesBelowLowerConstraint() throws InvalidInputException {
        exceptionTest("0-3", "input outside time constraints");
    }

    @Test
    public void testStepValuesSeparatorWithAsterisk() throws InvalidInputException {
        for (int x = minTimeInterval; x <= maxTimeInterval; x+= 2) {
            correctListOfMinutes.add(x);
        }
        validTest("*/2");
    }

    @Test
    public void testStepValuesSeparatorWithLimit() throws InvalidInputException {
        for (int x = 10; x <= 39; x+=2){
            correctListOfMinutes.add(x);
        }
        validTest("10-39/2");
    }

    @Test
    public void testInvalidInputRandomString(){
        exceptionTest("hello",  "invalid input");

    }

    @Test
    public void testInvalidInputNoTimes() {
        exceptionTest(",-/",  "invalid input");
    }

    @Test
    public void testInvalidInputEmptyString() {
        exceptionTest("",  "invalid input");
    }

    @Test
    public void testInvalidInputIncrementTooBig() {
        exceptionTest("1-2/101",  "increment outside time constraints");

    }

    @Test
    public void testInvalidInputSeparatorNoRange() {
        exceptionTest("/10",  "invalid input");
    }

    @Test
    public void testInvalidInputSeparatorNoIncrement() {
        exceptionTest("*/",  "invalid input");
    }

    @Test
    public void testInvalidInputSeparatorInvalidRange() {
        exceptionTest("17-101/2", "input outside time constraints");
    }

    @Test
    public void testInvalidInputSeparatorInvalidRange1() {
        exceptionTest("0-6/2", "input outside time constraints");
    }

    private void exceptionTest(String input, String expectedMessage){
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            new TimeMeasurement(name, minTimeInterval, maxTimeInterval, input);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(name + ": " + expectedMessage));
    }

    private void validTest(String input) throws InvalidInputException {
        Collections.sort(correctListOfMinutes);
        TimeMeasurement timeMeasurement = new TimeMeasurement(name, minTimeInterval, maxTimeInterval, input);
        assertEquals(timeMeasurement.getActualTimeIntervals(),
                correctListOfMinutes);
    }





}