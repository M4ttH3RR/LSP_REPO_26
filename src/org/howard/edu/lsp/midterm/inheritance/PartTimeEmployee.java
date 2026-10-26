package org.howard.edu.lsp.midterm.inheritance;

/**
 * A part-time employee, paid straight hourly pay with no overtime premium.
 *
 * @author Matthew Herriman
 */
public class PartTimeEmployee extends Employee {
    /**
     * Creates a part-time employee.
     *
     * @param hoursWorked hours worked in the week
     * @param hourlyRate  base pay per hour
     */
    public PartTimeEmployee(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates straight hourly pay.
     *
     * @return hours worked times hourly rate
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate();
    }
}