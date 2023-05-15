package ru.neoflex.vacationpaycalculator.exceptions;

import java.time.LocalDate;

public class IncorrectVacationDurationException extends IncorrectUserDataException {

    public IncorrectVacationDurationException(int incorrectValue) {
        super("Incorrect vacation duration: " + incorrectValue);
    }

    public IncorrectVacationDurationException(LocalDate firstDate, LocalDate lastDay) {
        super("Incorrect vacation duration: " + firstDate.toString() + " - " + lastDay.toString());
    }

    public IncorrectVacationDurationException() {
    }
}
