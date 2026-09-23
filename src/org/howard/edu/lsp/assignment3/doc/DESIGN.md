# Design Discussion — Assignment 3 vs. Assignment 2

## 1. How was the Assignment #2 solution organized?
Assignment #2 was implemented as a single class, `ETLPipeline`, containing
one `main` method plus a small set of private static helper methods
(`parseAndValidate`, `transform`) and a private static inner class,
`Employee`, used only as a plain data holder. All extraction (file
reading, parsing, validation), transformation (pay calculation, bonus,
rounding, classification), and loading (writing the output CSV and
printing the run summary) lived inside that one class. There was no
meaningful separation between "how a row is read," "how pay is
calculated," and "how a row is written" — everything was one procedural
flow from top to bottom of `main`.

## 2. What design changes were made for Assignment #3?
The single class was split into five classes, each with one clear job:

- **`Employee`** — a data model class that stores an employee's fields
  and knows how to render itself as an output CSV row. It has no
  knowledge of file I/O or payroll rules.
- **`EmployeeCsvReader`** — owns the Extract step: reading the input
  file, splitting/validating rows, and building `Employee` objects. It
  tracks and exposes `rowsRead` and `rowsSkipped`.
- **`PayCalculator`** — owns the Transform step's business rules:
  overtime, the IT bonus, rounding, pay-level classification, and
  employment-status classification.
- **`EmployeeCsvWriter`** — owns the Load step: writing the header and
  each employee's row to the output file.
- **`ETLPipeline`** — now a thin orchestrator. Its `main` method creates
  one instance of each collaborator, passes data between them in
  sequence, and prints the run summary. It contains no parsing,
  calculation, or formatting logic itself.

## 3. What classes/abstractions were introduced, and why?
`EmployeeCsvReader`, `PayCalculator`, and `EmployeeCsvWriter` are new.
Each represents a distinct responsibility that Assignment #2 handled
with static helper methods buried inside `ETLPipeline`. Pulling them out
into their own classes makes each concern independently readable,
testable, and changeable — for example, the payroll rules in
`PayCalculator` (overtime multiplier, IT bonus, pay-level thresholds)
can be reviewed or modified without touching any file-reading or
file-writing code. `Employee` was promoted from a private nested class
to a full top-level class, since it represents the central abstraction
the whole pipeline operates on, not an implementation detail of
`ETLPipeline` alone.

## 4. How were responsibilities divided differently?
In Assignment #2, `ETLPipeline` was responsible for *everything*:
reading, validating, calculating, formatting, writing, and reporting.
In Assignment #3, responsibility is divided along the natural ETL
boundaries — each stage of Extract, Transform, and Load has its own
class, and `Employee` is a passive data object that collaborates with
all three without knowing about any of them directly. `ETLPipeline`'s
only responsibility now is coordination: deciding the order in which
the other objects are used.

## 5. Why is the Assignment #3 design an improvement?
The redesign follows the single-responsibility principle: each class
has one reason to change. If the input file format changed, only
`EmployeeCsvReader` would need editing. If the payroll policy changed
(e.g., a different overtime multiplier), only `PayCalculator` would need
editing. If the output format changed, only `EmployeeCsvWriter` and
`Employee.toCsvRow()` would need editing. In Assignment #2, all of these
concerns were tangled together in one class, so any change risked
affecting unrelated logic. The new design also makes the pipeline easier
to read at a glance: `ETLPipeline.main` now reads as a short, high-level
description of the ETL process (read → transform each → write → report)
rather than a long block of mixed-purpose code.

## AI / Internet Resource Disclosure
AI assistance (Claude, by Anthropic) was used during this assignment, building on the
already-completed Assignment #2 program. 

Internet Resources: 
https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html
https://docs.oracle.com/javase/tutorial/java/concepts/


Transcript link: [(https://claude.ai/share/02f49cf8-9223-4dc1-b752-c4bf49648602)]


