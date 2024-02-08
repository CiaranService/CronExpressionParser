package org.example;

import org.example.Exceptions.InvalidInputException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testValidParser() {
        try {
            new Parser("* * * * * /usr/bin/find");
        }catch(Exception e){
                fail("Exception was thrown");
        }
    }

    @Test
    public void testParserTooFewExpressions(){

        Exception exception = assertThrows(InvalidInputException.class, () -> {
            Parser testParser = new Parser("1 2 * * * ");        });
        String expectedMessage = "invalid number of space separated expressions. Expected: 6 Actual: 5";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testParserTooManyExpressions() {

        Exception exception = assertThrows(InvalidInputException.class, () -> {
            new Parser("1 2 * * * * find");        });
        String expectedMessage = "invalid number of space separated expressions. Expected: 6 Actual: 7";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}