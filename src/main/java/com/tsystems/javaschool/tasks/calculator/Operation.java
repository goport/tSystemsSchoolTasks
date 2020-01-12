package com.tsystems.javaschool.tasks.calculator;

public enum Operation {
    ADD(1, '+'),
    SUB(1, '-'),
    DIV(2, '/'),
    MUL(2, '*'),
    OBRCKT(0, '('),
    CBRCKT(0, ')');

    private int priority;
    private char sym;

    Operation(int priority, char sym) {
        this.priority = priority;
        this.sym = sym;
    }

    public int getPriority() {
        return this.priority;
    }

    public char getSym() {
        return this.sym;
    }
}
