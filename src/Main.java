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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            try {
                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║                Menu:                 ║");
                System.out.println("║                                      ║");
                System.out.println("║   0 - Exit the application           ║");
                System.out.println("║                                      ║");
                System.out.println("║               DOCTORS:               ║");
                System.out.println("║   1 - Add a doctor                   ║");
                System.out.println("║   2 - View list of all doctors       ║");
                System.out.println("║   3 - Remove a doctor                ║");
                System.out.println("║                                      ║");
                System.out.println("║               PATIENTS:              ║");
                System.out.println("║   4 - Add a patient                  ║");
                System.out.println("║   5 - View list of all patients      ║");
                System.out.println("║   6 - Remove a patient               ║");
                System.out.println("║   7 - View patient's appointments    ║");
                System.out.println("║   8 - Book an appointment            ║");
                System.out.println("╚══════════════════════════════════════╝");

                System.out.print("Choose an action: ");
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
                        removeDoctor();
                        break;
                    case 4:
                        addPatient();
                        break;
                    case 5:
                        viewAllPatients();
                        break;
                    case 6:
                        removePatient();
                        break;
                    case 7:
                        viewPatientAppointments();
                        break;
                    case 8:
                        bookAppointment();
                        break;
                    case 0:
                        System.out.println("Exiting the application...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine();
                choice = -1;
            }
        } while (choice != 0);
    }

    ///// Doctor
    private static void addDoctor() {
        try {
            System.out.println("\u001B[34m====================  ADD NEW DOCTOR  ====================\u001B[0m");
            String name;
            do {
                System.out.print("\u001B[3mEnter doctor's name: \u001B[0m");
                name = scanner.nextLine();
                if (name.isEmpty()) {
                    System.out.println("\u001B[31mError: Doctor's name cannot be empty. Please fill in the data.\u001B[0m");
                }

                if (doctorManager.findDoctorByName(name) != null) {
                    System.out.println("\u001B[31mError: This doctor already exists in the database.\u001B[0m");
                    continue;
                }

            } while (name.isEmpty() || doctorManager.findDoctorByName(name) != null);

            String specialization;
            do {
                System.out.print("\u001B[3mEnter doctor's specialization: \u001B[0m");
                specialization = scanner.nextLine();
                if (specialization.isEmpty()) {
                    System.out.println("\u001B[31mError: Doctor's specialization cannot be empty. Please fill in the data.\u001B[0m");
                }
            } while (specialization.isEmpty());

            System.out.println("------------------------------------------------------");

            Schedule doctorSchedule = new Schedule();

            boolean addMore = true;
            String choice;
            do {
                DayOfWeek day = null;
                int dayChoice;
                do {
                    System.out.println("\u001B[3mSelect day of the week: \u001B[0m");
                    System.out.println("1. Monday");
                    System.out.println("2. Tuesday");
                    System.out.println("3. Wednesday");
                    System.out.println("4. Thursday");
                    System.out.println("5. Friday");
                    System.out.println("6. Saturday");
                    System.out.println("7. Sunday");
                    System.out.print("Enter your choice (1-7): ");
                    if (scanner.hasNextInt()) {
                        dayChoice = scanner.nextInt();
                        if (dayChoice >= 1 && dayChoice <= 7) {
                            day = DayOfWeek.of(dayChoice);
                        } else {
                            System.out.println("\u001B[31mError: Invalid day selection. Please enter a number between 1 and 7.\u001B[0m");
                        }
                    } else {
                        System.out.println("\u001B[31mError: Invalid input. Please enter a number between 1 and 7.\u001B[0m");
                        scanner.next();
                    }
                } while (day == null);

                if (doctorSchedule.hasSlot(day)) {
                    System.out.println("\u001B[31mError: You are already working at this time on " + day + ". Please choose another time.\u001B[0m");
                    continue;
                }

                LocalTime startTime = null;
                do {
                    System.out.print("\u001B[3mEnter start time (HH:MM): \u001B[0m");
                    String startTimeStr = scanner.next();
                    try {
                        startTime = LocalTime.parse(startTimeStr);
                    } catch (Exception e) {
                        System.out.println("\u001B[31mError: Invalid time format. Please enter time in HH:MM format.\u001B[0m");
                    }
                } while (startTime == null);

                LocalTime endTime = null;
                do {
                    System.out.print("\u001B[3mEnter end time (HH:MM): \u001B[0m");
                    String endTimeStr = scanner.next();
                    try {
                        endTime = LocalTime.parse(endTimeStr);
                        if (endTime.isBefore(startTime)) {
                            System.out.println("\u001B[31mError: End time cannot be before start time.\u001B[0m");
                            endTime = null;
                        }
                    } catch (Exception e) {
                        System.out.println("\u001B[31mError: Invalid time format. Please enter time in HH:MM format.\u001B[0m");
                    }
                } while (endTime == null);

                doctorSchedule.addSlot(day, startTime, endTime);

                do {
                    System.out.print("\u001B[3mDo you want to add another schedule? (yes/no): \u001B[0m");
                    choice = scanner.next();
                    if (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))) {
                        System.out.println("\u001B[31mError: Invalid input. Please enter 'yes' or 'no'.\u001B[0m");
                    }
                } while (!(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no")));

                addMore = choice.equalsIgnoreCase("yes");
            } while (addMore);

            Doctor doctor = new Doctor(String.valueOf(doctorManager.getDoctors().size() + 1), name, specialization, doctorSchedule);

            doctorManager.registerDoctor(doctor);
            System.out.println("-----------------------------------");
            System.out.println("\u001B[32mDoctor added: \u001B[1m" + doctor.getName() + "\u001B[0m");
            System.out.println("\u001B[32m==================== ADD NEW DOCTOR FINISHED SUCCESSFULLY ====================\u001B[0m");
        } catch (Exception e) {
            System.out.println("An error occurred while adding a doctor: " + e.getMessage());
        }
    }

    private static void viewAllDoctors() {
        System.out.println("\u001B[34m==================== DOCTORS LIST ====================\u001B[0m");

        if (doctorManager.getDoctors().isEmpty()) {
            System.out.println("DOCTORS NOT FOUND");
        } else {
            for (Doctor doctor : doctorManager.getDoctors()) {
                System.out.println("\u001B[32m========= Doctor \u001B[1m" + doctor.getName() + " =========\u001B[0m");
                System.out.println("Name: " + doctor.getName());
                System.out.println("Specialization: " + doctor.getSpecialization());
                System.out.println("Schedule:");
                List<String> availableSlots = doctor.getSchedule().getAvailableSlots();
                if (availableSlots.isEmpty()) {
                    System.out.println("No available slots");
                } else {
                    for (String slot : availableSlots) {
                        System.out.println(slot);
                    }
                }
                System.out.println("--------------------------");
            }
        }
    }

    private static void removeDoctor() {
        System.out.println("\u001B[34m====================  REMOVE DOCTOR  ====================\u001B[0m");

        List<Doctor> availableDoctors = doctorManager.getDoctors();

        if (availableDoctors.isEmpty()) {
            System.out.println("\u001B[34mDoctors list is empty!\u001B[0m");
        } else {
            System.out.println("------ Doctors list ------");

            for (Doctor doctor : availableDoctors) {
                System.out.println(doctor.getName());
            }
            System.out.print("\u001B[3mEnter doctor's name to remove: \u001B[0m");
            String name = scanner.nextLine();

            doctorManager.removeDoctor(name);
        }
    }

    ///// Patient
    private static void addPatient() {
        System.out.println("\u001B[34m====================  ADD NEW PATIENT  ====================\u001B[0m");

        String name;
        String id;
        String phoneNumber;

        do {
            System.out.print("\u001B[3mEnter patient's name, surname: \u001B[0m");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("\u001B[31mError: Patient's name cannot be empty. Please fill in the data.\u001B[0m");
                continue;
            }

            if (patientManager.findPatientByName(name) != null) {
                System.out.println("\u001B[31mError: This patient already exists in the database.\u001B[0m");
                continue;
            }

            System.out.println("------------------------------------------------------");
        } while (name.isEmpty() || patientManager.findPatientByName(name) != null);

        do {
            System.out.print("\u001B[3mEnter patient's phone number: \u001B[0m");
            phoneNumber = scanner.nextLine();
            if (phoneNumber.isEmpty()) {
                System.out.println("\u001B[31mError: Patient's phoneNumber4 cannot be empty. Please fill in the data.\u001B[0m");
            }
            System.out.println("------------------------------------------------------");
        } while (phoneNumber.isEmpty());

        do {
            System.out.print("\u001B[3mEnter patient's ID number: \u001B[0m");
            id = scanner.nextLine();
            if (id.isEmpty()) {
                System.out.println("\u001B[31mError: Patient's id cannot be empty. Please fill in the data.\u001B[0m");
            }
            System.out.println("------------------------------------------------------");
        } while (id.isEmpty());

        Patient patient = new Patient(id, name, phoneNumber);
        patientManager.registerPatient(patient);

        System.out.println("\u001B[32mPatient added: \u001B[1m" + patient.getName() + "\u001B[0m");
        System.out.println("\u001B[32m==================== ADD NEW PATIENT FINISHED SUCCESSFULLY ====================\u001B[0m");
    }

    private static void viewAllPatients() {
        System.out.println("\u001B[34m==================== PATIENTS LIST ====================\u001B[0m");

        if (patientManager.getPatients().isEmpty()) {
            System.out.println("PATIENTS NOT FOUND");
        } else {
            for (Patient patient : patientManager.getPatients()) {
                System.out.println("\u001B[32m========= Patient \u001B[1m" + patient.getName() + " =========\u001B[0m");
                System.out.println("Name: " + patient.getName());
                System.out.println("ID: " + patient.getId());
                System.out.println("Phone: " + patient.getPhoneNumber());
                System.out.println("--------------------------");
            }
        }
    }

    private static void removePatient() {
        System.out.println("\u001B[34m====================  REMOVE PATIENT  ====================\u001B[0m");
        List<Patient> availablePatients = patientManager.getPatients();

        if (availablePatients.isEmpty()) {
            System.out.println("\u001B[34mPatients list is empty!\u001B[0m");
        } else {
            System.out.println("------ Patients list ------");

            for (Patient patient : availablePatients) {
                System.out.println(patient.getName());
            }
            System.out.print("\u001B[3mEnter patient's name to remove: \u001B[0m");
            String name = scanner.nextLine();

            patientManager.removePatient(name);
        }
    }

    ///// Appointment
    private static void viewPatientAppointments() {
        System.out.print("Enter patient's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient's ID: ");
        String id = scanner.nextLine();
        Patient patient = patientManager.findPatientByName(name);
        if (patient != null && patient.getName().equalsIgnoreCase(name) && patient.getId().equalsIgnoreCase(id)) {
            List<Appointment> appointments = appointmentManager.getAppointmentsForPatient(patient);
            if (appointments.isEmpty()) {
                System.out.println("You have no appointments with doctors.");
            } else {
                System.out.println("Your appointments with doctors:");
                for (Appointment appointment : appointments) {
                    System.out.println("Date and time: " + appointment.getTime() + ", Doctor: " + appointment.getDoctor().getName());
                }
            }
        } else {
            System.out.println("Patient with such name and ID not found.");
        }
    }
    private static void bookAppointment() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("\u001B[31m" + "Name cannot be empty. Please enter your name." + "\u001B[0m");
            bookAppointment();
            return;
        }

        Patient patient = patientManager.findPatientByName(name);
        if (patient == null) {
            System.out.println("\u001B[31m" + "Patient with such name not found. Please register." + "\u001B[0m");
            return;
        }

        System.out.print("Enter the doctor's specialization you want to book with: ");
        String specialization = scanner.nextLine();

        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctorManager.getDoctors()) {
            if (doctor.getSpecialization().equalsIgnoreCase(specialization)) {
                availableDoctors.add(doctor);
            }
        }

        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors found with such specialization.");
            return;
        }

        System.out.println("Available doctors:");
        for (Doctor doctor : availableDoctors) {
            System.out.println(doctor.getName());
        }

        System.out.print("Choose a doctor: ");
        String doctorName = scanner.nextLine();

        Doctor doctor = doctorManager.findDoctorByName(doctorName);
        if (doctor == null) {
            System.out.println("\u001B[31m" + "Doctor with such name not found." + "\u001B[0m");
            return;
        }

        System.out.println("Doctor's schedule for " + doctor.getName() + ":");
        List<String> availableTimeSlots = appointmentManager.generateAvailableTimeSlots(doctor, doctor.getSchedule());

        if (!availableTimeSlots.isEmpty()) {
            for (String slot : availableTimeSlots) {
                System.out.println(slot);
            }

            String time;
            boolean isAvailable = false;
            do {
                System.out.print("Choose an available time slot from the doctor's schedule: ");
                time = scanner.nextLine().trim();

                if (availableTimeSlots.contains(time)) {
                    if (appointmentManager.isTimeSlotOccupied(doctor, time)) {
                        boolean isSlotBooked = appointmentManager.isTimeSlotBooked(time, patient);
                        if (!isSlotBooked) {
                            isAvailable = true;
                        } else {
                            System.out.println("\u001B[31m" + "You already have an appointment with another doctor at this time." + "\u001B[0m"); // Красный цвет
                        }
                    } else {
                        System.out.println("\u001B[31m" + "Selected time slot is already occupied. Please choose another time." + "\u001B[0m"); // Красный цвет
                    }
                } else {
                    System.out.println("\u001B[31m" + "Invalid time slot. Please choose from the available time slots." + "\u001B[0m"); // Красный цвет
                }
            } while (!isAvailable);

            appointmentManager.bookAppointment(doctor, patient, time);
            System.out.println("\u001B[32m" + "Appointment successfully booked." + "\u001B[0m"); // Зеленый цвет

        } else {
            System.out.println("No available time slots for today.");
        }
    }
}