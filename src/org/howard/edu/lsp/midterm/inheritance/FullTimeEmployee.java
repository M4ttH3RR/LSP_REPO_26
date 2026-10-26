package org.howard.edu.lsp.midterm.inheritance;

/**
 * A full-time employee. Hours beyond 40 per week are paid at 1.5 times the
 * hourly rate.
 *
 * @author Matthew Herriman
 */
public class FullTimeEmployee extends Employee {
    private static final double OVERTIME_THRESHOLD = 40.0;
    private static final double OVERTIME_MULTIPLIER = 1.5;

    /**
     * Creates a full-time employee.
     *
     * @param hoursWorked hours worked in the week
     * @param hourlyRate  base pay per hour
     */
    public FullTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates pay with overtime for hours beyond 40.
     *
     * @return regular pay plus overtime pay
     */
    @Override
    public double calculatePay() {
        double hours = getHoursWorked();
        double rate = getHourlyRate();
        if (hours <= OVERTIME_THRESHOLD) {
            return hours * rate;
        }
        double overtimeHours = hours - OVERTIME_THRESHOLD;
        return OVERTIME_THRESHOLD * rate + overtimeHours * rate * OVERTIME_MULTIPLIER;
    }
}