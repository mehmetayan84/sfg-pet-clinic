package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final  OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findowners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        if(owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> results = ownerService.findByLastNameLike(owner.getLastName());

        if(results.isEmpty()) {

            result.rejectValue("lastName", "notFound", "not Found");

            return "owners/findowners";

        }else if(results.size() == 1) {

            owner = results.get(0);

            return "redirect:/owners/" + owner.getId();

        } else {

            model.addAttribute("owners", results);

            return "owners/ownerslist";

        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long id) {
        ModelAndView modelAndView = new ModelAndView("/owners/ownerdetails");
        modelAndView.addObject(ownerService.findById(id));
        return modelAndView;
    }
}
