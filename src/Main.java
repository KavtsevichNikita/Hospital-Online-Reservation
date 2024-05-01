import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import Appointment.*;
import Doctor.*;
import HospitalError.HospitalError;
import Patient.*;
import Person.*;
import Schedule.*;

// RECOMMENDED TO READ README.md
// I use Builder, Factory, Facade, Marker( in class Doctor)
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // Facade
    private static HospitalFacade hospital = new HospitalFacade();

    public static void main(String[] args) {
        int choice;

        // Builder
        Patient testPatient = new PatientBuilderImpl().setId("P1").setName("Nikita").setPhoneNumber("11111111").build();
        Patient testPatient2 = new PatientBuilderImpl().setId("P2").setName("Ihor").setPhoneNumber("999999999").build();

        hospital.registerPatient(testPatient);
        hospital.registerPatient(testPatient2);
        viewAllPatients();
        hospital.logInfo("\n");

        // Factory
        PersonFactory doctorFactory = new DoctorFactory();
        Doctor testDoctor = doctorFactory.createPersonDoc("P1", "Nikita", "Surgeon", new Schedule() {{
            addSlot(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(12, 0));
        }});
        Doctor testDoctor2 = doctorFactory.createPersonDoc("P2", "Ihor", "Dentist", new Schedule() {{
            addSlot(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(17, 0));
        }});

        hospital.registerDoctor(testDoctor);
        hospital.registerDoctor(testDoctor2);
        viewAllDoctors();

        ////// Switch menu
        do {
            try {
                hospital.logInfo(
                                "╔══════════════════════════════════════╗\n" +
                                "║                Menu:                 ║\n" +
                                "║                                      ║\n" +
                                "║   0 - Exit the application           ║\n" +
                                "║                                      ║\n" +
                                "║               DOCTORS:               ║\n" +
                                "║   1 - Add a doctor                   ║\n" +
                                "║   2 - View list of all doctors       ║\n" +
                                "║   3 - Edit a doctor                  ║\n" +
                                "║   4 - Remove a doctor                ║\n" +
                                "║                                      ║\n" +
                                "║               PATIENTS:              ║\n" +
                                "║   5 - Add a patient                  ║\n" +
                                "║   6 - View list of all patients      ║\n" +
                                "║   7 - Edit a patient                 ║\n" +
                                "║   8 - Remove a patient               ║\n" +
                                "║   9 - View patient's appointments    ║\n" +
                                "║   10 - Book an appointment           ║\n" +
                                "╚══════════════════════════════════════╝\n" +
                                "Choose an action: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        viewAllDoctors();
                        break;
                    case 3:
                        editDoctor();
                        break;
                    case 4:
                        removeDoctor();
                        break;
                    case 5:
                        addPatient();
                        break;
                    case 6:
                        viewAllPatients();
                        break;
                    case 7:
                        editPatient();
                        break;
                    case 8:
                        removePatient();
                        break;
                    case 9:
                        viewPatientAppointments();
                        break;
                    case 10:
                        bookAppointment();
                        break;
                    case 0:
                        hospital.logInfo("Exiting the application...");
                        break;
                    default:
                        throw new HospitalError("Invalid choice. Please try again.", null);
                }
            } catch (HospitalError e) {
                hospital.logError(e.getMessage());
                scanner.nextLine();
                choice = -1;
            }
        } while (choice != 0);
    }

    // Add new doctor
    private static void addDoctor() {
        try {
            hospital.logInfo("====================  ADD NEW DOCTOR  ====================");

            String id, name, specialization, choice;
            Schedule doctorSchedule = new Schedule();
            boolean addMore = true;
            LocalTime startTime = null;
            LocalTime endTime = null;

            // doctor's id
            do {
                hospital.logCustom("Enter doctor's id: ");
                id = scanner.nextLine();
                if (id.isEmpty()) {
                    hospital.logError("Doctor's id cannot be empty. Please fill in the data.");
                }

                if (hospital.findDoctorById(id)) {
                    hospital.logError("Doctor with such id already exists in the database.");
                    continue;
                }

            } while (id.isEmpty() || hospital.findDoctorById(id));

            // doctor's name
            do {
                hospital.logCustom("Enter doctor's name: ");
                name = scanner.nextLine();
                if (name.isEmpty()) {
                    hospital.logError("Doctor's name cannot be empty. Please fill in the data.");
                }

                if (hospital.findDoctorByName(name) != null) {
                    hospital.logError("This doctor already exists in the database.");
                    continue;
                }

            } while (name.isEmpty() || hospital.findDoctorByName(name) != null);

            // doctor specialization
            do {
                hospital.logCustom("Enter doctor's specialization: ");
                specialization = scanner.nextLine();
                if (specialization.isEmpty()) {
                    hospital.logError("Doctor's specialization cannot be empty. Please fill in the data.");
                }
            } while (specialization.isEmpty());

            hospital.logInfo("------------------------------------------------------");

            do {
                DayOfWeek day = null;
                int dayChoice;
                do {
                    hospital.logCustom("Select day of the week:");
                    hospital.logCustom("1. Monday");
                    hospital.logCustom("2. Tuesday");
                    hospital.logCustom("3. Wednesday");
                    hospital.logCustom("4. Thursday");
                    hospital.logCustom("5. Friday");
                    hospital.logCustom("6. Saturday");
                    hospital.logCustom("7. Sunday");
                    hospital.logCustom("Enter your choice (1-7): ");
                    if (scanner.hasNextInt()) {
                        dayChoice = scanner.nextInt();
                        if (dayChoice >= 1 && dayChoice <= 7) {
                            day = DayOfWeek.of(dayChoice);
                        } else {
                            hospital.logError("Invalid day selection. Please enter a number between 1 and 7.");
                        }
                    } else {
                        hospital.logError("Invalid input. Please enter a number between 1 and 7.");
                        scanner.next();
                    }
                } while (day == null);

                if (doctorSchedule.hasSlot(day)) {
                    hospital.logError("You are already working at this day. Please choose another day.");
                    continue;
                }
                do {
                    hospital.logCustom("Enter start time (HH:MM): ");
                    String startTimeStr = scanner.next();
                    try {
                        startTime = LocalTime.parse(startTimeStr);
                    } catch (Exception e) {
                        hospital.logError("Invalid time format. Please enter time in HH:MM format.");
                    }
                } while (startTime == null);

                do {
                    hospital.logCustom("Enter end time (HH:MM): ");
                    String endTimeStr = scanner.next();
                    try {
                        endTime = LocalTime.parse(endTimeStr);
                        if (endTime.isBefore(startTime)) {
                            hospital.logError("End time cannot be before start time.");
                            endTime = null;
                        }
                    } catch (Exception e) {
                        hospital.logError("Invalid time format. Please enter time in HH:MM format.");
                    }
                } while (endTime == null);

                doctorSchedule.addSlot(day, startTime, endTime);

                do {
                    hospital.logCustom("Do you want to add another schedule? (yes/no): ");
                    choice = scanner.next();
                    if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))) {
                        hospital.logError("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } while (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")));

                addMore = choice.equalsIgnoreCase("yes");
            } while (addMore);

            Doctor doctor = new Doctor(String.valueOf(hospital.getDoctors().size() + 1), name, specialization, doctorSchedule);

            hospital.registerDoctor(doctor);

            hospital.logInfo("Doctor added: " + doctor.getName());
            hospital.logInfo("==================== ADD NEW DOCTOR FINISHED SUCCESSFULLY ====================");
        } catch (Exception e) {
            hospital.logError("An error occurred while adding a doctor: " + e.getMessage());
        }
    }

    // Get all doctors
    private static void viewAllDoctors() {
        hospital.logInfo("==================== DOCTORS LIST ====================");

        if (hospital.getDoctors().isEmpty()) {
            hospital.logError("Doctors not found");
        } else {
            for (Doctor doctor : hospital.getDoctors()) {
                hospital.logInfo("========= Doctor " + doctor.getName() + " =========");
                hospital.logCustom("Name: " + doctor.getName());
                hospital.logCustom("Specialization: " + doctor.getSpecialization());
                hospital.logCustom("Schedule:");
                List<String> availableSlots = doctor.getSchedule().getAvailableSlots();
                if (availableSlots.isEmpty()) {
                    hospital.logError("No available slots");
                } else {
                    for (String slot : availableSlots) {
                        hospital.logCustom(slot);
                    }
                }

                if (doctor instanceof Marker) {  // Marker = true
                    hospital.logInfo("Doctor is marked with @Marker annotation.");
                } else {
                    hospital.logInfo("Doctor is not marked with @Marker annotation.");
                }
            }
        }
    }

    // Remove doctor
    private static void removeDoctor() throws HospitalError {
        hospital.logInfo("====================  REMOVE DOCTOR  ====================");

        List<Doctor> availableDoctors = hospital.getDoctors();

        if (availableDoctors.isEmpty()) {
            throw new HospitalError("Doctors list is empty!", availableDoctors);
        }

        hospital.logCustom("------ Doctors list ------");
        availableDoctors.forEach(doctor -> hospital.logCustom(doctor.getName()));

        hospital.logCustom("Enter doctor's name to remove: ");
        String name = scanner.nextLine();

        hospital.removeDoctor(name);
    }

    // Edit doctor info
    private static void editDoctor() {
        hospital.logInfo("====================  EDIT DOCTOR INFORMATION  ====================");

        List<Doctor> doctors = hospital.getDoctors();
        if (doctors.isEmpty()) {
            hospital.logError("No doctors found.");
            return;
        }

        hospital.logInfo("Select a doctor to edit:");
        for (int i = 0; i < doctors.size(); i++) {
            hospital.logCustom((i + 1) + ". " + doctors.get(i).getName());
        }

        hospital.logCustom("Enter the number of the doctor you want to edit: ");
        int doctorIndex = scanner.nextInt();
        scanner.nextLine();

        if (doctorIndex < 1 || doctorIndex > doctors.size()) {
            hospital.logError("Invalid doctor number.");
            return;
        }

        Doctor doctorToEdit = doctors.get(doctorIndex - 1);

        hospital.logCustom("Current Name: " + doctorToEdit.getName());
        hospital.logCustom("Current Specialization: " + doctorToEdit.getSpecialization());
        hospital.logCustom("Current Schedule: " + doctorToEdit.getSchedule());

        hospital.logCustom("Enter the new name (or leave empty to keep current): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            doctorToEdit.setName(newName);
            hospital.logInfo("Name updated successfully.");
        }

        hospital.logCustom("Enter the new specialization (or leave empty to keep current): ");
        String newSpecialization = scanner.nextLine().trim();
        if (!newSpecialization.isEmpty()) {
            doctorToEdit.setSpecialization(newSpecialization);
            hospital.logInfo("Specialization updated successfully.");
        }

        hospital.logCustom("Would you like to edit the schedule? (yes/no): ");
        String editScheduleChoice = scanner.nextLine().trim();
        if (editScheduleChoice.equalsIgnoreCase("yes")) {
            editDoctorSchedule(doctorToEdit);
        }

        hospital.logInfo("==================== EDIT DOCTOR INFORMATION FINISHED ====================");
    }

    // Edit schedule for doctor
    private static void editDoctorSchedule(Doctor doctorToEdit) {
        hospital.logInfo("====================  EDIT DOCTOR SCHEDULE  ====================");

        Schedule schedule = doctorToEdit.getSchedule();

        hospital.logInfo("Current schedule for Doctor " + doctorToEdit.getName() + ":");
        List<String> availableSlots = schedule.getAvailableSlots();
        if (!availableSlots.isEmpty()) {
            for (String slot : availableSlots) {
                hospital.logCustom(slot);
            }
        } else {
            hospital.logCustom("No available slots for the current schedule.");
            return;
        }

        Schedule newSchedule = new Schedule();
        boolean addMore = true;
        do {
            DayOfWeek day = null;
            int dayChoice;
            do {
                hospital.logCustom("Select day of the week:");
                hospital.logCustom("1. Monday");
                hospital.logCustom("2. Tuesday");
                hospital.logCustom("3. Wednesday");
                hospital.logCustom("4. Thursday");
                hospital.logCustom("5. Friday");
                hospital.logCustom("6. Saturday");
                hospital.logCustom("7. Sunday");
                hospital.logCustom("Enter your choice (1-7): ");
                if (scanner.hasNextInt()) {
                    dayChoice = scanner.nextInt();
                    if (dayChoice >= 1 && dayChoice <= 7) {
                        day = DayOfWeek.of(dayChoice);
                    } else {
                        hospital.logError("Invalid day selection. Please enter a number between 1 and 7.");
                    }
                } else {
                    hospital.logError("Invalid input. Please enter a number between 1 and 7.");
                    scanner.next();
                }
            } while (day == null);

            LocalTime startTime = null;
            do {
                hospital.logCustom("Enter start time (HH:MM): ");
                String startTimeStr = scanner.next();
                try {
                    startTime = LocalTime.parse(startTimeStr);
                } catch (Exception e) {
                    hospital.logError("Invalid time format. Please enter time in HH:MM format.");
                }
            } while (startTime == null);

            LocalTime endTime = null;
            do {
                hospital.logCustom("Enter end time (HH:MM): ");
                String endTimeStr = scanner.next();
                try {
                    endTime = LocalTime.parse(endTimeStr);
                    if (endTime.isBefore(startTime)) {
                        hospital.logError("End time cannot be before start time.");
                        endTime = null;
                    }
                } catch (Exception e) {
                    hospital.logError("Invalid time format. Please enter time in HH:MM format.");
                }
            } while (endTime == null);

            newSchedule.addSlot(day, startTime, endTime);

            String choice;
            do {
                hospital.logCustom("Do you want to add another schedule? (yes/no): ");
                choice = scanner.next();
                if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))) {
                    hospital.logError("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")));

            addMore = choice.equalsIgnoreCase("yes");
        } while (addMore);

        doctorToEdit.setSchedule(newSchedule);
        hospital.logInfo("Doctor's schedule updated successfully.");

        hospital.logInfo("==================== EDIT DOCTOR SCHEDULE FINISHED ====================");
    }

    // Add new patient
    private static void addPatient() {
        hospital.logInfo("====================  ADD NEW PATIENT  ====================");

        String id, name, phoneNumber;

        do {
            hospital.logCustom("Enter patient's name, surname: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                hospital.logError("Patient's name cannot be empty. Please fill in the data.");
                continue;
            }

            if (hospital.findPatientByName(name) != null) {
                hospital.logError("This patient already exists in the database.");
                continue;
            }
        } while (name.isEmpty() || hospital.findPatientByName(name) != null);

        do {
            hospital.logCustom("Enter patient's phone number: ");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.isEmpty()) {
                hospital.logError("Patient's phone number cannot be empty. Please fill in the data.");
            }
        } while (phoneNumber.isEmpty());

        do {
            hospital.logCustom("Enter patient's ID number: ");
            id = scanner.nextLine();
            if (id.isEmpty()) {
                hospital.logError("Patient's ID cannot be empty. Please fill in the data.");
            }
        } while (id.isEmpty());

        Patient patient = new Patient(id, name, phoneNumber);
        hospital.registerPatient(patient);

        hospital.logInfo("Patient added: " + patient.getName());
        hospital.logInfo("==================== ADD NEW PATIENT FINISHED SUCCESSFULLY ====================");
    }

    // Get all patients
    private static void viewAllPatients() {
        hospital.logInfo("==================== PATIENTS LIST ====================");

        if (hospital.getPatients().isEmpty()) {
            hospital.logError("========== Patients not found! ==========");
        } else {
            for (Patient patient : hospital.getPatients()) {
                hospital.logInfo("========= Patient " + patient.getName() + " =========");
                hospital.logCustom("Name: " + patient.getName());
                hospital.logCustom("ID: " + patient.getId());
                hospital.logCustom("Phone: " + patient.getPhoneNumber());
                hospital.logCustom("--------------------------");

                if (patient instanceof Marker) {  // Marker = false
                    hospital.logInfo("Patient is marked with @Marker annotation.");
                } else {
                    hospital.logInfo("Patient is not marked with @Marker annotation.");
                }
            }
        }
    }

    // Remove patient
    private static void removePatient() {
        hospital.logInfo("====================  REMOVE PATIENT  ====================");

        List<Patient> availablePatients = hospital.getPatients();
        if (availablePatients.isEmpty()) {
            hospital.logCustom("Patients list is empty!");
        } else {
            hospital.logCustom("------ Patients list ------");

            for (Patient patient : availablePatients) {
                hospital.logCustom(patient.getName());
            }
            hospital.logCustom("Enter patient's name to remove: ");
            String name = scanner.nextLine();

            hospital.removePatient(name);
        }
    }

    // Edit patient
    private static void editPatient() {
        hospital.logInfo("====================  EDIT PATIENT INFORMATION  ====================");

        List<Patient> patients = hospital.getPatients();

        if (patients.isEmpty()) {
            hospital.logError("No patients found.");
            return;
        }

        hospital.logInfo("Select a patient to edit:");
        for (int i = 0; i < patients.size(); i++) {
            hospital.logCustom((i + 1) + ". " + patients.get(i).getName());
        }

        hospital.logCustom("Enter the number of the patient you want to edit: ");
        int patientIndex = scanner.nextInt();
        scanner.nextLine();

        if (patientIndex < 1 || patientIndex > patients.size()) {
            hospital.logError("Invalid patient number.");
            return;
        }

        Patient patientToEdit = patients.get(patientIndex - 1);

        hospital.logCustom("Current ID: " + patientToEdit.getId());
        hospital.logCustom("Current Name: " + patientToEdit.getName());
        hospital.logCustom("Current Phone Number: " + patientToEdit.getPhoneNumber());

        hospital.logCustom("Enter the new name (or leave empty to keep current): ");
        String newName = scanner.nextLine().trim();

        if (!newName.isEmpty()) {
            patientToEdit.setName(newName);
            hospital.logInfo("Name updated successfully.");
        }

        hospital.logCustom("Enter the new phone number (or leave empty to keep current): ");
        String newPhoneNumber = scanner.nextLine().trim();
        if (!newPhoneNumber.isEmpty()) {
            patientToEdit.setPhoneNumber(newPhoneNumber);
            hospital.logInfo("Phone number updated successfully.");
        }

        hospital.logInfo("==================== EDIT PATIENT INFORMATION FINISHED ====================");
    }

    // View created appointments of patient
    private static void viewPatientAppointments() throws HospitalError {
        hospital.logCustom("Enter patient's name: ");
        String name = scanner.nextLine();

        hospital.logCustom("Enter patient's ID: ");
        String id = scanner.nextLine();

        Patient patient = hospital.findPatientByNameAndId(name, id);

        if (patient != null) {
            List<Appointment> appointments = hospital.getAppointmentsForPatient(patient);

            if (appointments.isEmpty()) {
                hospital.logCustom("================== You have no appointments with doctors. ==================");
            } else {
                hospital.logCustom("Your appointments with doctors:");
                for (Appointment appointment : appointments) {
                    hospital.logCustom("Date and time: " + appointment.getTime() + ", Doctor: " + appointment.getDoctor().getName());
                }
            }
        } else {
            throw new HospitalError("Patient with such name and ID not found.", patient);
        }
    }

    // Book an appoinment
    private static void bookAppointment() {
        hospital.logCustom("Enter your name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            hospital.logError("Name cannot be empty. Please enter your name.");
            bookAppointment();
            return;
        }

        Patient patient = hospital.findPatientByName(name);

        if (patient == null) {
            hospital.logError("Patient with such name not found. Please register.");
            return;
        }

        hospital.logCustom("Enter the doctor's specialization you want to book with: ");
        String specialization = scanner.nextLine();

        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : hospital.getDoctors()) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                availableDoctors.add(doctor);
            }
        }

        if (availableDoctors.isEmpty()) {
            hospital.logCustom("No doctors found with such specialization.");
            return;
        }

        hospital.logInfo("Available doctors:");
        for (Doctor doctor : availableDoctors) {
            hospital.logCustom(doctor.getName());
        }

        hospital.logCustom("Choose a doctor: ");
        String doctorName = scanner.nextLine();

        Doctor doctor = hospital.findDoctorByName(doctorName);
        if (doctor == null) {
            hospital.logError("Doctor with such name not found.");
            return;
        }

        hospital.logCustom("Doctor's schedule for " + doctor.getName() + ":");
        List<String> availableTimeSlots = hospital.generateAvailableTimeSlots(doctor, doctor.getSchedule());

        if (!availableTimeSlots.isEmpty()) {
            for (String slot : availableTimeSlots) {
                hospital.logCustom(slot);
            }

            String time;
            boolean isAvailable = false;
            do {
                hospital.logCustom("Choose an available time slot from the doctor's schedule: ");
                time = scanner.nextLine().trim();

                if (availableTimeSlots.contains(time)) {
                    if (hospital.isTimeSlotOccupied(doctor, time)) {
                        boolean isSlotBooked = hospital.isTimeSlotBooked(time, patient);
                        if (!isSlotBooked) {
                            isAvailable = true;
                        } else {
                            hospital.logError("You already have an appointment with another doctor at this time.");
                        }
                    } else {
                        hospital.logError("Selected time slot is already occupied. Please choose another time.");
                    }
                } else {
                    hospital.logError("Invalid time slot. Please choose from the available time slots.");
                }
            } while (!isAvailable);

            hospital.bookAppointment(doctor, patient, time);
            hospital.logInfo("Appointment successfully booked.");
        } else {
            hospital.logCustom("No available time slots for today.");
        }
    }
}



