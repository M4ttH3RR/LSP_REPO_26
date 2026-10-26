package org.howard.edu.lsp.midterm.inheritance;

/**
 * Represents a contractor, a type of {@link Employee} who is paid for the
 * hours worked at a premium. A contractor's pay is the regular hourly pay
 * increased by a 15% premium.
 *
 * @author Matthew Herriman
 */
public class Contractor extends Employee {
    /** Factor applied to regular pay to give contractors a 15% premium. */
    private static final double PREMIUM_MULTIPLIER = 1.15;

    /**
     * Creates a new Contractor.
     *
     * @param hoursWorked the number of hours the contractor worked
     * @param hourlyRate  the contractor's base pay per hour
     */
    public Contractor(double hoursWorked, double hourlyRate) {
        super(hoursWorked, hourlyRate);
    }

    /**
     * Calculates the contractor's pay: hours worked times the hourly rate,
     * multiplied by the 15% premium.
     *
     * @return the total pay including the premium
     */
    @Override
    public double calculatePay() {
        return getHoursWorked() * getHourlyRate() * PREMIUM_MULTIPLIER;
    }
}