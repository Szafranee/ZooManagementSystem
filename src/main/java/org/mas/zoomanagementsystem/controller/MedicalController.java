package org.mas.zoomanagementsystem.controller;

import org.mas.zoomanagementsystem.dto.CreateMedicalTreatmentDto;
import org.mas.zoomanagementsystem.dto.MedicalHistoryViewDto;
import org.mas.zoomanagementsystem.dto.MedicalTreatmentSummaryDto;
import org.mas.zoomanagementsystem.model.Animal;
import org.mas.zoomanagementsystem.model.Employee;
import org.mas.zoomanagementsystem.model.enums.EmployeeRole;
import org.mas.zoomanagementsystem.repository.AnimalRepository;
import org.mas.zoomanagementsystem.repository.EmployeeRepository;
import org.mas.zoomanagementsystem.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling the "Manage Animal Medical History" use case.
 * It supports viewing medical history from both the animal's and the veterinarian's perspective.
 */
@Controller
@RequestMapping("/medical") // A more general mapping for all medical-related views
public class MedicalController {

    private final MedicalHistoryService medicalHistoryService;
    private final EmployeeRepository employeeRepository;
    private final AnimalRepository animalRepository;

    @Autowired
    public MedicalController(MedicalHistoryService medicalHistoryService, EmployeeRepository employeeRepository, AnimalRepository animalRepository) {
        this.medicalHistoryService = medicalHistoryService;
        this.employeeRepository = employeeRepository;
        this.animalRepository = animalRepository;
    }

    /**
     * Displays the main view for browsing medical history, focused on animals.
     * This is the primary entry point for the use case.
     *
     * @param animalId    The ID of the selected animal (optional).
     * @param treatmentId The ID of the selected treatment (optional).
     * @param model       The Spring Model object to pass data to the view.
     * @return The name of the Thymeleaf template to render.
     */
    @GetMapping("/by-animal")
    public String showHistoryByAnimal(@RequestParam(name = "animalId", required = false) Long animalId, @RequestParam(name = "treatmentId", required = false) Long treatmentId, Model model) {

        var animals = medicalHistoryService.getAllAnimalsForListing();
        var viewDto = MedicalHistoryViewDto.builder().animals(animals).build();

        if (animalId != null) {
            var treatments = medicalHistoryService.getTreatmentsForAnimal(animalId);
            viewDto.setTreatments(treatments);
            viewDto.setSelectedAnimalId(animalId);

            if (treatmentId != null) {
                var treatmentDetail = medicalHistoryService.getTreatmentDetails(treatmentId);
                viewDto.setTreatmentDetail(treatmentDetail);
                viewDto.setSelectedTreatmentId(treatmentId);
            }
        }

        model.addAttribute("viewData", viewDto);
        return "history-by-animal";
    }

    /**
     * Displays an alternative view for browsing medical history, focused on veterinarians.
     * This demonstrates navigating the association from the other side.
     *
     * @param vetId       The ID of the selected veterinarian (optional).
     * @param treatmentId The ID of the selected treatment (optional).
     * @param model       The Spring Model object.
     * @return The name of the Thymeleaf template.
     */
    @GetMapping("/by-veterinarian")
    public String showHistoryByVeterinarian(@RequestParam(name = "vetId", required = false) Long vetId, @RequestParam(name = "treatmentId", required = false) Long treatmentId, Model model) {

        List<Employee> veterinarians = employeeRepository.findByRolesContaining(EmployeeRole.VETERINARIAN);
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("selectedVetId", vetId);

        if (vetId != null) {
             List<MedicalTreatmentSummaryDto> treatments = medicalHistoryService.getTreatmentsByVeterinarian(vetId);
             model.addAttribute("treatments", treatments);

            if (treatmentId != null) {
                var treatmentDetail = medicalHistoryService.getTreatmentDetails(treatmentId);
                model.addAttribute("treatmentDetail", treatmentDetail);
                model.addAttribute("selectedTreatmentId", treatmentId);
            }
        }

        return "history-by-veterinarian";
    }

    /**
     * Displays the form for adding a new medical treatment.
     * It can be initiated with a pre-selected animal.
     *
     * @param animalId The ID of the animal to pre-select (optional).
     * @param model    The Spring Model object.
     * @return The name of the Thymeleaf template for the form.
     */
    @GetMapping("/treatments/new")
    public String showAddTreatmentForm(@RequestParam(name = "animalId", required = false) Long animalId, Model model) {
        List<Employee> veterinarians = employeeRepository.findByRolesContaining(EmployeeRole.VETERINARIAN);
        List<Animal> animals = animalRepository.findAll();

        CreateMedicalTreatmentDto treatmentDto = new CreateMedicalTreatmentDto();
        if (animalId != null) {
            treatmentDto.setAnimalId(animalId); // Pre-populate if coming from animal's page
        }

        model.addAttribute("treatment", treatmentDto);
        model.addAttribute("allAnimals", animals);
        model.addAttribute("allVeterinarians", veterinarians);

        return "add-treatment-form";
    }

    /**
     * Processes the submission of the new medical treatment form.
     *
     * @param createDto The DTO populated with data from the form.
     * @return A redirect instruction.
     */
    @PostMapping("/treatments/new")
    public String handleAddTreatmentForm(@ModelAttribute("treatment") CreateMedicalTreatmentDto createDto) {
        medicalHistoryService.createNewTreatment(createDto);

        // Redirect back to the medical history page for the animal that was just treated.
        return "redirect:/medical/by-animal?animalId=" + createDto.getAnimalId();
    }
}
