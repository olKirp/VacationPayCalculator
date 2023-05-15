package ru.neoflex.vacationpaycalculator.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectSalaryException;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectVacationDurationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class VacationPayServiceTest {

    VacationPayService vacationPayService;

    @Autowired
    public VacationPayServiceTest(VacationPayService vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @Test
    void givenCorrectData_whenCalculateOrdinaryVacationPay_thenReturnVacationPay() {
        assertEquals(4453.92d, vacationPayService.calculateVacationPay(30000d, 5));
    }

    @Test
    void givenNoSalary_whenCalculateOrdinaryVacationPay_thenReturnZero() {
        assertEquals(0d, vacationPayService.calculateVacationPay(0d, 5));
    }

    @Test
    void givenZeroDays_whenCalculateOrdinaryVacationPay_thenReturnZero() {
        assertEquals(0d, vacationPayService.calculateVacationPay(30000d, 0));
    }

    @Test
    void givenIncorrectDuration_whenCalculateOrdinaryVacationPay_thenThrowIncorrectDurationException() {
        assertThrows(IncorrectVacationDurationException.class, () -> {
            vacationPayService.calculateVacationPay(30000d, -1);
        });
    }

    @Test
    void givenIncorrectSalary_whenCalculateOrdinaryVacationPay_thenThrowIncorrectSalaryException() {
        assertThrows(IncorrectSalaryException.class, () -> {
            vacationPayService.calculateVacationPay(-30000d, 5);
        });
    }

    @Test
    void givenPeriodWithHolidays_whenCalculateDatedVacationPay_thenThrowIncorrectSalaryException() {
        LocalDate firstDay = LocalDate.of(2023, 5, 1);
        LocalDate lastDay = LocalDate.of(2023, 5, 10);

        assertEquals(7126.28d, vacationPayService.calculateVacationPay(30000d, firstDay, lastDay));
    }

    @Test
    void givenPeriodWithNoPaidDays_whenCalculateDatedVacationPay_thenThrowIncorrectSalaryException() {
        LocalDate firstDay = LocalDate.of(2023, 1, 2);
        LocalDate lastDay = LocalDate.of(2023, 1, 5);

        assertEquals(0d, vacationPayService.calculateVacationPay(30000d, firstDay, lastDay));
    }

    @Test
    void givenPeriodWithNoHolidays_whenCalculateDatedVacationPay_thenThrowIncorrectSalaryException() {
        LocalDate firstDay = LocalDate.of(2023, 1, 9);
        LocalDate lastDay = LocalDate.of(2023, 1, 15);

        assertEquals(6235.49d, vacationPayService.calculateVacationPay(30000d, firstDay, lastDay));
    }

    @Test
    void givenIncorrectDuration_whenCalculateDatedVacationPay_thenThrowIncorrectSalaryException() {
        LocalDate firstDay = LocalDate.of(2023, 5, 5);
        LocalDate lastDay = LocalDate.of(2023, 1, 1);

        assertThrows(IncorrectVacationDurationException.class, () -> {
            vacationPayService.calculateVacationPay(30000d, firstDay, lastDay);
        });
    }

    @Test
    void givenIncorrectSalary_whenCalculateDatedVacationPay_thenThrowIncorrectSalaryException() {
        LocalDate firstDay = LocalDate.of(2023, 2, 1);
        LocalDate lastDay = LocalDate.of(2023, 2, 10);

        assertThrows(IncorrectSalaryException.class, () -> {
            vacationPayService.calculateVacationPay(-30000d, firstDay, lastDay);
        });
    }
}