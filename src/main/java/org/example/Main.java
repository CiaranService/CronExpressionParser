package org.example;

import org.example.Exceptions.InvalidInputException;

public class Main {
    public static void main(String[] args) throws InvalidInputException {

        if (args.length == 1) {
            Parser parser = new Parser(args[0]);
            System.out.print(parser);
        }else{
            throw new InvalidInputException("incorrect amount of args");
        }
    }
}