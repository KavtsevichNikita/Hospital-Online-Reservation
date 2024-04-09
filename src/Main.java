import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static DoctorManager doctorManager = new DoctorManager();
    private static PatientManager patientManager = new PatientManager();
    private static AppointmentManager appointmentManager = new AppointmentManager();
    private static Logger logger = new Logger();
    private static Patient patient1 = new Patient("P001", "Alice Johnson", "1234567890");
    private static Patient patient2 = new Patient("P002", "Bob Brown", "0987654321");

    public static void main(String[] args) {
        int choice;

        // init data for patients
        patientManager.registerPatient(patient1);
        patientManager.registerPatient(patient2);

        do {
            try {
                logger.logInfo("╔══════════════════════════════════════╗");
                logger.logInfo("║                Menu:                 ║");
                logger.logInfo("║                                      ║");
                logger.logInfo("║   0 - Exit the application           ║");
                logger.logInfo("║                                      ║");
                logger.logInfo("║               DOCTORS:               ║");
                logger.logInfo("║   1 - Add a doctor                   ║");
                logger.logInfo("║   2 - View list of all doctors       ║");
                logger.logInfo("║   3 - Edit a doctor                  ║");
                logger.logInfo("║   4 - Remove a doctor                ║");
                logger.logInfo("║                                      ║");
                logger.logInfo("║               PATIENTS:              ║");
                logger.logInfo("║   5 - Add a patient                  ║");
                logger.logInfo("║   6 - View list of all patients      ║");
                logger.logInfo("║   7 - Edit a patient                 ║");
                logger.logInfo("║   8 - Remove a patient               ║");
                logger.logInfo("║   9 - View patient's appointments    ║");
                logger.logInfo("║   10 - Book an appointment           ║");
                logger.logInfo("╚══════════════════════════════════════╝");

                logger.logInfo("Choose an action: ");
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
                        logger.logInfo("Exiting the application...");
                        break;
                    default:
                        logger.logError("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.logError("An error occurred: " + e.getMessage());
                scanner.nextLine();
                choice = -1;
            }
        } while (choice != 0);
    }

    private static void addDoctor() {
        try {
            logger.logInfo("====================  ADD NEW DOCTOR  ====================");

            String id, name, specialization, choice;
            Schedule doctorSchedule = new Schedule();
            boolean addMore = true;
            LocalTime startTime = null;
            LocalTime endTime = null;

            // doctor's id
            do {
                logger.logCustom("Enter doctor's id: ");
                id = scanner.nextLine();
                if (id.isEmpty()) {
                    logger.logError("Doctor's id cannot be empty. Please fill in the data.");
                }

                if (doctorManager.findDoctorById(id)) {
                    logger.logError("Doctor with such id already exists in the database.");
                    continue;
                }

            } while (id.isEmpty() || doctorManager.findDoctorById(id));

            // doctor's name
            do {
                logger.logCustom("Enter doctor's name: ");
                name = scanner.nextLine();
                if (name.isEmpty()) {
                    logger.logError("Doctor's name cannot be empty. Please fill in the data.");
                }

                if (doctorManager.findDoctorByName(name) != null) {
                    logger.logError("This doctor already exists in the database.");
                    continue;
                }

            } while (name.isEmpty() || doctorManager.findDoctorByName(name) != null);

            // doctor specialization
            do {
                logger.logCustom("Enter doctor's specialization: ");
                specialization = scanner.nextLine();
                if (specialization.isEmpty()) {
                    logger.logError("Doctor's specialization cannot be empty. Please fill in the data.");
                }
            } while (specialization.isEmpty());

            logger.logInfo("------------------------------------------------------");

            do {
                DayOfWeek day = null;
                int dayChoice;
                do {
                    logger.logCustom("Select day of the week:");
                    logger.logCustom("1. Monday");
                    logger.logCustom("2. Tuesday");
                    logger.logCustom("3. Wednesday");
                    logger.logCustom("4. Thursday");
                    logger.logCustom("5. Friday");
                    logger.logCustom("6. Saturday");
                    logger.logCustom("7. Sunday");
                    logger.logCustom("Enter your choice (1-7): ");
                    if (scanner.hasNextInt()) {
                        dayChoice = scanner.nextInt();
                        if (dayChoice >= 1 && dayChoice <= 7) {
                            day = DayOfWeek.of(dayChoice);
                        } else {
                            logger.logError("Invalid day selection. Please enter a number between 1 and 7.");
                        }
                    } else {
                        logger.logError("Invalid input. Please enter a number between 1 and 7.");
                        scanner.next();
                    }
                } while (day == null);

                if (doctorSchedule.hasSlot(day)) {
                    logger.logError("You are already working at this day. Please choose another day.");
                    continue;
                }

                do {
                    logger.logCustom("Enter start time (HH:MM): ");
                    String startTimeStr = scanner.next();
                    try {
                        startTime = LocalTime.parse(startTimeStr);
                    } catch (Exception e) {
                        logger.logError("Invalid time format. Please enter time in HH:MM format.");
                    }
                } while (startTime == null);

                do {
                    logger.logCustom("Enter end time (HH:MM): ");
                    String endTimeStr = scanner.next();
                    try {
                        endTime = LocalTime.parse(endTimeStr);
                        if (endTime.isBefore(startTime)) {
                            logger.logError("End time cannot be before start time.");
                            endTime = null;
                        }
                    } catch (Exception e) {
                        logger.logError("Invalid time format. Please enter time in HH:MM format.");
                    }
                } while (endTime == null);

                doctorSchedule.addSlot(day, startTime, endTime);

                do {
                    logger.logCustom("Do you want to add another schedule? (yes/no): ");
                    choice = scanner.next();
                    if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))) {
                        logger.logError("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } while (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")));

                addMore = choice.equalsIgnoreCase("yes");
            } while (addMore);

            Doctor doctor = new Doctor(String.valueOf(doctorManager.getDoctors().size() + 1), name, specialization, doctorSchedule);

            doctorManager.registerDoctor(doctor);

            logger.logCustom("-----------------------------------");
            logger.logInfo("Doctor added: " + doctor.getName());
            logger.logInfo("==================== ADD NEW DOCTOR FINISHED SUCCESSFULLY ====================");
        } catch (Exception e) {
            logger.logError("An error occurred while adding a doctor: " + e.getMessage());
        }
    }
    private static void viewAllDoctors() {
        logger.logInfo("==================== DOCTORS LIST ====================");

        if (doctorManager.getDoctors().isEmpty()) {
            logger.logCustom("Doctors not found");
        } else {
            for (Doctor doctor : doctorManager.getDoctors()) {
                logger.logInfo("========= Doctor " + doctor.getName() + " =========");
                logger.logCustom("Name: " + doctor.getName());
                logger.logCustom("Specialization: " + doctor.getSpecialization());
                logger.logCustom("Schedule:");
                List<String> availableSlots = doctor.getSchedule().getAvailableSlots();
                if (availableSlots.isEmpty()) {
                    logger.logCustom("No available slots");
                } else {
                    for (String slot : availableSlots) {
                        logger.logCustom(slot);
                    }
                }
            }
        }
    }
    private static void removeDoctor() {
        logger.logInfo("====================  REMOVE DOCTOR  ====================");

        List<Doctor> availableDoctors = doctorManager.getDoctors();

        if (availableDoctors.isEmpty()) {
            logger.logCustom("Doctors list is empty!");
            return;
        }

        logger.logCustom("------ Doctors list ------");
        availableDoctors.forEach(doctor -> logger.logCustom(doctor.getName()));

        logger.logCustom("Enter doctor's name to remove: ");
        String name = scanner.nextLine();

        doctorManager.removeDoctor(name);  // remove doctor
    }
    private static void editDoctor() {
        logger.logInfo("====================  EDIT DOCTOR INFORMATION  ====================");

        List<Doctor> doctors = doctorManager.getDoctors();
        if (doctors.isEmpty()) {
            logger.logError("No doctors found.");
            return;
        }

        logger.logInfo("Select a doctor to edit:");
        for (int i = 0; i < doctors.size(); i++) {
            logger.logCustom((i + 1) + ". " + doctors.get(i).getName());
        }

        logger.logCustom("Enter the number of the doctor you want to edit: ");
        int doctorIndex = scanner.nextInt();
        scanner.nextLine();

        if (doctorIndex < 1 || doctorIndex > doctors.size()) {
            logger.logError("Invalid doctor number.");
            return;
        }

        Doctor doctorToEdit = doctors.get(doctorIndex - 1);

        logger.logCustom("Current Name: " + doctorToEdit.getName());
        logger.logCustom("Current Specialization: " + doctorToEdit.getSpecialization());
        logger.logCustom("Current Schedule: " + doctorToEdit.getSchedule());

        logger.logCustom("Enter the new name (or leave empty to keep current): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            doctorToEdit.setName(newName);
            logger.logInfo("Name updated successfully.");
        }

        logger.logCustom("Enter the new specialization (or leave empty to keep current): ");
        String newSpecialization = scanner.nextLine().trim();
        if (!newSpecialization.isEmpty()) {
            doctorToEdit.setSpecialization(newSpecialization);
            logger.logInfo("Specialization updated successfully.");
        }

        logger.logCustom("Would you like to edit the schedule? (yes/no): ");
        String editScheduleChoice = scanner.nextLine().trim();
        if (editScheduleChoice.equalsIgnoreCase("yes")) {
            editDoctorSchedule(doctorToEdit);
        }

        logger.logInfo("==================== EDIT DOCTOR INFORMATION FINISHED ====================");
    }
    private static void editDoctorSchedule(Doctor doctorToEdit) {
        logger.logInfo("====================  EDIT DOCTOR SCHEDULE  ====================");

        Schedule schedule = doctorToEdit.getSchedule();

        logger.logInfo("Current schedule for Doctor " + doctorToEdit.getName() + ":");
        List<String> availableSlots = schedule.getAvailableSlots();
        if (!availableSlots.isEmpty()) {
            for (String slot : availableSlots) {
                logger.logCustom(slot);
            }
        } else {
            logger.logCustom("No available slots for the current schedule.");
            return;
        }

        Schedule newSchedule = new Schedule();
        boolean addMore = true;
        do {
            DayOfWeek day = null;
            int dayChoice;
            do {
                logger.logCustom("Select day of the week:");
                logger.logCustom("1. Monday");
                logger.logCustom("2. Tuesday");
                logger.logCustom("3. Wednesday");
                logger.logCustom("4. Thursday");
                logger.logCustom("5. Friday");
                logger.logCustom("6. Saturday");
                logger.logCustom("7. Sunday");
                logger.logCustom("Enter your choice (1-7): ");
                if (scanner.hasNextInt()) {
                    dayChoice = scanner.nextInt();
                    if (dayChoice >= 1 && dayChoice <= 7) {
                        day = DayOfWeek.of(dayChoice);
                    } else {
                        logger.logError("Invalid day selection. Please enter a number between 1 and 7.");
                    }
                } else {
                    logger.logError("Invalid input. Please enter a number between 1 and 7.");
                    scanner.next();
                }
            } while (day == null);

            LocalTime startTime = null;
            do {
                logger.logCustom("Enter start time (HH:MM): ");
                String startTimeStr = scanner.next();
                try {
                    startTime = LocalTime.parse(startTimeStr);
                } catch (Exception e) {
                    logger.logError("Invalid time format. Please enter time in HH:MM format.");
                }
            } while (startTime == null);

            LocalTime endTime = null;
            do {
                logger.logCustom("Enter end time (HH:MM): ");
                String endTimeStr = scanner.next();
                try {
                    endTime = LocalTime.parse(endTimeStr);
                    if (endTime.isBefore(startTime)) {
                        logger.logError("End time cannot be before start time.");
                        endTime = null;
                    }
                } catch (Exception e) {
                    logger.logError("Invalid time format. Please enter time in HH:MM format.");
                }
            } while (endTime == null);

            newSchedule.addSlot(day, startTime, endTime);

            String choice;
            do {
                logger.logCustom("Do you want to add another schedule? (yes/no): ");
                choice = scanner.next();
                if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))) {
                    logger.logError("Invalid input. Please enter 'yes' or 'no'.");
                }
            } while (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")));

            addMore = choice.equalsIgnoreCase("yes");
        } while (addMore);

        doctorToEdit.setSchedule(newSchedule);
        logger.logInfo("Doctor's schedule updated successfully.");

        logger.logInfo("==================== EDIT DOCTOR SCHEDULE FINISHED ====================");
    }
    private static void addPatient() {
        logger.logInfo("====================  ADD NEW PATIENT  ====================");

        String id, name, phoneNumber;

        do {
            logger.logCustom("Enter patient's name, surname: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                logger.logError("Patient's name cannot be empty. Please fill in the data.");
                continue;
            }

            if (patientManager.findPatientByName(name) != null) {
                logger.logError("This patient already exists in the database.");
                continue;
            }
        } while (name.isEmpty() || patientManager.findPatientByName(name) != null);

        do {
            logger.logCustom("Enter patient's phone number: ");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.isEmpty()) {
                logger.logError("Patient's phone number cannot be empty. Please fill in the data.");
            }
        } while (phoneNumber.isEmpty());

        do {
            logger.logCustom("Enter patient's ID number: ");
            id = scanner.nextLine();
            if (id.isEmpty()) {
                logger.logError("Patient's ID cannot be empty. Please fill in the data.");
            }
        } while (id.isEmpty());

        Patient patient = new Patient(id, name, phoneNumber);
        patientManager.registerPatient(patient);

        logger.logInfo("Patient added: " + patient.getName());
        logger.logInfo("==================== ADD NEW PATIENT FINISHED SUCCESSFULLY ====================");
    }
    private static void viewAllPatients() {
        logger.logInfo("==================== PATIENTS LIST ====================");

        if (patientManager.getPatients().isEmpty()) {
            logger.logCustom("==========Patients not found!==========");
        } else {
            for (Patient patient : patientManager.getPatients()) {
                logger.logInfo("========= Patient " + patient.getName() + " =========");
                logger.logCustom("Name: " + patient.getName());
                logger.logCustom("ID: " + patient.getId());
                logger.logCustom("Phone: " + patient.getPhoneNumber());
                logger.logCustom("--------------------------");
            }
        }
    }
    private static void removePatient() {
        logger.logInfo("====================  REMOVE PATIENT  ====================");

        List<Patient> availablePatients = patientManager.getPatients();
        if (availablePatients.isEmpty()) {
            logger.logCustom("Patients list is empty!");
        } else {
            logger.logCustom("------ Patients list ------");

            for (Patient patient : availablePatients) {
                logger.logCustom(patient.getName());
            }
            logger.logCustom("Enter patient's name to remove: ");
            String name = scanner.nextLine();

            patientManager.removePatient(name); // remove Patient
        }
    }
    private static void editPatient() {
        logger.logInfo("====================  EDIT PATIENT INFORMATION  ====================");

        List<Patient> patients = patientManager.getPatients();

        if (patients.isEmpty()) {
            logger.logError("No patients found.");
            return;
        }

        logger.logInfo("Select a patient to edit:");
        for (int i = 0; i < patients.size(); i++) {
            logger.logCustom((i + 1) + ". " + patients.get(i).getName());
        }

        logger.logCustom("Enter the number of the patient you want to edit: ");
        int patientIndex = scanner.nextInt();
        scanner.nextLine();

        if (patientIndex < 1 || patientIndex > patients.size()) {
            logger.logError("Invalid patient number.");
            return;
        }

        Patient patientToEdit = patients.get(patientIndex - 1);

        logger.logCustom("Current ID: " + patientToEdit.getId());
        logger.logCustom("Current Name: " + patientToEdit.getName());
        logger.logCustom("Current Phone Number: " + patientToEdit.getPhoneNumber());

        logger.logCustom("Enter the new name (or leave empty to keep current): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            patientToEdit.setName(newName);
            logger.logInfo("Name updated successfully.");
        }

        logger.logCustom("Enter the new phone number (or leave empty to keep current): ");
        String newPhoneNumber = scanner.nextLine().trim();
        if (!newPhoneNumber.isEmpty()) {
            patientToEdit.setPhoneNumber(newPhoneNumber);
            logger.logInfo("Phone number updated successfully.");
        }

        logger.logInfo("==================== EDIT PATIENT INFORMATION FINISHED ====================");
    }
    private static void viewPatientAppointments() {
        logger.logCustom("Enter patient's name: ");
        String name = scanner.nextLine();

        logger.logCustom("Enter patient's ID: ");
        String id = scanner.nextLine();

        Patient patient = patientManager.findPatientByNameAndId(name, id);

        if (patient != null) {
            List<Appointment> appointments = appointmentManager.getAppointmentsForPatient(patient);

            if (appointments.isEmpty()) {
                logger.logCustom("==================You have no appointments with doctors.==================");
            } else {
                logger.logCustom("Your appointments with doctors:");
                for (Appointment appointment : appointments) {
                    logger.logCustom("Date and time: " + appointment.getTime() + ", Doctor: " + appointment.getDoctor().getName());
                }
            }
        } else {
            logger.logError("Patient with such name and ID not found.");
        }
    }
    private static void bookAppointment() {
        logger.logCustom("Enter your name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            logger.logError("Name cannot be empty. Please enter your name.");
            bookAppointment();
            return;
        }

        Patient patient = patientManager.findPatientByName(name);
        if (patient == null) {
            logger.logError("Patient with such name not found. Please register.");
            return;
        }

        logger.logCustom("Enter the doctor's specialization you want to book with: ");
        String specialization = scanner.nextLine();

        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctorManager.getDoctors()) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                availableDoctors.add(doctor);
            }
        }

        if (availableDoctors.isEmpty()) {
            logger.logCustom("No doctors found with such specialization.");
            return;
        }

        logger.logInfo("Available doctors:");
        for (Doctor doctor : availableDoctors) {
            logger.logCustom(doctor.getName());
        }

        logger.logCustom("Choose a doctor: ");
        String doctorName = scanner.nextLine();

        Doctor doctor = doctorManager.findDoctorByName(doctorName);
        if (doctor == null) {
            logger.logError("Doctor with such name not found.");
            return;
        }

        logger.logCustom("Doctor's schedule for " + doctor.getName() + ":");
        List<String> availableTimeSlots = appointmentManager.generateAvailableTimeSlots(doctor, doctor.getSchedule());

        if (!availableTimeSlots.isEmpty()) {
            for (String slot : availableTimeSlots) {
                logger.logCustom(slot);
            }

            String time;
            boolean isAvailable = false;
            do {
                logger.logCustom("Choose an available time slot from the doctor's schedule: ");
                time = scanner.nextLine().trim();

                if (availableTimeSlots.contains(time)) {
                    if (appointmentManager.isTimeSlotOccupied(doctor, time)) {
                        boolean isSlotBooked = appointmentManager.isTimeSlotBooked(time, patient);
                        if (!isSlotBooked) {
                            isAvailable = true;
                        } else {
                            logger.logError("You already have an appointment with another doctor at this time.");
                        }
                    } else {
                        logger.logError("Selected time slot is already occupied. Please choose another time.");
                    }
                } else {
                    logger.logError("Invalid time slot. Please choose from the available time slots.");
                }
            } while (!isAvailable);

            appointmentManager.bookAppointment(doctor, patient, time);
            logger.logInfo("Appointment successfully booked.");

        } else {
            logger.logCustom("No available time slots for today.");
        }
    }
}
