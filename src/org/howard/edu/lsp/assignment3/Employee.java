package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

/**
 * Represents a single employee payroll record as it moves through the
 * ETL pipeline. Employee is a plain data holder: it knows how to store
 * and expose its own fields, and how to render itself as a CSV row, but
 * it does not know how pay is calculated or how records are read/written.
 * That separation keeps this class focused on a single responsibility:
 * representing employee data.
 */
public class Employee {

    private final int employeeId;
    private final String name;
    private final String department;
    private final double hoursWorked;
    private final double hourlyRate;

    private BigDecimal grossPay;
    private String payLevel;
    private String employmentStatus;

    public Employee(int employeeId, String name, String department,
                     double hoursWorked, double hourlyRate) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public BigDecimal getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(BigDecimal grossPay) {
        this.grossPay = grossPay;
    }

    public String getPayLevel() {
        return payLevel;
    }

    public void setPayLevel(String payLevel) {
        this.payLevel = payLevel;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    /**
     * Renders this employee as a single CSV row matching the required
     * output column order. Formatting (two decimal places) is applied
     * only here, at output time, never during the payroll calculation.
     */
    public String toCsvRow() {
        return employeeId + ","
                + name + ","
                + department + ","
                + String.format("%.2f", hoursWorked) + ","
                + String.format("%.2f", hourlyRate) + ","
                + String.format("%.2f", grossPay) + ","
                + payLevel + ","
                + employmentStatus;
    }
}
