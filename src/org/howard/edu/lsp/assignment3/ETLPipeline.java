package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/**
 * Orchestrates the ETL pipeline: Extract (EmployeeCsvReader), Transform
 * (PayCalculator), and Load (EmployeeCsvWriter). This class is
 * intentionally thin — it delegates the real work to collaborator
 * objects and is responsible only for wiring the steps together and
 * reporting the run summary.
 */
public class ETLPipeline {

    private static final String INPUT_PATH = "data/employees.csv";
    private static final String OUTPUT_PATH = "data/transformed_employees.csv";

    public static void main(String[] args) {
        EmployeeCsvReader reader = new EmployeeCsvReader(INPUT_PATH);
        PayCalculator payCalculator = new PayCalculator();
        EmployeeCsvWriter writer = new EmployeeCsvWriter(OUTPUT_PATH);

        List<Employee> employees;
        try {
            employees = reader.readEmployees();
        } catch (IOException e) {
            System.err.println("Error reading input file: " + INPUT_PATH);
            System.err.println(e.getMessage());
            return;
        }

        for (Employee employee : employees) {
            payCalculator.apply(employee);
        }

        try {
            writer.writeEmployees(employees);
        } catch (IOException e) {
            System.err.println("Error writing output file: " + OUTPUT_PATH);
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Rows read: " + reader.getRowsRead());
        System.out.println("Rows transformed: " + employees.size());
        System.out.println("Rows skipped: " + reader.getRowsSkipped());
        System.out.println("Output file: " + OUTPUT_PATH);
    }
}
