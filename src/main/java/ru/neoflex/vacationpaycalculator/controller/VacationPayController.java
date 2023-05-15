package ru.neoflex.vacationpaycalculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.neoflex.vacationpaycalculator.exceptions.IncorrectUserDataException;
import ru.neoflex.vacationpaycalculator.service.VacationPayServiceImpl;

import java.time.LocalDate;

@RestController
public class VacationPayController {

    VacationPayServiceImpl vacationPayService;

    private final String usageTip = "Incorrect arguments." +
            "\nUsage:" +
            "\n1.calculate?salary=xxxxx&firstDay=ddmmyyyy&lastDay=ddmmyyyy" +
            "\n2.calculate?salary=xxxxx&duration=xx";

    @Autowired
    public VacationPayController(VacationPayServiceImpl vacationPayService) {
        this.vacationPayService = vacationPayService;
    }

    @GetMapping("calculate")
    public String getAllAccommodation(@RequestParam Double salary,
                                      @RequestParam(required = false) Integer duration,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate firstDay,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate lastDay) {
        Double vacationPay;
        try {
            if (firstDay != null && lastDay != null) {
                vacationPay = vacationPayService.calculateVacationPay(salary, firstDay, lastDay);
            } else if (duration != null) {
                vacationPay = vacationPayService.calculateVacationPay(salary, duration);
            } else {
                return usageTip;
            }
        } catch (IncorrectUserDataException e) {
            return e.getMessage();
        }
        return "Vacation pay: " + vacationPay;
    }


    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public String handleException() {
        return usageTip;
    }

}
