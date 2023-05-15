package ru.neoflex.vacationpaycalculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectSalaryException;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectUserDataException;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectVacationDurationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class VacationPayServiceImpl implements VacationPayService {

    private final PaidDaysCalculationServiceImpl paidDaysCalculationService;

    private static final double AVERAGE_NUM_OF_DAYS = 29.3d;
    private static final double INCOME_TAX = 0.13d;
    private static final int SCALE = 2;

    @Autowired
    public VacationPayServiceImpl(PaidDaysCalculationServiceImpl paidDaysCalculationService) {
        this.paidDaysCalculationService = paidDaysCalculationService;
    }

    public Double calculateVacationPay(Double salary, Integer duration) throws IncorrectUserDataException {
        if (salary < 0) {
            throw new IncorrectSalaryException(salary);
        }
        if (duration < 0) {
            throw new IncorrectVacationDurationException(duration);
        }

        BigDecimal dailySalary = BigDecimal.valueOf(salary / AVERAGE_NUM_OF_DAYS);
        BigDecimal vacationPay = dailySalary.multiply(BigDecimal.valueOf(duration));
        BigDecimal incomeTax = vacationPay.multiply(BigDecimal.valueOf(INCOME_TAX));

        System.out.println(vacationPay + "-" + incomeTax + "=" + vacationPay.subtract(incomeTax));
        vacationPay = vacationPay.subtract(incomeTax);

        return vacationPay.setScale(SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public Double calculateVacationPay(Double salary, LocalDate firstDay, LocalDate lastDay) throws IncorrectUserDataException {
        int duration = paidDaysCalculationService.getNumOfPaidDays(firstDay,lastDay);

        return calculateVacationPay(salary, duration);
    }


}
