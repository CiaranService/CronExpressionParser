package org.example;

import lombok.Getter;
import org.example.Exceptions.InvalidInputException;

import java.util.*;
import java.util.stream.Collectors;

public class TimeMeasurement {
    @Getter
    private final String name;
    private final int minTimeInterval;
    private final int maxTimeInterval;
    @Getter
    private final List<Integer> actualTimeIntervals;

    public TimeMeasurement(String name, int minTimeInterval, int maxTimeInterval, String input) throws InvalidInputException {
        this.name = name;
        this.minTimeInterval = minTimeInterval;
        this.maxTimeInterval = maxTimeInterval;
        try {
            this.actualTimeIntervals = determineTimeIntervals(input);
        }catch(NumberFormatException numberFormatException){
            throw new InvalidInputException(name + ": invalid input");
        }
    }

    public List<Integer> determineTimeIntervals(String expression) throws InvalidInputException {
        List<Integer> validTimeIntervals = new ArrayList<>();

        if (expression.contains(",")){
            validTimeIntervals.addAll(valueListSeparator(expression));
        }else if(expression.contains("/")){
            validTimeIntervals.addAll(stepValuesSeparator(expression));
        }else if(expression.contains("-")) {
            validTimeIntervals.addAll(valuesWithinStringRange(expression, 1));
        }else if(expression.equals("*")){
            for (int x = minTimeInterval; x <= maxTimeInterval; x++){
                validTimeIntervals.add(x);
            }
        }else {
            int time = Integer.parseInt(expression);
            if (time >= minTimeInterval && time <= maxTimeInterval) {
                validTimeIntervals.add(Integer.parseInt(expression));
            }else{
                throw new InvalidInputException(name + ": input outside time constraints");
            }
        }

        validTimeIntervals = validTimeIntervals.stream()
                .distinct()
                .collect(Collectors.toList());

        Collections.sort(validTimeIntervals);
        return validTimeIntervals;
    }


    private List<Integer> valueListSeparator(String expression) throws InvalidInputException {
        ArrayList<Integer> timeIntervals = new ArrayList<>();
        String[] separatedTimes = expression.split(",");

        for (String separatedTime : separatedTimes){
            timeIntervals.addAll(determineTimeIntervals(separatedTime));
        }

        return timeIntervals;
    }

    private List<Integer> stepValuesSeparator(String expression) throws InvalidInputException {
        ArrayList<Integer> timeIntervals = new ArrayList<>();

        String[] stepExpression = expression.split("/");

        if (stepExpression[0].contains("-")){
            try {
                timeIntervals.addAll(valuesWithinStringRange(stepExpression[0],
                        Integer.parseInt(stepExpression[1])));
            }catch(ArrayIndexOutOfBoundsException e){
                throw new InvalidInputException(name + ": invalid input");
            }
        } else if (stepExpression.length == 2 && stepExpression[0].equals("*")) {
            for (int x = minTimeInterval; x <= maxTimeInterval; x += Integer.parseInt(stepExpression[1])) {
                timeIntervals.add(x);
            }
        }else{
            throw new InvalidInputException( name + ": invalid input");
        }

        return timeIntervals;
    }

    private List<Integer> valuesWithinStringRange(String s, int increment) throws InvalidInputException {

        if (increment > maxTimeInterval){
            throw new InvalidInputException(name + ": increment outside time constraints");
        }
        String[] range = s.split("-");
        ArrayList<Integer> timeIntervals = new ArrayList<>();

        if (Integer.parseInt(range[0]) >= minTimeInterval && Integer.parseInt(range[1]) <= maxTimeInterval ) {
            for (int x = Integer.parseInt(range[0]); x <= Integer.parseInt(range[1]); x += increment) {
                timeIntervals.add(x);
            }
        }else{
            throw new InvalidInputException(name + ": input outside time constraints");
        }
        return  timeIntervals;
    }

}
