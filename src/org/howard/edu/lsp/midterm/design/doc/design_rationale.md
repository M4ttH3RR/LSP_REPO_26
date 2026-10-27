Design Rationale: Evolving Requirements
How the design accommodates the changes

Storage (file or database). RegistrationService depends only on the RegistrationRepository interface, never on a concrete storage class. FileRegistrationRepository already exists. To support a database I add one new class, DatabaseRegistrationRepository, which implements the same interface. RegistrationService, Registration, and TuitionCalculator do not change. The deployment environment decides which implementation is supplied to RegistrationService when it is created.

Confirmations (email or text). The same approach applies. RegistrationService depends on the NotificationSender interface, and EmailNotificationSender is one implementation. I add SmsNotificationSender as a second implementation. No existing class changes except Student, which gains a phone number (an additive change, since text messages need one). RegistrationService is unaffected.

Changes to the CRC cards
Add DatabaseRegistrationRepository: save registrations to a database.
Add SmsNotificationSender: send a confirmation by text message.
Add a phone number responsibility to Student: store and provide a contact phone number.
Add one responsibility to RegistrationService: it receives its repository and sender from outside rather than creating them.
Revised and new cards
Student (revised)

Responsibilities

Store and protect student id, name, and email
Store and provide a contact phone number
Provide student details to other classes

Collaborators

None
RegistrationService (revised)

Responsibilities

Coordinate the registration workflow (validate, calculate tuition, create the Registration, save it, notify, log)
Receive its repository and notification sender from outside rather than creating them
Report failure if any required step fails

Collaborators

Student
Course
Registration
TuitionCalculator
RegistrationRepository
NotificationSender
RegistrationLogger
DatabaseRegistrationRepository (new)

Responsibilities

Save registrations to a database in the row format it owns

Collaborators

Registration
SmsNotificationSender (new)

Responsibilities

Send a confirmation by text message

Collaborators

Student
Registration
Key ownership decision

RegistrationService owns the workflow, but each RegistrationRepository implementation owns how a Registration is turned into stored data (a file line versus a database row). I put this in the repository rather than in Registration or the service for these reasons:

Cohesion: Registration should only represent registration data. If it knew how to produce a file line or a SQL row, it would depend on storage details.
Change isolation: a new storage format changes only one class.
Consistency with the RegistrationService card in design.md: this keeps RegistrationService a pure coordinator with no knowledge of formats, matching its card, and it follows Riel's heuristic that a class should capture one key abstraction.

A similar choice applies to the messages: each NotificationSender builds the wording and delivery for its channel, so the service just says "notify this student about this registration."