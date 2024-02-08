package org.example;

import org.example.Exceptions.InvalidInputException;

import java.util.ArrayList;

public class Parser {

    private final ArrayList<TimeMeasurement> parsedTimes;
    private String command;
    public Parser(String input) throws InvalidInputException {
        parsedTimes = splitInput(input);
    }

    public ArrayList<TimeMeasurement> splitInput(String expression) throws InvalidInputException {

        String[] splitExpressions = expression.split(" ");
        ArrayList<TimeMeasurement> parsedTimes = new ArrayList<>();

        if (splitExpressions.length == 6){
            parsedTimes.add(new TimeMeasurement("minute", 0, 59, splitExpressions[0]));
            parsedTimes.add(new TimeMeasurement("hour", 0, 23, splitExpressions[1]));
            parsedTimes.add(new TimeMeasurement("day of month", 1, 31, splitExpressions[2]));
            parsedTimes.add(new TimeMeasurement("month", 1, 12, splitExpressions[3]));
            parsedTimes.add(new TimeMeasurement("day of week", 1, 7, splitExpressions[4]));
            this.command = splitExpressions[5];
        }else{
            throw new InvalidInputException("invalid number of space separated expressions. Expected: 6 Actual: " + splitExpressions.length);
        }

        return parsedTimes;

    }

    public String toString(){
        StringBuilder string = new StringBuilder();

        for (TimeMeasurement parsedTime : parsedTimes){
            string.append(String.format("%-14s", parsedTime.getName()));
            for (Integer i : parsedTime.getActualTimeIntervals()){
                string.append(i + " ");
            }
            string.append("\n");
        }
        string.append( String.format("%-14s" + command + "\n", "command") );

        return string.toString();
    }

}
