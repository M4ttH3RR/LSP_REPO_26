package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Responsible for the Transform step's business rules: overtime pay,
 * the IT department bonus, rounding, pay level classification, and
 * employment status classification. Isolating these rules in their own
 * class means the payroll policy can be read, tested, and changed
 * without touching how records are parsed or written.
 */
public class PayCalculator {

    private static final double OVERTIME_THRESHOLD = 40.00;
    private static final double OVERTIME_MULTIPLIER = 1.5;
    private static final double IT_BONUS_MULTIPLIER = 1.05;
    private static final String IT_DEPARTMENT = "IT";

    private static final double FULL_TIME_THRESHOLD = 30.00;

    private static final double LOW_MAX = 500.00;
    private static final double STANDARD_MAX = 1000.00;
    private static final double HIGH_MAX = 2000.00;

    /**
     * Applies all Transform-step calculations to the given employee,
     * setting its grossPay, payLevel, and employmentStatus fields.
     */
    public void apply(Employee employee) {
        BigDecimal grossPay = calculateGrossPay(employee);
        employee.setGrossPay(grossPay);
        employee.setPayLevel(determinePayLevel(grossPay));
        employee.setEmploymentStatus(determineEmploymentStatus(employee.getHoursWorked()));
    }

    private BigDecimal calculateGrossPay(Employee employee) {
        double hours = employee.getHoursWorked();
        double rate = employee.getHourlyRate();

        double basePay;
        if (hours <= OVERTIME_THRESHOLD) {
            basePay = hours * rate;
        } else {
            double overtimeHours = hours - OVERTIME_THRESHOLD;
            basePay = (OVERTIME_THRESHOLD * rate) + (overtimeHours * rate * OVERTIME_MULTIPLIER);
        }

        double grossPay = basePay;
        if (IT_DEPARTMENT.equals(employee.getDepartment())) {
            grossPay = grossPay * IT_BONUS_MULTIPLIER;
        }

        // Round to exactly two decimal places using round-half-up.
        return new BigDecimal(Double.toString(grossPay)).setScale(2, RoundingMode.HALF_UP);
    }

    private String determinePayLevel(BigDecimal grossPay) {
        double value = grossPay.doubleValue();
        if (value < LOW_MAX) {
            return "Low";
        } else if (value < STANDARD_MAX) {
            return "Standard";
        } else if (value < HIGH_MAX) {
            return "High";
        } else {
            return "Executive";
        }
    }

    private String determineEmploymentStatus(double hoursWorked) {
        return hoursWorked < FULL_TIME_THRESHOLD ? "Part-Time" : "Full-Time";
    }
}
