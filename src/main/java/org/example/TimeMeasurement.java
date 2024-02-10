package org.example;

import lombok.Getter;
import org.example.Exceptions.InvalidInputException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Time Measurement contains fields and methods that are used to determine
 * the actual time intervals based on a cron expression
 */
public class TimeMeasurement {
    @Getter
    private final String name;
    private final int minTimeInterval;
    private final int maxTimeInterval;
    @Getter
    private final List<Integer> actualTimeIntervals;

    public TimeMeasurement(String name, int minTimeInterval, int maxTimeInterval, String expression) throws InvalidInputException {
        this.name = name;
        this.minTimeInterval = minTimeInterval;
        this.maxTimeInterval = maxTimeInterval;
        try {
            this.actualTimeIntervals = determineTimeIntervals(expression);
        }catch(NumberFormatException numberFormatException){
            throw new InvalidInputException(name + ": invalid input");
        }
    }

    /**
     * determine the time intervals based on time measurement cron expression
     * @param expression
     * @return list of time intervals based on cron expression
     * @throws InvalidInputException
     */
    public List<Integer> determineTimeIntervals(String expression) throws InvalidInputException {
        List<Integer> validTimeIntervals = new ArrayList<>();

        if (expression.contains(",")){
            validTimeIntervals.addAll(expressionSeparator(expression));
        }else if(expression.contains("/")){
            validTimeIntervals.addAll(getStepExpressionTimeIntervals(expression));
        }else if(expression.contains("-")) {
            validTimeIntervals.addAll(getTimeIntervalsWithinRange(expression, 1));
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

    /**
     * split the cron expression on a comma and determineTimeIntervals
     * @param expression
     * @return list of time intervals based on cron expression
     * @throws InvalidInputException
     */
    private List<Integer> expressionSeparator(String expression) throws InvalidInputException {
        ArrayList<Integer> timeIntervals = new ArrayList<>();
        String[] separatedTimes = expression.split(",");

        for (String separatedTime : separatedTimes){
            timeIntervals.addAll(determineTimeIntervals(separatedTime));
        }

        return timeIntervals;
    }

    /**
     * determine time intervals for a step expression
     * @param expression valid examples: "&#42/2", "11-49/5"
     * @return
     * @throws InvalidInputException
     */
    private List<Integer> getStepExpressionTimeIntervals(String expression) throws InvalidInputException {
        ArrayList<Integer> timeIntervals = new ArrayList<>();

        String[] stepExpression = expression.split("/");

        if (stepExpression[0].contains("-")){
            try {
                timeIntervals.addAll(getTimeIntervalsWithinRange(stepExpression[0],
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

    /**
     * determine time intervals for a step expression with given increment
     * @param expression valid expressions: "20-40", "5-6"
     * @param increment
     * @return
     * @throws InvalidInputException
     */
    private List<Integer> getTimeIntervalsWithinRange(String expression, int increment) throws InvalidInputException {

        if (increment > maxTimeInterval){
            throw new InvalidInputException(name + ": increment outside time constraints");
        }
        String[] range = expression.split("-");
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
