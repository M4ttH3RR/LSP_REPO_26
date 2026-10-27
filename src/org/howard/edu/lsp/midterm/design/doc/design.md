Improved Design: CRC Cards
Student

Responsibilities

Store and protect student id, name, and email
Provide student details to other classes

Collaborators

None
Course

Responsibilities

Store and protect course code and credit hours
Provide course details to other classes

Collaborators

None
Registration

Responsibilities

Represent one student's registration for one course
Hold the calculated tuition and registration time
Provide registration details

Collaborators

Student
Course
TuitionCalculator

Responsibilities

Own the tuition rule (rate per credit hour and heavy-load discount)
Calculate tuition for a given course load

Collaborators

Course
RegistrationService

Responsibilities

Coordinate the registration workflow (validate, calculate tuition, create the Registration, save it, notify, log)
Report failure if any required step fails

Collaborators

Student
Course
Registration
TuitionCalculator
RegistrationRepository
NotificationSender
RegistrationLogger
RegistrationRepository (interface)

Responsibilities

Define how registrations are saved

Collaborators

Registration
FileRegistrationRepository

Responsibilities

Save registrations to a local file in the file format it owns

Collaborators

Registration
NotificationSender (interface)

Responsibilities

Define how a confirmation is sent to a student

Collaborators

Student
Registration
EmailNotificationSender

Responsibilities

Send a confirmation by email

Collaborators

Student
Registration
RegistrationLogger

Responsibilities

Record that a registration was processed, including the registration time held by Registration

Collaborators

Registration

Note: RegistrationLogger is a concrete class because logging is not one of the parts expected to vary. If that changed, it could be placed behind an interface in the same way as the repository and notification sender.