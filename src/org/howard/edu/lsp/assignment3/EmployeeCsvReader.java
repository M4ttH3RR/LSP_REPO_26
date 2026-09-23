package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for the Extract step of the pipeline: reading raw CSV text
 * from disk, splitting rows into fields, and validating/parsing each row
 * into an Employee object. This class owns all knowledge of the input
 * file format and validation rules, keeping that concern separate from
 * pay calculation (PayCalculator) and output writing (EmployeeCsvWriter).
 */
public class EmployeeCsvReader {

    private final String inputPath;
    private int rowsRead;
    private int rowsSkipped;

    public EmployeeCsvReader(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Reads and validates every data row in the input file, returning the
     * list of successfully parsed Employee objects. Skipped rows are not
     * included in the returned list, but are counted internally and can
     * be retrieved with getRowsSkipped().
     */
    public List<Employee> readEmployees() throws IOException {
        List<Employee> employees = new ArrayList<>();
        rowsRead = 0;
        rowsSkipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                rowsRead++;

                Employee employee = parseAndValidate(line);
                if (employee == null) {
                    rowsSkipped++;
                } else {
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    public int getRowsRead() {
        return rowsRead;
    }

    public int getRowsSkipped() {
        return rowsSkipped;
    }

    /**
     * Parses a single raw CSV line into an Employee, applying field
     * normalization (trimming, name uppercasing) and validation rules.
     * Returns null if the row should be skipped.
     */
    private Employee parseAndValidate(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] fields = line.split(",", -1);
        if (fields.length != 5) {
            return null;
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

        return new Employee(employeeId, name, rawDept, hoursWorked, hourlyRate);
    }
}
