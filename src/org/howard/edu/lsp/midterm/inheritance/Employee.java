package org.howard.edu.lsp.midterm.inheritance;

/**
 * Abstract base class for all employee types. Holds the data common to every
 * employee and requires each subclass to define its own pay rule.
 *
 * @author Student
 */
public abstract class Employee {
    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Creates an employee.
     *
     * @param hoursWorked hours worked in the week (nonnegative)
     * @param hourlyRate  base pay per hour (nonnegative)
     */
    protected Employee(double hoursWorked, double hourlyRate) {
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    /**
     * Returns the hours worked.
     *
     * @return hours worked
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    /**
     * Returns the hourly rate.
     *
     * @return hourly rate
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Calculates this employee's pay according to their employment type.
     *
     * @return the calculated pay
     */
    public abstract double calculatePay();
}