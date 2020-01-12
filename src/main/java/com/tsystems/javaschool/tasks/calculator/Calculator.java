package com.tsystems.javaschool.tasks.calculator;

import java.util.LinkedList;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {

        // first check the valid
        if (statement == null || statement.isEmpty()) return null;

        LinkedList<Double> numList = new LinkedList<>();
        LinkedList<Operation> operationList = new LinkedList<>();
        LinkedList<String> parsedStatement = new LinkedList<>();

        int bufNum = 0;
        //parsing of statement
        for (int i = 0; i < statement.length(); i++) {

            if (Character.isDigit(statement.charAt(i)) || (statement.charAt(i) == '.')) {
                bufNum++;
            } else {
                if (bufNum != 0) {
                    parsedStatement.addLast(statement.subSequence(i - bufNum, i).toString());
                    bufNum = 0;
                }
                parsedStatement.addLast(String.valueOf(statement.charAt(i)));
            }
        }
        if (bufNum != 0) {
            parsedStatement.addLast(statement.subSequence(statement.length() - bufNum, statement.length()).toString());
        }
        //-------------------------------------------------------------------------------------------------------------
        //second check valid of statement
        int bracket = 0;
        int bracketQuantity = 0;
        int digit = 0;

        for (int i = 0; i < parsedStatement.size(); i++) {
            if (parsedStatement.get(i).equals(String.valueOf(Operation.OBRCKT.getSym()))) {
                bracket++;
                bracketQuantity++;
            }
            if (parsedStatement.get(i).equals(String.valueOf(Operation.CBRCKT.getSym()))) {
                bracket--;
                bracketQuantity++;
            }
            if (Character.isDigit(parsedStatement.get(i).charAt(0)) || Character.valueOf(parsedStatement.get(i).charAt(0)).equals('.'))
                digit++;
        }
        if (bracket != 0 || parsedStatement.size() - digit - bracketQuantity >= digit) return null;
        //-------------------------------------------------------------------------------------------------------------
        double result;
        // parsing of statement to operations/numeric
        while (!parsedStatement.isEmpty()) {
            try {
                numList.addLast(Double.parseDouble(parsedStatement.getFirst()));
                parsedStatement.removeFirst();

            } catch (NumberFormatException e) {

                //for check the symbol is contained to Operation ENUM
                boolean contain = false;

                //find operation
                for (Operation op : Operation.values()) {
                    if (String.valueOf(op.getSym()).equals(parsedStatement.getFirst())) {
                        contain = true;
                        // add operation to opList if is it first element / prioryty curr > priority last / (
                        if (operationList.isEmpty() || operationList.getLast().getPriority() < op.getPriority() || op.getSym() == '(') {
                            operationList.addLast(op);
                            break;
                        }
                        else {
                            if (op.getSym() == Operation.CBRCKT.getSym()) {
                                while (operationList.getLast().getSym() != Operation.OBRCKT.getSym()) {
                                    //make calc before (
                                    numList.addLast(result=makeCalc(operationList.removeLast(), numList.removeLast(), numList.removeLast()));
                                    if (result==Double.POSITIVE_INFINITY||result==Double.NEGATIVE_INFINITY||result==Double.NaN) return null;
                                }
                                //remove (
                                operationList.removeLast();
                            } else {
                                while (operationList.size() > 0 && op.getPriority() <= operationList.getLast().getPriority()) {
                                    //make calc before opList is not empty / curr priority>last priority
                                    numList.addLast(result=makeCalc(operationList.removeLast(), numList.removeLast(), numList.removeLast()));
                                    if (result==Double.POSITIVE_INFINITY||result==Double.NEGATIVE_INFINITY||result==Double.NaN) return null;
                                }
                                //add curr operation
                                operationList.addLast(op);
                            }
                            break;
                        }
                    }
                }
                //if symb is not contained to opList
                if (!contain) return null;
                parsedStatement.removeFirst();
            }
        }
        //-------------------------------------------------------------------------------------------------------------
        // make calculation
        while (numList.size() > 1) {
            numList.addLast(result=makeCalc(operationList.removeLast(), numList.removeLast(), numList.removeLast()));
            if (result==Double.POSITIVE_INFINITY||result==Double.NEGATIVE_INFINITY||result==Double.NaN) return null;
        }

        //is it for jUnit tests
        if (Math.abs(numList.getLast().intValue() - numList.getLast()) <= 0.0000001)
            return String.valueOf(numList.getLast().intValue());

        return String.valueOf(numList.getLast());
    }

    private double makeCalc(Operation operation, double num2, double num1) {
        switch (operation) {
            case ADD:
                return num1 + num2;
            case SUB:
                return num1 - num2;
            case DIV:
                return num1 / num2;
            case MUL:
                return num1 * num2;
        }
        return Double.NaN;
    }
}
