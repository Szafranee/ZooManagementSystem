package org.mas.zoomanagementsystem;

import org.mas.zoomanagementsystem.model.*;
import org.mas.zoomanagementsystem.model.embeddable.Address;
import org.mas.zoomanagementsystem.model.enums.*;
import org.mas.zoomanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    // Inject all necessary repositories
    private final ZooRepository zooRepository;
    private final EmployeeRepository employeeRepository;
    private final SpeciesRepository speciesRepository;
    private final EnclosureRepository enclosureRepository;
    private final AnimalRepository animalRepository;
    private final MedicalTreatmentRepository medicalTreatmentRepository;

    @Autowired
    public DataInitializer(ZooRepository zooRepository, EmployeeRepository employeeRepository, SpeciesRepository speciesRepository, EnclosureRepository enclosureRepository, AnimalRepository animalRepository, MedicalTreatmentRepository medicalTreatmentRepository) {
        this.zooRepository = zooRepository;
        this.employeeRepository = employeeRepository;
        this.speciesRepository = speciesRepository;
        this.enclosureRepository = enclosureRepository;
        this.animalRepository = animalRepository;
        this.medicalTreatmentRepository = medicalTreatmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Initializing sample data ---");

        // 1. Create Zoo
        Zoo mainZoo = new Zoo();
        mainZoo.setZooName("Central City Zoo");
        Address zooAddress = new Address();
        zooAddress.setCity("Metropolis");
        zooAddress.setCountry("USA");
        mainZoo.setAddress(zooAddress);
        zooRepository.save(mainZoo);

        // 2. Create Species
        Species lionSpecies = new Species();
        lionSpecies.setScientificName("Panthera leo");
        lionSpecies.setCommonName("Lion");
        lionSpecies.setConservationStatus(ConservationStatus.VULNERABLE);
        speciesRepository.save(lionSpecies);

        Species pandaSpecies = new Species();
        pandaSpecies.setScientificName("Ailuropoda melanoleuca");
        pandaSpecies.setCommonName("Giant Panda");
        pandaSpecies.setConservationStatus(ConservationStatus.VULNERABLE);
        speciesRepository.save(pandaSpecies);

        // 3. Create Enclosures
        Enclosure lionEnclosure = new Enclosure();
        lionEnclosure.setName("Lion's Den");
        lionEnclosure.setType(EnclosureType.EXHIBIT);
        lionEnclosure.setCapacity(5);
        lionEnclosure.setZoo(mainZoo);
        enclosureRepository.save(lionEnclosure);

        Enclosure pandaEnclosure = new Enclosure();
        pandaEnclosure.setName("Panda Forest");
        pandaEnclosure.setType(EnclosureType.EXHIBIT);
        pandaEnclosure.setCapacity(3);
        pandaEnclosure.setZoo(mainZoo);
        enclosureRepository.save(pandaEnclosure);

        // 4. Create Employees (Veterinarians)
        Employee vet1 = new Employee();
        vet1.setFirstName("Anna");
        vet1.setLastName("Kowalska");
        vet1.setEmail("anna.kowalska@zoo.com");
        vet1.setRoles(new HashSet<>(List.of(EmployeeRole.VETERINARIAN)));
        vet1.setSpecialization("Large Mammals");
        employeeRepository.save(vet1);

        Employee vet2 = new Employee();
        vet2.setFirstName("Piotr");
        vet2.setLastName("Nowak");
        vet2.setEmail("piotr.nowak@zoo.com");
        vet2.setRoles(new HashSet<>(List.of(EmployeeRole.VETERINARIAN, EmployeeRole.ZOOKEEPER)));
        vet2.setSpecialization("General Practice");
        employeeRepository.save(vet2);

        // 5. Create Animals
        Animal animal1 = new Animal();
        animal1.setGivenName("Leo");
        animal1.setAnimalID("ZOO-LION-001");
        animal1.setDateOfBirth(LocalDate.of(2018, 5, 20));
        animal1.setGender(Gender.MALE);
        animal1.setHealthStatus(HealthStatus.HEALTHY);
        animal1.setSpecies(lionSpecies);
        animal1.setEnclosure(lionEnclosure);
        animalRepository.save(animal1);

        Animal animal2 = new Animal();
        animal2.setGivenName("Nala");
        animal2.setAnimalID("ZOO-LION-002");
        animal2.setDateOfBirth(LocalDate.of(2019, 7, 15));
        animal2.setGender(Gender.FEMALE);
        animal2.setHealthStatus(HealthStatus.HEALTHY);
        animal2.setSpecies(lionSpecies);
        animal2.setEnclosure(lionEnclosure);
        animalRepository.save(animal2);

        Animal animal3 = new Animal();
        animal3.setGivenName("Bao Bao");
        animal3.setAnimalID("ZOO-PANDA-001");
        animal3.setDateOfBirth(LocalDate.of(2020, 1, 10));
        animal3.setGender(Gender.MALE);
        animal3.setHealthStatus(HealthStatus.RECOVERING);
        animal3.setSpecies(pandaSpecies);
        animal3.setEnclosure(pandaEnclosure);
        animalRepository.save(animal3);

        // 6. Create Sample Medical Treatments
        // --- Treatments for Leo (animal1) ---
        MedicalTreatment t1_leo = new MedicalTreatment();
        t1_leo.setAnimal(animal1);
        t1_leo.setPerformingEmployee(vet1);
        t1_leo.setDate(LocalDateTime.now().minusMonths(1));
        t1_leo.setType(MedicalTreatmentType.VACCINATION);
        t1_leo.setDiagnosis("Annual vaccination booster");
        t1_leo.setProcedureDescription("Administered standard multi-vaccine shot.");
        medicalTreatmentRepository.save(t1_leo);

        MedicalTreatment t2_leo = new MedicalTreatment();
        t2_leo.setAnimal(animal1);
        t2_leo.setPerformingEmployee(vet2);
        t2_leo.setDate(LocalDateTime.now().minusWeeks(2));
        t2_leo.setType(MedicalTreatmentType.CHECKUP);
        t2_leo.setDiagnosis("Minor paw injury");
        t2_leo.setProcedureDescription("Cleaned and disinfected a small cut on the left front paw.");
        t2_leo.setNotes("Caused by a sharp rock in the enclosure. Rock has been removed.");
        medicalTreatmentRepository.save(t2_leo);

        MedicalTreatment t3_leo = new MedicalTreatment();
        t3_leo.setAnimal(animal1);
        t3_leo.setPerformingEmployee(vet1);
        t3_leo.setDate(LocalDateTime.now().minusYears(1));
        t3_leo.setType(MedicalTreatmentType.SURGERY);
        t3_leo.setDiagnosis("Broken canine tooth");
        t3_leo.setProcedureDescription("Surgical extraction of the upper left canine tooth under general anesthesia.");
        medicalTreatmentRepository.save(t3_leo);

        MedicalTreatment t4_leo = new MedicalTreatment();
        t4_leo.setAnimal(animal1);
        t4_leo.setPerformingEmployee(vet2);
        t4_leo.setDate(LocalDateTime.now().minusMonths(4));
        t4_leo.setType(MedicalTreatmentType.MEDICATION);
        t4_leo.setDiagnosis("Routine deworming");
        t4_leo.setProcedureDescription("Administered oral anti-parasitic medication.");
        t4_leo.setMedicationAdministered(List.of("Praziquantel"));
        medicalTreatmentRepository.save(t4_leo);

        // --- Treatments for Nala (animal2) ---
        MedicalTreatment t1_nala = new MedicalTreatment();
        t1_nala.setAnimal(animal2);
        t1_nala.setPerformingEmployee(vet2);
        t1_nala.setDate(LocalDateTime.now().minusMonths(6));
        t1_nala.setType(MedicalTreatmentType.CHECKUP);
        t1_nala.setDiagnosis("Dental checkup");
        t1_nala.setProcedureDescription("Routine dental cleaning and inspection.");
        medicalTreatmentRepository.save(t1_nala);

        MedicalTreatment t2_nala = new MedicalTreatment();
        t2_nala.setAnimal(animal2);
        t2_nala.setPerformingEmployee(vet1);
        t2_nala.setDate(LocalDateTime.now().minusDays(45));
        t2_nala.setType(MedicalTreatmentType.MEDICATION);
        t2_nala.setDiagnosis("Seasonal allergies");
        t2_nala.setProcedureDescription("Prescribed antihistamines for seasonal allergies.");
        t2_nala.setMedicationAdministered(List.of("Cetirizine 10mg"));
        medicalTreatmentRepository.save(t2_nala);

        MedicalTreatment t3_nala = new MedicalTreatment();
        t3_nala.setAnimal(animal2);
        t3_nala.setPerformingEmployee(vet1);
        t3_nala.setDate(LocalDateTime.now().minusMonths(2));
        t3_nala.setType(MedicalTreatmentType.CHECKUP);
        t3_nala.setDiagnosis("Annual physical examination");
        t3_nala.setProcedureDescription("Full body checkup, weight and measurements taken. All normal.");
        medicalTreatmentRepository.save(t3_nala);

        MedicalTreatment t4_nala = new MedicalTreatment();
        t4_nala.setAnimal(animal2);
        t4_nala.setPerformingEmployee(vet2);
        t4_nala.setDate(LocalDateTime.now().minusDays(5));
        t4_nala.setType(MedicalTreatmentType.OBSERVATION);
        t4_nala.setDiagnosis("Behavioral observation");
        t4_nala.setProcedureDescription("Observed reduced appetite. Monitoring behavior for 48 hours.");
        t4_nala.setNotes("Appetite returned to normal the next day. No further action needed.");
        medicalTreatmentRepository.save(t4_nala);

        // --- Treatments for Bao Bao (animal3) ---
        MedicalTreatment t1_baobao = new MedicalTreatment();
        t1_baobao.setAnimal(animal3);
        t1_baobao.setPerformingEmployee(vet2);
        t1_baobao.setDate(LocalDateTime.now().minusDays(10));
        t1_baobao.setType(MedicalTreatmentType.CHECKUP);
        t1_baobao.setDiagnosis("Minor respiratory infection");
        t1_baobao.setProcedureDescription("Administered antibiotics and vitamins.");
        t1_baobao.setMedicationAdministered(List.of("Amoxicillin 500mg", "Vitamin C supplement"));
        medicalTreatmentRepository.save(t1_baobao);

        MedicalTreatment t2_baobao = new MedicalTreatment();
        t2_baobao.setAnimal(animal3);
        t2_baobao.setPerformingEmployee(vet2);
        t2_baobao.setDate(LocalDateTime.now().minusDays(3));
        t2_baobao.setType(MedicalTreatmentType.CHECKUP);
        t2_baobao.setDiagnosis("Follow-up on respiratory infection");
        t2_baobao.setProcedureDescription("Lungs clear, animal is recovering well. Discontinued antibiotics.");
        t2_baobao.setNotes("Health status changed to RECOVERING.");
        medicalTreatmentRepository.save(t2_baobao);

        MedicalTreatment t3_baobao = new MedicalTreatment();
        t3_baobao.setAnimal(animal3);
        t3_baobao.setPerformingEmployee(vet2);
        t3_baobao.setDate(LocalDate.of(2020, 4, 1).atStartOfDay());
        t3_baobao.setType(MedicalTreatmentType.VACCINATION);
        t3_baobao.setDiagnosis("Initial youth vaccinations");
        t3_baobao.setProcedureDescription("Standard set of vaccinations for panda cubs.");
        medicalTreatmentRepository.save(t3_baobao);

        MedicalTreatment t4_baobao = new MedicalTreatment();
        t4_baobao.setAnimal(animal3);
        t4_baobao.setPerformingEmployee(vet1);
        t4_baobao.setDate(LocalDateTime.now().minusMonths(3));
        t4_baobao.setType(MedicalTreatmentType.CHECKUP);
        t4_baobao.setDiagnosis("Weight and dietary assessment");
        t4_baobao.setProcedureDescription("Checked weight and adjusted bamboo portion sizes.");
        medicalTreatmentRepository.save(t4_baobao);

        MedicalTreatment t5_baobao = new MedicalTreatment();
        t5_baobao.setAnimal(animal3);
        t5_baobao.setPerformingEmployee(vet2);
        t5_baobao.setDate(LocalDateTime.now().minusYears(2));
        t5_baobao.setType(MedicalTreatmentType.CHECKUP);
        t5_baobao.setDiagnosis("Annual checkup");
        t5_baobao.setProcedureDescription("Routine annual health screening. All clear.");
        medicalTreatmentRepository.save(t5_baobao);

        System.out.println("--- Sample data initialized successfully ---");
    }
}