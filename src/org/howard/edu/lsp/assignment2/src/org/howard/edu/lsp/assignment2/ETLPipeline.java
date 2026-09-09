package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * ETLPipeline
 *
 * Reads employee payroll data from data/employees.csv, applies a series of
 * transformations (normalization, validation, pay calculation, bonus,
 * rounding, pay level, employment status), and writes the results to
 * data/transformed_employees.csv. Prints a run summary to the console.
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    public static void main(String[] args) {
        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus")
                .append(System.lineSeparator());

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    // Skip the header row; it is not counted as read/transformed/skipped.
                    isHeader = false;
                    continue;
                }

                rowsRead++;

                Employee employee = parseAndValidate(line);
                if (employee == null) {
                    rowsSkipped++;
                    continue;
                }

                transform(employee);
                outputBuilder.append(employee.toOutputRow()).append(System.lineSeparator());
                rowsTransformed++;
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + INPUT_PATH);
            System.err.println(e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH))) {
            writer.write(outputBuilder.toString());
        } catch (IOException e) {
            System.err.println("Error writing output file: " + OUTPUT_PATH);
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Rows skipped: " + rowsSkipped);
        System.out.println("Output file: " + OUTPUT_PATH);
    }

    /**
     * Parses a raw CSV line into an Employee, applying field normalization
     * (trimming, name uppercasing) and validation rules. Returns null if the
     * row should be skipped.
     */
    private static Employee parseAndValidate(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null; // blank line
        }

        // Fields are guaranteed not to contain embedded commas or quotes.
        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return null; // must contain exactly five comma-separated fields
        }

        String rawId = fields[0].trim();
        String rawName = fields[1].trim();
        String rawDept = fields[2].trim();
        String rawHours = fields[3].trim();
        String rawRate = fields[4].trim();

        int employeeId;
        try {
            employeeId = Integer.parseInt(rawId);
        } catch (NumberFormatException e) {
            return null;
        }

        double hoursWorked;
        double hourlyRate;
        try {
            hoursWorked = Double.parseDouble(rawHours);
            hourlyRate = Double.parseDouble(rawRate);
        } catch (NumberFormatException e) {
            return null;
        }

        if (hoursWorked < 0 || hourlyRate < 0) {
            return null;
        }

        String name = rawName.toUpperCase();

        Employee employee = new Employee();
        employee.employeeId = employeeId;
        employee.name = name;
        employee.department = rawDept;
        employee.hoursWorked = hoursWorked;
        employee.hourlyRate = hourlyRate;
        return employee;
    }

    /**
     * Applies pay calculation, IT bonus, rounding, pay level, and employment
     * status determination to a validated Employee record.
     */
    private static void transform(Employee employee) {
        double hours = employee.hoursWorked;
        double rate = employee.hourlyRate;

        double basePay;
        if (hours <= 40.00) {
            basePay = hours * rate;
        } else {
            double overtimeHours = hours - 40.00;
            basePay = (40.00 * rate) + (overtimeHours * rate * 1.5);
        }

        double grossPay = basePay;
        if ("IT".equals(employee.department)) {
            grossPay = grossPay * 1.05;
        }

        // Round to exactly two decimal places using round-half-up.
        BigDecimal rounded = new BigDecimal(Double.toString(grossPay))
                .setScale(2, RoundingMode.HALF_UP);
        employee.grossPay = rounded;

        double grossPayValue = rounded.doubleValue();
        if (grossPayValue < 500.00) {
            employee.payLevel = "Low";
        } else if (grossPayValue < 1000.00) {
            employee.payLevel = "Standard";
        } else if (grossPayValue < 2000.00) {
            employee.payLevel = "High";
        } else {
            employee.payLevel = "Executive";
        }

        if (hours < 30.00) {
            employee.employmentStatus = "Part-Time";
        } else {
            employee.employmentStatus = "Full-Time";
        }
    }

    /**
     * Simple data holder for an employee record as it moves through the
     * transform stage.
     */
    private static class Employee {
        int employeeId;
        String name;
        String department;
        double hoursWorked;
        double hourlyRate;
        BigDecimal grossPay;
        String payLevel;
        String employmentStatus;

        String toOutputRow() {
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
}
