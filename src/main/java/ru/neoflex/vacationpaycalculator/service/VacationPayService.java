package ru.neoflex.vacationpaycalculator.service;

import ru.neoflex.vacationpaycalculator.exceptions.IncorrectUserDataException;

import java.time.LocalDate;

public interface VacationPayService {

    Double calculateVacationPay(Double salary, Integer duration) throws IncorrectUserDataException;

    Double calculateVacationPay(Double salary, LocalDate firstDay, LocalDate lastDay) throws IncorrectUserDataException;
}
