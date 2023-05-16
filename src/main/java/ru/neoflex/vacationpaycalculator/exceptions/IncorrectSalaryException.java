package ru.neoflex.vacationpaycalculator.exceptions;

public class IncorrectSalaryException extends IncorrectUserDataException {

    public IncorrectSalaryException(double incorrectValue) {
        super("Incorrect salary: " + incorrectValue);
    }

    public IncorrectSalaryException() {
    }
}
