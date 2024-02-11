package org.example;

import org.example.Exceptions.InvalidInputException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testValidMain() {
        try {
            String[] args = new String[1];
            args[0] = "* * * * * /bin/find";
            Main.main(args);
        }catch(InvalidInputException e) {
            fail("Exception was thrown");
        }
    }

    @Test
    public void testMainTooManyArgs() {
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            String[] args = new String[2];
            args[0] = "* * * * * ";
            args[1] = "/bin/find";
            Main.main(args);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains("incorrect amount of args"));

    }

}