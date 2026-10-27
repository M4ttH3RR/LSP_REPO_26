Evaluation of the CourseRegistration Design

CourseRegistration works as a single procedure, but its object-oriented design has several problems.

1. Public data (Riel 2.1)

studentId, studentName, email, courseCode, and creditHours are all public fields. Riel's heuristic 2.1 says a class's data should be hidden within the class. Any other code can set creditHours to a negative number or email to garbage, so the class cannot protect its own correctness, and any change to a field's type breaks every user of the class.

2. One class with many abstractions (Riel 2.8, 3.2 "god class")

Riel's heuristic 2.8 says a class should capture one and only one key abstraction. registerStudent() does at least seven jobs: it models a student, models a course, calculates tuition, prints confirmations, writes to a file, sends email, and logs. This is the "god class" Riel warns about in 3.2: it concentrates the system's intelligence in one place instead of distributing it (heuristic 3.1). A change to tuition rules, the file format, the email mechanism, or logging all force edits to this one class, so it has many reasons to change and is hard to test or extend.

3. Related data and behavior not kept together (Riel 2.9)

Student identity data (id, name, email) is mixed with course data (code, credit hours), while the tuition rule that depends on credit hours sits inside a method that also does I/O. Heuristic 2.9 says related data and behavior belong together. A Student should own student data, a Course should own course data, and tuition rules should live with whichever class owns that policy.

4. Hard-wired behavior hurts extensibility

The file name registrations.txt, the file format, the console "email", and the logging are all embedded in the method. Switching to a database, a real email service, or text messages means editing and retesting this class (an open/closed problem). Nothing can be substituted or mocked, so unit testing is nearly impossible without touching the file system and console.

5. Correctness and robustness problems
Magic numbers: 750.0, 12, and 0.90 are unexplained constants buried in code.
No validation: nothing checks for negative credit hours, a null email, or an unknown course.
Money as double: floating-point error can produce wrong tuition values; BigDecimal or integer cents is safer.
Swallowed errors: catch (Exception e) only prints a stack trace, so registration continues and "succeeds" even if saving failed. The "email sent" and "log" lines then report something false.
Resource handling: writer.close() is skipped if write throws, leaking the file handle; try-with-resources should be used.
Weak model: one object holds one student and one course, so it is unclear how a student registers for several courses, and credit hours (a property of the course) are stored on the registration.
Fragile data format: a comma-separated line built by string concatenation breaks if a value contains a comma.
Mixed concerns in output: the class prints to System.out directly, so it cannot be reused in a GUI or web setting.
Summary

The class violates data hiding (2.1), the single-abstraction rule (2.8), the avoidance of god classes (3.2), uniform distribution of intelligence (3.1), and keeping related data and behavior together (2.9). The result is poor cohesion, high coupling to I/O details, and code that is difficult to change or test. The redesign in design.md splits these responsibilities across focused classes.