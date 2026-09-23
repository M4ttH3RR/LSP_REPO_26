package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for the Load step of the pipeline: writing a list of
 * transformed Employee records to the output CSV file, including the
 * required header row. This class knows only about output formatting
 * and file writing, not about how employees were read or how their pay
 * was calculated.
 */
public class EmployeeCsvWriter {

    private static final String HEADER =
            "EmployeeID,Name,Department,HoursWorked,HourlyRate,GrossPay,PayLevel,EmploymentStatus";

    private final String outputPath;

    public EmployeeCsvWriter(String outputPath) {
        this.outputPath = outputPath;
    }

    public void writeEmployees(List<Employee> employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(HEADER);
            writer.newLine();

            for (Employee employee : employees) {
                writer.write(employee.toCsvRow());
                writer.newLine();
            }
        }
    }
}
