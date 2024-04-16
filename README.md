# Online Registration System

This Java application provides an online registration system for managing doctors, patients, and appointments. It allows users to add, view, edit, and remove doctors and patients, as well as book appointments with available doctors.

## Features

- Add, view, edit, and remove doctors
- Add, view, edit, and remove patients
- Book appointments with available doctors
- View patient's appointments

## Technologies Used

- Java
- Java.util package (ArrayList, List, Scanner)
- Java.time package (DayOfWeek, LocalTime)


## Classes and Methods

### Main Class
- `main(String[] args)`: The entry point of the program that displays the main menu and handles user input for various actions.

### DoctorManager Class
- `registerDoctor(Doctor doctor)`: Registers a new doctor.
- `getDoctors()`: Retrieves the list of all doctors.
- `findDoctorById(String id)`: Finds a doctor by their ID.
- `findDoctorByName(String name)`: Finds a doctor by their name.
- `removeDoctor(String name)`: Removes a doctor by their name.

### PatientManager Class
- `registerPatient(Patient patient)`: Registers a new patient.
- `getPatients()`: Retrieves the list of all patients.
- `findPatientByName(String name)`: Finds a patient by their name.
- `findPatientByNameAndId(String name, String id)`: Finds a patient by their name and ID.
- `removePatient(String name)`: Removes a patient by their name.

### AppointmentManager Class
- `generateAvailableTimeSlots(Doctor doctor, Schedule schedule)`: Generates available time slots for booking appointments.
- `isTimeSlotOccupied(Doctor doctor, String time)`: Checks if a specific time slot is occupied.
- `isTimeSlotBooked(String time, Patient patient)`: Checks if a specific time slot is booked by a patient.
- `bookAppointment(Doctor doctor, Patient patient, String time)`: Books an appointment for a patient with a doctor at a specified time.

### Logger Class
- `logInfo(String message)`: Logs informational messages.
- `logError(String message)`: Logs error messages.
- `logCustom(String message)`: Logs custom messages.

### Schedule Class
- `addSlot(DayOfWeek day, LocalTime startTime, LocalTime endTime)`: Adds a time slot to the schedule.
- `hasSlot(DayOfWeek day)`: Checks if the schedule has a time slot for a specific day.
- `getAvailableSlots()`: Retrieves the list of available time slots in the schedule.

### Abstract Class: Person
- `compareTo(Person other)`: Compares this person with another person based on their names.
- `equals(Object o)`: Indicates whether some other object is "equal to" this one.
- `hashCode()`: Returns a hash code value for the object.

### Interface: PersonFactory
- `createPersonDoc(String id, String name, String specialization, Schedule schedule)`: Creates a new doctor object.

### Interface: PatientBuilder
- `setPhoneNumber(String phoneNumber)`: Sets the phone number of the patient.
- `setId(String id)`: Sets the ID of the patient.
- `setName(String name)`: Sets the name of the patient.
- `build()`: Constructs and returns the patient object.

### Abstract Class: Marker
- Marker interface indicating that a class is a doctor.

## Design Patterns

### Creation Patterns
1. Factory Method Pattern: `PersonFactory` interface and `DoctorFactory` class implement the factory method pattern for creating doctor objects.
2. Builder Pattern: `PatientBuilder` interface and `PatientBuilderImpl` class implement the builder pattern for constructing patient objects.

### Structural Patterns
1. Marker Interface Pattern: `Marker` interface acts as a marker for classes implementing doctor functionalities.

## How to Use

1. Clone or download the project. (https://github.com/KavtsevichNikita/Hospital-Online-Reservation.git)
2. Open the project in your preferred Java IDE.
3. Run the `Main` class.
4. Follow the instructions in the console to navigate through the menu and perform actions.

## Example Usage

1. To add a doctor:
   - Choose option `1` from the menu.
   - Enter the doctor's details as prompted.
   - Follow the instructions to add the doctor's schedule.

2. To book an appointment:
   - Choose option `10` from the menu.
   - Enter your name.
   - Choose the doctor's specialization.
   - Select a doctor from the available list.
   - Choose an available time slot for the appointment.

3. To view patient appointments:
   - Choose option `9` from the menu.
   - Enter the patient's name and ID.

![menu](https://github.com/KavtsevichNikita/Hospital-Online-Reservation/assets/77801467/b2db1af9-e29b-4c78-a76a-2a52f0fb7e32)

## Notes

- Ensure correct input format for times (HH:MM).
- Follow the instructions carefully to navigate through the menu and perform actions effectively.
