package ru.neoflex.vacationpaycalculator.service;

import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectVacationDurationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaidDaysCalculationServiceImpl implements PaidDaysCalculationService {

    private static final List<LocalDate> holidays = new ArrayList<>();

    public PaidDaysCalculationServiceImpl() {
        for (int day = 1; day <= 8; day++) {
            holidays.add(LocalDate.of(2000, 1, day));
        }
        holidays.add(LocalDate.of(2000, 2, 23));
        holidays.add(LocalDate.of(2000, 3, 8));
        holidays.add(LocalDate.of(2000, 5, 1));
        holidays.add(LocalDate.of(2000, 5, 9));
        holidays.add(LocalDate.of(2000, 6, 12));
        holidays.add(LocalDate.of(2000, 11, 4));
    }

    public int getNumOfPaidDays(LocalDate firstDay, LocalDate lastDay) {
        if (!firstDay.isBefore(lastDay) && !firstDay.isEqual(lastDay)) {
            throw new IncorrectVacationDurationException(firstDay, lastDay);
        }
        int numOfPaidDays = 0;
        for (LocalDate date = firstDay; date.isBefore(lastDay) || date.isEqual(lastDay); date = date.plusDays(1)) {
            if (!isHoliday(date)) {
                numOfPaidDays++;
            }
        }
        return numOfPaidDays;
    }

    private boolean isHoliday(LocalDate finalDate) {
        return holidays.stream().anyMatch(holiday -> holiday.getDayOfMonth() == finalDate.getDayOfMonth() && holiday.getMonthValue() == finalDate.getMonthValue());
    }
}