package ru.neoflex.vacationpaycalculator.exceptions;

public class IncorrectUserDataException extends RuntimeException {

    public IncorrectUserDataException(String message) {
        super(message);
    }

    public IncorrectUserDataException() {
    }
}
