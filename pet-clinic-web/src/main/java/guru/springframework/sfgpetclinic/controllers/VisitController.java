package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;
    private final static String VIEWS_VISIT_CREATE_OR_UPDATE_FROM = "pets/createorupdatevisitform";

    @Autowired
    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/visits/new")
    public String initVisitForm(Model model) {
        return VIEWS_VISIT_CREATE_OR_UPDATE_FROM;
    }

    @PostMapping("/visits/new")
    public String processVisitForm(@PathVariable Long petId, @Valid Visit visit, BindingResult result) {
        if(result.hasErrors())
            return VIEWS_VISIT_CREATE_OR_UPDATE_FROM;
        else {
            visitService.save(visit);
            return "redirect:/owners/" + petService.findById(petId).getOwner().getId();
        }
    }

}
