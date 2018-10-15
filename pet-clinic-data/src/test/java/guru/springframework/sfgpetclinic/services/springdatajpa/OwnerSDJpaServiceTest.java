package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    String lastName = "Smith";
    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(lastName).build();
    }

    @Test
    void findByLastName() {

        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner owner = ownerSDJpaService.findByLastName(lastName);

        assertEquals(lastName, owner.getLastName());

        verify(ownerRepository).findByLastName(any());

    }

    @Test
    void findAll() {

        Set<Owner> returnOwnerSet = new HashSet<>();

        returnOwnerSet.add(Owner.builder().id(1L).build());
        returnOwnerSet.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        Set<Owner> owners = ownerSDJpaService.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());

    }

    @Test
    void findById() {

        when(ownerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(returnOwner));

        Owner owner = ownerSDJpaService.findById(1L);

        assertNotNull(owner);

    }

    @Test
    void findByIdNotFound() {

        when(ownerRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        Owner owner = ownerSDJpaService.findById(1L);

        assertNull(owner);

    }

    @Test
    void save() {

        Owner owner = Owner.builder().id(2L).build();

        when(ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = ownerSDJpaService.save(owner);

        assertNotNull(savedOwner);

        verify(ownerRepository).save(any());

    }

    @Test
    void delete() {

        ownerSDJpaService.delete(returnOwner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {

        ownerSDJpaService.deleteById(returnOwner.getId());

        verify(ownerRepository).deleteById(anyLong());

    }
}