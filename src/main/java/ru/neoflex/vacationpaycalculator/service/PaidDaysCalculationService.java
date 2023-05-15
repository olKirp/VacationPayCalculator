package ru.neoflex.vacationpaycalculator.service;

import java.time.LocalDate;

public interface PaidDaysCalculationService {

    int getNumOfPaidDays(LocalDate firstDay, LocalDate lastDay);

}
