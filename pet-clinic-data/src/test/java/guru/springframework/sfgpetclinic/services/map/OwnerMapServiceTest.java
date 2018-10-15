package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;

    private Long ownerId = 1L;

    private String lastName = "Doe";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(), new PetTypeMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(owners.size(), 1);
    }

    @Test
    void deleteById() {
        Owner owner = ownerMapService.findById(ownerId);
        assertEquals(owner.getId(), ownerId);
    }

    @Test
    void delete() {

        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());

    }

    @Test
    void saveExistingId() {

        Long id = 2L;

        Owner owner = Owner.builder().id(id).build();

        Owner savedOwner = ownerMapService.save(owner);

        assertEquals(savedOwner.getId(), id);

    }

    @Test
    void saveNoId() {

        Owner owner = Owner.builder().build();

        Owner savedOwner = ownerMapService.save(owner);

        assertNotNull(savedOwner.getId());

        assertNotNull(savedOwner);

    }

    @Test
    void findById() {

        Owner foundOwner = ownerMapService.findById(ownerId);

        assertEquals(ownerId, foundOwner.getId());

    }

    @Test
    void findByLastName() {

        Owner foundOwner = ownerMapService.findByLastName(lastName);

        assertNotNull(foundOwner);

        assertEquals(lastName, foundOwner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {

        Owner foundOwner = ownerMapService.findByLastName("Foo");

        assertNull(foundOwner);
    }
}