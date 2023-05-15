package ru.neoflex.vacationpaycalculator.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectVacationDurationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class PaidDaysCalculationServiceTest {

    PaidDaysCalculationService paidDaysCalculationService;

    @Autowired
    public PaidDaysCalculationServiceTest(PaidDaysCalculationService paidDaysCalculationService) {
        this.paidDaysCalculationService = paidDaysCalculationService;
    }

    @Test
    void givenPeriodWithoutHolidays_whenCountPaidDays_thenReturnNum() {
        assertEquals(5, paidDaysCalculationService.getNumOfPaidDays(LocalDate.of(2023, 10, 9), LocalDate.of(2023, 10, 13)));
    }

    @Test
    void givenPeriodWithHolidays_whenCountPaidDays_thenReturnNum() {
        assertEquals(5, paidDaysCalculationService.getNumOfPaidDays(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 6)));
    }

    @Test
    void givenIncorrectDuration_whenCountPaidDays_thenThrowIncorrectDurationException() {
        assertThrows(IncorrectVacationDurationException.class, () -> {
            paidDaysCalculationService.getNumOfPaidDays(LocalDate.of(2023, 5, 7), LocalDate.of(2023, 5, 6));
        });
    }
}