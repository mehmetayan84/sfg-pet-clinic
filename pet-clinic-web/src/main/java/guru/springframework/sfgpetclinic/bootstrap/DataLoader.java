package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;

    private final VetService vetService;

    private final PetTypeService petTypeService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Ece");
        owner1.setLastName("Ayan");
        owner1.setAddress("Golbasi");
        owner1.setCity("Ankara");
        owner1.setTelephone("123123123");

        Pet pet1 = new Pet();
        pet1.setPetType(savedCatPetType);
        pet1.setBirthDate(LocalDate.now());
        pet1.setName("Rosco");
        pet1.setOwner(owner1);
        owner1.getPets().add(pet1);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Neslihan");
        owner2.setLastName("Ayan");
        owner2.setAddress("Nazilli");
        owner2.setCity("Aydin");
        owner2.setTelephone("456456456");

        Pet pet2 = new Pet();
        pet2.setPetType(savedDogPetType);
        pet2.setBirthDate(LocalDate.now());
        pet2.setName("Mamuk");
        pet2.setOwner(owner2);
        owner2.getPets().add(pet2);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Mehmet");
        vet1.setLastName("Ayan");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Selçuk");
        vet2.setLastName("Ayan");
        vetService.save(vet2);

        System.out.println("Loaded vets...");

    }
}
